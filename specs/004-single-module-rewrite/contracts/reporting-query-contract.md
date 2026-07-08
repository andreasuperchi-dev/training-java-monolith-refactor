# Reporting Query Contract (Legacy-Equivalent)

This contract defines the reporting-module interface that must remain behaviorally identical to the current legacy reporting surface to support gradual traffic switching.

## Contract Scope

Module: Reporting Query Domain (phase 3a boundary)

In scope operations:
- Customer Bill report query
- Monthly Summary report query
- Revenue Summary report query

Out of scope:
- write operations
- non-reporting module contracts

## R-001 Customer Bill Report Contract

### Request
- Route semantic: equivalent to `reports.jsp?reportType=customer&customerId={id}`
- Required inputs:
  - `reportType=customer`
  - `customerId` (numeric customer identifier)
- Input constraint behavior:
  - missing/empty `customerId` must preserve legacy-equivalent failure path
  - non-numeric `customerId` must preserve legacy-equivalent failure path

### Response Semantics
- Produces customer bill view data containing:
  - customer identity (`name`, `email`)
  - line items with date, user, category, hours, hourly rate, computed line amount, note
  - totals: total hours and total amount
- Ordering requirement: line items ordered by `date_logged` descending.

### Business Rules and Calculations
- Line amount formula: `hours * hourly_rate`
- Bill totals: sums of line amounts and hours
- Customer must exist; missing customer behavior remains equivalent to legacy error behavior.

### Traceability
- `BR-001`, `BR-002`, `BR-013`

## R-002 Monthly Summary Report Contract

### Request
- Route semantic: equivalent to `reports.jsp?reportType=monthly&year={yyyy}&month={mm}`
- Required inputs:
  - `reportType=monthly`
  - `year` (numeric)
  - `month` (two-digit month)
- Input/date-window constraint behavior:
  - date-window semantics remain equivalent to legacy SQL-style month range behavior
  - invalid month/year formatting preserves legacy-equivalent handling

### Response Semantics
- Produces per-customer monthly aggregates:
  - customer name
  - total hours in month window
  - total revenue in month window
- Includes monthly totals row.
- Ordering requirement: rows sorted by total amount descending.

### Business Rules and Calculations
- Included entries match legacy month filter behavior.
- Revenue arithmetic preserves legacy line-total aggregation semantics.

### Traceability
- `BR-003`, `BR-013`

## R-003 Revenue Summary Report Contract

### Request
- Route semantic: equivalent to `reports.jsp?reportType=revenue`
- Required inputs:
  - `reportType=revenue`
- Input constraint behavior:
  - no additional parameters are required
  - unexpected optional parameters must not alter legacy-equivalent output semantics

### Response Semantics
- Produces two summary projections:
  - by customer: total hours, total revenue, average rate
  - by category: hourly rate, total hours, total revenue
- Ordering requirement: rows sorted by total revenue descending in each projection.

### Business Rules and Calculations
- Preserves category-based aggregation behavior and arithmetic semantics.
- Left-join/empty-result visibility remains equivalent to legacy behavior.

### Traceability
- `BR-002`, `BR-004`, `BR-013`

## Error and Compatibility Semantics

- Required parameter omissions and invalid values produce behavior equivalent to legacy report flow for the corresponding operation.
- Traffic may be shifted between legacy and rewritten implementations only when parity tests for all in-scope operations pass.
- No externally observable contract drift (inputs, outputs, calculations, ordering, or error semantics) is permitted during gradual switching.

## Traffic Switching Gates

- Gate 1: Contract-equivalence checks pass for R-001, R-002, R-003.
- Gate 2: Acceptance parity suite passes with identical outputs across legacy and rewritten implementations.
- Gate 3: Rollback path preserves same legacy route semantics if parity regression occurs.

## Gradual Switch Execution Sequence

1. Baseline parity evidence complete for R-001, R-002, and R-003.
2. Enable rewritten path for low-risk reporting traffic segment.
3. Compare live outputs against legacy parity assertions.
4. Expand traffic only after stable parity windows pass.
5. Keep rollback available until full stabilization completes.

## Ambiguity Handling Rule

If behavior required by this contract is ambiguous or missing in phase-1 business rules, the item is recorded as a blocker and clarified before contract finalization. No invented behavior is allowed.

## Source Anchors

- `specs/001-rediscover-legacy-monolith/business-rules.md`
- `specs/001-rediscover-legacy-monolith/contracts/ui-and-reporting-contracts.md`
- `src/main/webapp/reports.jsp`
- `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`
- `specs/002-substitution-audit/substitution-audit.md#SA-001`
- `specs/003-define-target-architecture/contracts/target-architecture-contracts.md#C-001`
