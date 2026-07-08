# Legacy Reporting Scenario Fixtures

Canonical fixture set for legacy-vs-rewrite parity checks.

## Fixture Set A: Customer Bill Report

### A1 - Existing customer with mixed categories
- Input: `reportType=customer`, `customerId=<valid-with-hours>`
- Expected:
  - ordered line rows by `date_logged` DESC
  - `line_total = hours * hourly_rate`
  - totals row with exact sum of hours and amount

### A2 - Existing customer with no billable hours
- Input: `reportType=customer`, `customerId=<valid-no-hours>`
- Expected:
  - customer identity is present
  - no detail rows beyond totals baseline
  - totals are zero-equivalent

### A3 - Missing/invalid customer
- Input: `reportType=customer`, `customerId=<invalid>`
- Expected:
  - error/failure behavior equivalent to legacy path

## Fixture Set B: Monthly Summary Report

### B1 - Month with multi-customer entries
- Input: `reportType=monthly`, `year=<yyyy>`, `month=<mm>`
- Expected:
  - only entries in requested month window included
  - rows grouped by customer
  - sorted by total amount DESC
  - monthly totals row present

### B2 - Month with no entries
- Input: `reportType=monthly`, `year=<yyyy-empty>`, `month=<mm-empty>`
- Expected:
  - empty or zero-equivalent aggregates matching legacy behavior

## Fixture Set C: Revenue Summary Report

### C1 - Full revenue summary
- Input: `reportType=revenue`
- Expected:
  - summary by customer includes hours/revenue/avg rate
  - summary by category includes hourly rate/hours/revenue
  - each table sorted by total revenue DESC

### C2 - Sparse data environment
- Input: `reportType=revenue` with low/no billable records
- Expected:
  - left-join visibility behavior matches legacy output semantics

## Fixture Governance

- Do not alter parity expectations without corresponding business-rule evidence.
- If output behavior lacks business-rule backing, raise blocker in `ambiguity-blockers.md`.
