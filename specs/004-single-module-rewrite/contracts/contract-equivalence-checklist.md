# Reporting Contract Equivalence Checklist (US3)

## Scope

Validate contract equivalence between legacy reporting interface and rewritten reporting module.

## Operation-Level Checks

### R-001 Customer Bill
- Request parameters equivalent (`reportType=customer`, `customerId`)
- Response sections equivalent (customer identity, line rows, totals)
- Ordering and arithmetic equivalent
- Error behavior equivalent

### R-002 Monthly Summary
- Request parameters equivalent (`reportType=monthly`, `year`, `month`)
- Month-window semantics equivalent
- Grouping/totals behavior equivalent
- Ordering equivalent

### R-003 Revenue Summary
- Request parameters equivalent (`reportType=revenue`)
- Customer and category projections equivalent
- Aggregate and ordering semantics equivalent
- Sparse-data behavior equivalent

## Cross-Cutting Checks

- No externally visible field/semantic drift
- No report-type regression
- Compatibility with gradual traffic switching maintained

## Verdict

- PASS only if all checks pass
- FAIL on any drift
- BLOCKED if ambiguity unresolved
