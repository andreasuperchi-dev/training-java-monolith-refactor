# Target Architecture Contracts

This document defines architecture-level contracts that replace flagged legacy integrations and preserve validated business behavior.

## Contract Coverage Rule

Each contract entry maps to one or more substitution-audit rows and references preserved business rules where applicable.

## C-001 Reporting Access Contract (replaces direct JSP SQL access)

- Replaces: direct JDBC SQL/scriptlet reporting path.
- Legacy flags: `SA-001`.
- Contract intent:
  - Reporting consumers interact through a reporting query interface boundary, not direct SQL in presentation layer.
  - Contract guarantees stable report semantics for customer bill, monthly report, and revenue summaries.
- Preservation anchors: `BR-002`, `BR-003`, `BR-004`, `BR-013`.
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-001`, `specs/001-rediscover-legacy-monolith/business-rules.md#BR-013`

## C-002 Data Access Platform Contract (replaces runtime datasource fallback split)

- Replaces: mixed Liberty JNDI + embedded Derby fallback behavior.
- Legacy flags: `SA-002`, `SA-003`.
- Contract intent:
  - Data access is provided through a platform-managed persistence contract.
  - Runtime mode switching is explicit and policy-driven, not implicit fallback.
  - Database availability and connectivity semantics are externalized as platform concerns.
- Preservation anchors: `BR-015`, referential constraints from data model.
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-002`, `#SA-003`, `specs/001-rediscover-legacy-monolith/business-rules.md#BR-015`

## C-003 Identity Federation Contract (replaces config-only basic registry auth)

- Replaces: Liberty basic registry assumptions.
- Legacy flags: `SA-004`.
- Contract intent:
  - Authentication and authorization rely on federated identity contract.
  - Role/claim mapping is explicit and portable across environments.
  - Security policy is not embedded as static server-local user entries.
- Preservation anchors: user identity continuity (`BR-006`) and secure access boundary requirements.
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-004`, `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md#INT-003`

## C-004 API Surface Authority Contract (retires undocumented API claim drift)

- Replaces: documentation-only API claims without executable evidence.
- Legacy flags: `SA-005`.
- Contract intent:
  - All externally consumable APIs must be declared in authoritative architecture contract inventory.
  - Undeclared or unimplemented API claims are excluded from target-state contracts.
- Preservation anchors: none required for retired undocumented behavior; retirement is evidence-backed.
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-005`, `specs/001-rediscover-legacy-monolith/open-questions.md#OQ-005`

## C-005 Endpoint Discovery and Environment Binding Contract

- Replaces: hard-coded localhost endpoint assumptions.
- Legacy flags: `SA-010`.
- Contract intent:
  - Service endpoints are discovered/configured through environment-aware runtime configuration.
  - No architecture dependency on localhost-specific host/port assumptions.
- Preservation anchors: context-root based interaction continuity where needed.
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-010`, `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md#EP-001`, `#EP-004`

## C-006 Driver and Runtime Dependency Provisioning Contract

- Replaces: server-local driver fileset dependency assumptions.
- Legacy flags: `SA-012`.
- Contract intent:
  - Runtime dependencies are provisioned through platform artifact management contract.
  - Driver/runtime compatibility lifecycle is governed externally from app-local filesystem layout.
- Preservation anchors: database connectivity behavior parity (`BR-015` operational continuity intent).
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-012`, `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md#FS-003`

## C-007 Validation Policy Contract (addresses write-path validation divergence)

- Replaces: split validation semantics across service path and JSP write path.
- Legacy flags: `SA-008`.
- Contract intent:
  - Time-entry validation policy is exposed as a canonical domain policy contract independent of UI channel.
  - Consumers must receive consistent validation outcomes for positivity/date constraints and advisory semantics.
- Preservation anchors: `BR-008`, `BR-009`, `BR-010`, `BR-011`.
- Sources: `specs/002-substitution-audit/substitution-audit.md#SA-008`, `specs/001-rediscover-legacy-monolith/business-rules.md#BR-008`

## Contract Completeness Check

- Every flagged integration-related substitution row has a replacement contract entry.
- Contract definitions remain architecture-level only (no implementation tasks, no code).
- Each contract includes source traceability to substitution rows and, where relevant, preserved business rules.