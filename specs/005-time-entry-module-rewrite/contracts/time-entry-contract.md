# Time Entry Contract (Legacy-Equivalent)

This contract defines externally observable Time Entry behavior that must remain equivalent during gradual migration.

## Scope

Module: Time Entry Domain

In scope operations:
- Log billable hours request handling
- Time entry validation feedback semantics
- Persistence outcome semantics

Out of scope:
- reporting contract behavior
- billing-calculation internals

## T-001 Log Time Entry Request Contract

### Request semantics
- Route semantic: equivalent to `hours.jsp` submission with `action=log`.
- Required inputs:
  - `customerId`
  - `userId`
  - `categoryId`
  - `hours`
- Optional inputs:
  - `note`
  - `date`

### Input handling behavior
- Missing required fields preserve legacy-equivalent validation-error behavior.
- `date` omitted -> defaults to current date behavior.
- Invalid numeric parsing preserves legacy-equivalent error behavior.

### Traceability
- `BR-010`, `BR-011`

## T-002 Validation Semantics Contract

### Constraint behavior
- Non-positive hours preserve legacy-equivalent invalid behavior.
- Future-dated entries preserve legacy-equivalent invalid behavior.
- Weekend entries preserve warning-equivalent behavior unless policy clarification supersedes.

### Ambiguity rule
- If validation semantics are ambiguous between sources, behavior remains blocked for clarification; no invented behavior is permitted.

### Traceability
- `BR-008`, `BR-009`, `CF-002`, `SA-008`

## T-003 Persistence Semantics Contract

### Persistence behavior
- Successful valid requests produce equivalent persisted billable hour record semantics.
- Relationship references to customer/user/category remain required.
- Error outcomes for invalid references preserve legacy-equivalent path semantics.

### Traceability
- `BR-005`, `BR-010`

## T-004 Traffic-Switch Compatibility Contract

### Switch gate requirements
- Legacy and rewritten Time Entry paths must satisfy parity checks before traffic expansion.
- No externally observable drift in validation messages, success/error outcomes, or default date behavior.
- Rollback to legacy path must remain available during switch progression.

### Traceability
- `FR-009`, `FR-011`, `SA-008`

## Source Anchors

- `src/main/webapp/hours.jsp`
- `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`
- `specs/001-rediscover-legacy-monolith/business-rules.md`
- `specs/002-substitution-audit/substitution-audit.md#SA-008`
- `specs/003-define-target-architecture/architecture.md` (Time Entry boundary)

## Executed Parity Notes

- `hours.jsp` now routes logging through `TimeEntryService` while preserving input names and action semantics.
- Validation outcomes retain legacy-equivalent prefixes (`Validation errors:` / `Error logging hours:`).
- Success outcome remains `Hours logged successfully!` with weekend warning preserved as additive information.
