# Target Architecture Blueprint: Modernized System

## 1) Scope and Guardrails

This document defines architecture only.

Out of scope:
- implementation code
- per-module rewrite task planning
- detailed migration runbooks
- deployment scripts and infrastructure-as-code specifics

Traceability rule:
- Every major architecture choice must include at least one source trace to a preserved business rule (`BR-*`) or substitution-audit row (`SA-*`).

## 2) Canonical Source Index

| Source Artifact | Role in Architecture Decisions |
|----------------|--------------------------------|
| `specs/001-rediscover-legacy-monolith/business-rules.md` | Preserved business behavior anchors (`BR-*`) |
| `specs/001-rediscover-legacy-monolith/data-model.md` | Legacy entities, relationships, invariants |
| `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md` | Legacy integration footprint |
| `specs/001-rediscover-legacy-monolith/open-questions.md` | Ambiguity and policy risk inputs |
| `specs/002-substitution-audit/substitution-audit.md` | Target replacement/retire decisions (`SA-*`) |
| `specs/003-define-target-architecture/research.md` | Architecture rationale and alternatives |
| `specs/003-define-target-architecture/data-model.md` | Target domains and migration mapping |
| `specs/003-define-target-architecture/contracts/target-architecture-contracts.md` | Replacement contract definitions |

## 3) Architecture Decision Inventory (ADR-style)

| ADR | Decision | Rationale | Alternatives Considered | Traceability |
|-----|----------|-----------|-------------------------|--------------|
| ADR-001 | Use cloud-managed runtime/platform as target operating model | Removes host-coupled assumptions and implicit local runtime dependencies | Preserve Liberty + embedded Derby | `SA-002`, `SA-003`, `SA-010`, `SA-012`, `BR-015` |
| ADR-002 | Define bounded modules for Billing, Time Entry, Customer, Reporting, Identity/Access, and Integration Adapter | Separates mixed concerns currently coupled in JSP/service/DAO paths | Keep single layered monolith with package cleanup only | `SA-001`, `SA-008`, `BR-001`, `BR-002`, `BR-013` |
| ADR-003 | Preserve core domain model concepts while normalizing policy consistency | Maintains validated semantics while enabling modernization | Full schema redesign from scratch | `BR-002`, `BR-003`, `BR-005`, `SA-006`, `SA-007`, `SA-008` |
| ADR-004 | Replace all flagged legacy integration patterns with explicit architecture contracts | Eliminates undocumented, config-only, or host-bound integration assumptions | Defer contracts to implementation phase | `SA-001`, `SA-002`, `SA-004`, `SA-005`, `SA-010`, `SA-012` |
| ADR-005 | Apply strangler-fig as default migration strategy | Enables staged replacement while preserving continuity of validated rules | Big-bang rewrite | `FR-009`, `BR-001` to `BR-015`, `SA-001` to `SA-012` |

## 4) Target Runtime and Platform

### Runtime/Platform Direction
- Target runtime is containerized and cloud-managed.
- Persistence is delivered through a platform-managed relational service rather than embedded/local filesystem coupling.
- Identity is provided via federated identity contract, not static server-local registries.
- Endpoint addressing is environment-aware and discovery/config-driven, not localhost-hardcoded.

### Architecture Outcomes
- No dependence on local Derby data paths in target-state runtime.
- No hidden runtime fallback behavior; data access mode is explicit.
- Runtime dependencies are provisioned through platform artifact management, not app-local driver filesystem assumptions.

Traceability:
- `SA-002`, `SA-003`, `SA-004`, `SA-010`, `SA-012`, `BR-015`

## 5) Module and Service Boundary Catalog

| Boundary | Responsibilities | Inputs/Outputs | Notes | Traceability |
|----------|------------------|----------------|-------|--------------|
| Identity and Access Edge | Authentication, authorization claims/roles, identity boundary policy | User identity claims in, authorization decisions out | Replaces static basic registry assumptions | `SA-004`, `BR-006` |
| Customer Domain | Customer account identity, contact policy, account lifecycle | Customer records in/out | Preserves required customer write invariants | `BR-007`, `SA-007` |
| Billing Catalog Domain | Category/rate semantics | Category/rate data in/out | Preserves billing category semantics | `BR-002`, `BR-004` |
| Time Entry Domain | Billable work capture and policy validation | Time-entry commands/events | Resolves current split validation policy across paths | `BR-008`, `BR-010`, `SA-008` |
| Billing Calculation Domain | Customer bill and monthly aggregation behavior | Time/rate inputs -> bill/revenue outputs | Must preserve bill semantics | `BR-001`, `BR-002`, `BR-003`, `BR-004` |
| Reporting Query Domain | Query/read model access for customer/monthly/revenue reporting | Report queries in, report views out | Replaces direct JSP SQL | `BR-013`, `SA-001` |
| Integration Adapter Boundary | Platform-facing integration contracts and endpoint binding | Integration contract requests/responses | Contains endpoint and runtime binding policies | `SA-005`, `SA-010`, `SA-012` |

## 6) Data Model and Migration Path

### Target Data Domains
- `identity_user` (from `users`)
- `customer_account` (from `customers`)
- `billing_catalog_item` (from `billing_categories`)
- `time_entry` (from `billable_hours`)
- reporting read models (replacing direct JSP SQL joins)

### Migration Path (Strangler-Aligned)
1. Establish target data domains in parallel with legacy schema.
2. Replicate and validate data for each domain while preserving invariants.
3. Gradually reroute read/write concerns by boundary (starting with reporting and policy-consistency hotspots).
4. Cut over domain-by-domain once parity checks pass.
5. Decommission legacy access paths after successful cutover gates.

### Preservation Guardrails
- Bill calculations, month filters, and referential constraints must remain behaviorally compatible.
- Email uniqueness for user identity must be preserved.
- Known policy ambiguities (for example customer duplicate email policy) remain explicit architecture assumptions until resolved.

Traceability:
- `BR-002`, `BR-003`, `BR-005`, `BR-006`, `BR-007`, `BR-013`, `SA-001`, `SA-006`, `SA-007`, `SA-008`

## 7) Integration Contracts Replacement Mapping

| Legacy Flag | Legacy Integration Concern | Replacement Contract | Source |
|-------------|----------------------------|----------------------|--------|
| SA-001 | Direct JSP SQL reporting path | C-001 Reporting Access Contract | `contracts/target-architecture-contracts.md#C-001` |
| SA-002 | Mixed datasource runtime fallback | C-002 Data Access Platform Contract | `contracts/target-architecture-contracts.md#C-002` |
| SA-003 | Embedded Derby host-local store | C-002 Data Access Platform Contract | `contracts/target-architecture-contracts.md#C-002` |
| SA-004 | Basic registry auth assumptions | C-003 Identity Federation Contract | `contracts/target-architecture-contracts.md#C-003` |
| SA-005 | Documentation-only API claim drift | C-004 API Surface Authority Contract | `contracts/target-architecture-contracts.md#C-004` |
| SA-008 | Split validation semantics by path | C-007 Validation Policy Contract | `contracts/target-architecture-contracts.md#C-007` |
| SA-010 | Localhost endpoint assumptions | C-005 Endpoint Discovery and Environment Binding Contract | `contracts/target-architecture-contracts.md#C-005` |
| SA-012 | Server-local driver fileset assumptions | C-006 Driver and Runtime Dependency Provisioning Contract | `contracts/target-architecture-contracts.md#C-006` |

Coverage result:
- All flagged integration-related substitutions are mapped to replacement contracts.

## 8) Cross-Cutting Concerns

### Authentication and Authorization
- Federated identity and claim-based authorization are architecture defaults.
- Identity and authorization policies are centralized at boundary level and portable across environments.

Traceability: `SA-004`, `BR-006`

### Logging
- Centralized structured logging contract across boundaries.
- Correlation context required for cross-boundary request tracing.

Traceability: `SA-001` (reporting observability need), `SA-008` (policy consistency diagnostics)

### Configuration
- Runtime configuration is externalized and environment-bound.
- No architecture reliance on hard-coded localhost endpoints.

Traceability: `SA-010`, `EP-001`, `EP-004`

### Secrets
- Secrets are handled via managed secret provisioning model, not file-local assumptions.
- Access scoped by boundary and runtime identity.

Traceability: `SA-004`, `SA-012`

### Observability
- Metrics and traces aligned to domain boundaries and migration checkpoints.
- Architecture requires parity observability for legacy-vs-target behavior validation during strangler transition.

Traceability: `BR-002`, `BR-003`, `SA-001`, `SA-008`

## 9) Migration Strategy (Default: Strangler-Fig)

### Default Strategy
- Strangler-fig is the default migration strategy.
- Legacy system remains operational while target boundaries are introduced incrementally.

### Migration Segments
- Segment A: Reporting access contract and read model separation.
- Segment B: Validation-policy consistency through canonical contract.
- Segment C: Identity and endpoint/platform binding modernization.
- Segment D: Data access platform normalization and final legacy path retirement.

### Exception Criteria
- Domain-specific exceptions may be approved only when preserving a validated business rule requires temporary deviation.

Traceability:
- `FR-009`, `SA-001`, `SA-008`, `SA-004`, `SA-010`, `SA-012`, `BR-001` to `BR-015`

## 10) Risk Register

| Risk ID | Risk | Impact Area | Trigger | Mitigation Direction | Traceability |
|---------|------|-------------|---------|----------------------|--------------|
| R-001 | Rule-preservation regression during staged boundary cutover | Business correctness | Divergence in bill/report outcomes | Enforce decision-level traceability and parity validation checkpoints | `BR-002`, `BR-003`, `BR-013`, `SA-001` |
| R-002 | Identity policy mismatch across legacy and target | Security and access | Conflicting auth/identity assumptions | Centralize identity contract and require explicit policy gate for unresolved items | `BR-006`, `SA-004`, `OQ-006` |
| R-003 | Data migration inconsistency in referential links | Data integrity | Broken user/customer/category to time-entry mapping | Preserve referential parity guardrails and migration validation gates | `BR-005`, `SA-008` |
| R-004 | Integration contract drift from substitution intent | Integration reliability | Unmapped or ambiguous legacy integration replacement | Maintain one-to-one SA-to-contract mapping and coverage checks | `SA-001`, `SA-002`, `SA-005`, `SA-010`, `SA-012` |
| R-005 | Migration complexity and rollback uncertainty | Delivery safety | Cross-boundary cutover failures | Segment migration by strangler slices with rollback boundaries and observability gates | `FR-009`, `SA-001` to `SA-012` |

## 11) Unresolved Assumptions and Dependency Gates

- Customer duplicate-email canonical policy requires domain decision before final identity/account normalization.
  - Source: `OQ-004`, `SA-007`
- Weekend time-entry policy strictness remains advisory vs enforcement ambiguous.
  - Source: `OQ-003`, `BR-009`, `SA-009`
- Documentation-only API claims require explicit governance decision for retirement vs external ownership.
  - Source: `OQ-005`, `SA-005`

## 12) Architecture-Only Compliance Check

- No code included.
- No per-module rewrite tasks included.
- No implementation sequencing detail beyond architecture-level migration segmentation.

Status: Compliant.

## 13) Final Traceability Coverage Check

- All major architecture decisions include at least one `BR-*` or `SA-*` trace.
- Integration replacement mapping includes all flagged integration-related substitution rows.
- Cross-cutting concerns and migration strategy include explicit source anchors.

Status: Ready for architecture review and downstream implementation task execution.