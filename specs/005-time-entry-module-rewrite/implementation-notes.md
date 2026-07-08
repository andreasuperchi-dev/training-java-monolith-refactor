# Time Entry Rewrite Implementation Notes

Feature: `005-time-entry-module-rewrite`

## Scope

- Time Entry Domain only
- Legacy-equivalent behavior preservation
- No non-Time Entry module expansion

## Baseline Evidence

- `src/main/webapp/hours.jsp`
- `BillingService.validateBillableHour`
- `BR-008`, `BR-009`, `BR-010`, `BR-011`

## Execution Notes

- Apply test-first within each story phase.
- Stop and log blocker for ambiguous behavior before finalizing implementation.
