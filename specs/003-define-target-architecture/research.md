# Phase 0 Research: Target Architecture for Modernized System

## Decision Log

### 1) Target runtime and platform profile
- Decision: Adopt a containerized, cloud-managed runtime with managed relational database and managed identity integration as target operating model.
- Rationale: Substitution findings indicate current embedded/host-coupled runtime assumptions are cloud-hostile and operationally fragile (`SA-002`, `SA-003`, `SA-010`, `SA-012`).
- Alternatives considered: Preserve Liberty + embedded Derby deployment model; rejected due to host-local state and environment-coupled endpoint assumptions.
- Traceability: `specs/002-substitution-audit/substitution-audit.md#SA-002`, `#SA-003`, `#SA-010`, `#SA-012`; `specs/001-rediscover-legacy-monolith/business-rules.md#BR-015`

### 2) Service/module boundary architecture
- Decision: Define bounded modules around Billing, Time Entry, Customer Management, Identity/Access Edge, Reporting Query/API, and Platform Integration Adapter.
- Rationale: Legacy monolith mixes direct JSP SQL, service logic, and DAO concerns; explicit boundaries reduce coupling while preserving business rules (`BR-001` to `BR-004`, `BR-010` to `BR-013`).
- Alternatives considered: Keep a layered monolith with only package cleanup; rejected because it does not sufficiently separate reporting/query and policy enforcement concerns identified in substitution rows.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-001`, `#BR-002`, `#BR-003`, `#BR-004`, `#BR-010`, `#BR-013`; `specs/002-substitution-audit/substitution-audit.md#SA-001`, `#SA-008`

### 3) Data model modernization direction
- Decision: Keep core domain concepts (`Customer`, `User`, `BillingCategory`, `BillableHour`) but reorganize into target data domains with explicit policy constraints and migration-safe identity references.
- Rationale: Rediscovery confirms stable core entities and relationships; gaps exist in policy enforcement consistency and customer identity semantics.
- Alternatives considered: Full schema redesign from scratch; rejected because business rule preservation and migration traceability are primary constraints.
- Traceability: `specs/001-rediscover-legacy-monolith/data-model.md#Entity: Customer`, `#Entity: BillableHour`, `#Relationships`; `specs/002-substitution-audit/substitution-audit.md#SA-006`, `#SA-007`, `#SA-008`

### 4) Integration replacement pattern
- Decision: Define explicit target integration contracts for every flagged legacy integration and endpoint assumption, including runtime datasource handling, auth, endpoint discovery, and reporting access paths.
- Rationale: Legacy integration surface includes configuration-only assumptions, documentation drift, and mixed runtime/database access semantics.
- Alternatives considered: Defer contract definition to implementation phase; rejected because architecture acceptance requires replacement intent coverage now.
- Traceability: `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md#INT-001`, `#INT-002`, `#INT-003`, `#EP-005`, `#FS-003`; `specs/002-substitution-audit/substitution-audit.md#SA-001`, `#SA-002`, `#SA-004`, `#SA-005`, `#SA-010`, `#SA-012`

### 5) Cross-cutting architecture baseline
- Decision: Establish architecture-level policies for identity federation, centralized logging/trace correlation, externalized configuration, managed secrets, and observability SLO dashboards.
- Rationale: Existing state reveals config-bound auth and host-specific runtime assumptions; cross-cutting consistency is required for strangler migration safety.
- Alternatives considered: Keep concerns local to each module; rejected as high risk for drift and inconsistent controls.
- Traceability: `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md#INT-003`, `#EP-001`, `#EP-004`; `specs/002-substitution-audit/substitution-audit.md#SA-004`, `#SA-010`

### 6) Migration strategy default
- Decision: Use strangler-fig as default strategy, introducing target boundaries around existing capabilities and progressively routing traffic while preserving validated rules.
- Rationale: Feature constraint sets strangler-fig as default; legacy rule coverage and substitution mapping support staged extraction without immediate full rewrite.
- Alternatives considered: Big-bang rewrite; rejected due to high business continuity and policy regression risk.
- Traceability: `specs/003-define-target-architecture/spec.md#FR-009`; `specs/001-rediscover-legacy-monolith/business-rules.md#BR-001` to `#BR-015`; `specs/002-substitution-audit/substitution-audit.md#SA-001` to `#SA-012`

## Best-Practice Findings Applied

- Architecture decisions are normalized into traceable ADR-style entries (decision, rationale, alternatives, source links).
- Data migration is modeled as domain-preserving transformation, not immediate schema replacement.
- Integration replacement contracts are defined before tasking to reduce ambiguity and ensure coverage completeness.
- Risks are documented as architecture risks with mitigation direction only (no implementation tasks).

## Clarification Resolution Status

No `NEEDS CLARIFICATION` items remain in this planning phase.

Remaining uncertainty is captured as explicit assumptions and risks to be validated during later implementation planning and stakeholder review.