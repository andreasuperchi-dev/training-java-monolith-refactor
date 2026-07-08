# Reporting Functional Requirements (US1)

Reporting Query Domain only.

## In Scope

- Customer bill report behavior
- Monthly summary report behavior
- Revenue summary report behavior
- Legacy-equivalent reporting contract semantics

## Out of Scope

- Customer, billing catalog, time-entry, identity, and integration-adapter module rewrites
- New report types not present in legacy contract
- Architectural redesign beyond reporting module rewrite scope

## Requirement Catalog

### RQ-001 Customer Bill Retrieval
- The reporting module must return customer bill details for a valid customer request.
- Must include customer identity, line items, and totals.
- Rule links: BR-001, BR-002, BR-013.

### RQ-002 Customer Bill Arithmetic
- Line amount must be computed as `hours * hourly_rate`.
- Total hours and total amount must be sum of line values.
- Rule links: BR-002, BR-013.

### RQ-003 Monthly Summary Filtering
- Monthly summary must include only entries matching requested year/month window.
- Rule links: BR-003, BR-013.

### RQ-004 Monthly Summary Aggregation
- Monthly summary must aggregate by customer and include monthly totals.
- Rule links: BR-003, BR-013.

### RQ-005 Revenue Summary by Category
- Revenue summary must provide category-level totals and preserve ordering by total revenue.
- Rule links: BR-004, BR-013.

### RQ-006 Revenue Summary by Customer
- Revenue summary must provide customer-level totals and average rate semantics equivalent to legacy outputs.
- Rule links: BR-002, BR-013.

### RQ-007 Error Behavior Equivalence
- Missing/invalid customer requests must preserve legacy-equivalent failure behavior.
- Rule links: BR-001.

### RQ-008 Ambiguity Block Rule
- If a required behavior lacks explicit rule support, requirement remains blocked until clarification.
- Rule links: BR-013 plus blocker workflow.

## Ambiguity-Stop Checkpoints

- Checkpoint A: requirement drafting complete -> verify all RQ entries have rule links.
- Checkpoint B: if any RQ has unclear behavior -> add blocker entry and halt finalization.
- Checkpoint C: finalize only after blocker resolution or approved deferral.
