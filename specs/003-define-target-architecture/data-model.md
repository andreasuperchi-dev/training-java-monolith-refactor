# Data Model: Target Architecture for Modernized System

This model defines target-state data domains and migration mapping from legacy schema while preserving validated business semantics.

## Target Data Domains

### Domain: Identity and Access
- Purpose: Canonical user identity, authentication claims, and authorization role mappings.
- Preserved legacy anchors:
  - User uniqueness by email (`BR-006`)
  - Existing user lifecycle semantics from current DAO-backed operations
- Legacy mappings:
  - `users` -> `identity_user`
- Migration notes:
  - Preserve email uniqueness invariant in target identity store.
  - Normalize profile fields without losing legacy referential keys.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-006`; `specs/002-substitution-audit/substitution-audit.md#SA-006`

### Domain: Customer and Account Context
- Purpose: Customer identity, contact policy, and billing account context.
- Preserved legacy anchors:
  - Required customer name/email on write path (`BR-007`)
  - Existing billability relationship to hours (`billable_hours.customer_id`)
- Legacy mappings:
  - `customers` -> `customer_account`
- Migration notes:
  - Carry forward required fields.
  - Treat duplicate email policy as explicit architecture assumption requiring domain decision gate.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-007`; `specs/001-rediscover-legacy-monolith/data-model.md#Entity: Customer`; `specs/002-substitution-audit/substitution-audit.md#SA-007`

### Domain: Billing Catalog
- Purpose: Billable categories, rate policies, and category lifecycle.
- Preserved legacy anchors:
  - Category-based revenue calculation semantics (`BR-002`, `BR-004`)
- Legacy mappings:
  - `billing_categories` -> `billing_catalog_item`
- Migration notes:
  - Preserve category-to-hour association.
  - Keep rate semantic compatibility for historical billing integrity.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-002`; `specs/001-rediscover-legacy-monolith/business-rules.md#BR-004`

### Domain: Time Entry and Billable Work
- Purpose: Time capture, validation policy state, and bill generation inputs.
- Preserved legacy anchors:
  - Customer bill computation from hours * rate (`BR-002`)
  - Monthly filtering semantics (`BR-003`)
  - Required references to customer/user/category (`BR-005`)
- Legacy mappings:
  - `billable_hours` -> `time_entry`
- Migration notes:
  - Preserve FK-equivalent referential guarantees.
  - Explicitly unify validation policy that is currently split between JSP and service paths.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-002`; `#BR-003`; `#BR-005`; `specs/002-substitution-audit/substitution-audit.md#SA-008`

### Domain: Reporting and Read Models
- Purpose: Query-optimized billing and revenue views replacing direct JSP SQL execution.
- Preserved legacy anchors:
  - Customer and monthly report business outcomes (`BR-013`)
  - Dashboard revenue semantics (`BR-012`)
- Legacy mappings:
  - JSP SQL report flows -> reporting read model contracts
- Migration notes:
  - Keep output semantics stable while decoupling from direct view-layer SQL.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-012`; `#BR-013`; `specs/002-substitution-audit/substitution-audit.md#SA-001`

## Migration Path Model (Legacy -> Target)

| Legacy Structure | Target Domain Structure | Migration Pattern | Preservation Goal | Evidence |
|------------------|-------------------------|-------------------|-------------------|----------|
| `users` | `identity_user` | replicate + validate + cutover | email uniqueness and actor continuity | `BR-006`, `SA-006` |
| `customers` | `customer_account` | replicate + policy normalization | required customer identity fields | `BR-007`, `SA-007` |
| `billing_categories` | `billing_catalog_item` | replicate + compatibility projection | rate semantics continuity | `BR-002`, `BR-004` |
| `billable_hours` | `time_entry` | replicate + referential integrity verification | billability and monthly reporting correctness | `BR-002`, `BR-003`, `BR-005` |
| JSP reporting joins | reporting read model | strangler query cutover | report output parity | `BR-013`, `SA-001` |

## Data Migration Guardrails

- No target mapping can invalidate preserved rule outcomes for billing totals, month filtering, or required references.
- Referential integrity parity is mandatory for user/customer/category to time-entry links.
- Policy ambiguities from open questions are carried as migration assumptions until formal decisions are made.

## State and Consistency Assumptions

- Legacy and target data domains may run in parallel during strangler transition.
- Reporting views may be dual-sourced during transitional validation windows.
- Identity and customer policy decisions unresolved in prior phases remain architecture risks, not implementation commitments.

## Traceability Requirement

Every target domain and migration mapping above is linked to at least one preserved business rule or substitution-audit row in conformance with `FR-012` and `SC-001`.