# Reporting Rewrite Traceability Matrix

This matrix maps reporting requirements and acceptance tests to preserved phase-1 business rules.

## Requirement to Rule Mapping

| Requirement ID | Requirement Summary | Business Rule Links | Coverage Notes |
|----------------|---------------------|---------------------|----------------|
| FR-001 | Module selected is Reporting Query Domain | BR-013 | Reporting-only boundary aligned to observed report behavior |
| FR-002 | Reporting-specific requirements only | BR-013 | Scope excludes non-reporting modules |
| FR-003 | Every requirement maps to preserved rules | BR-001, BR-002, BR-003, BR-004, BR-013 | Core reporting rule set |
| FR-004 | Acceptance tests derived from mapped rules | BR-001, BR-002, BR-003, BR-004, BR-013 | Direct derivation documented in test specs |
| FR-005 | Legacy and rewritten expected outcomes defined | BR-001, BR-002, BR-003, BR-004, BR-013 | Parity expectations listed per report flow |
| FR-006 | Stop and clarify ambiguous or missing behavior | BR-013 | Blocker protocol, no invented behavior |
| FR-007 | Legacy-equivalent interface contract | BR-013 | Contract operations mirror legacy report types |
| FR-008 | Preserve operation, input/output, error semantics | BR-001, BR-002, BR-003, BR-004, BR-013 | Includes ordering and totals parity |
| FR-009 | Gradual traffic switching compatibility criteria | BR-013 | Switch gates enforce no drift |
| FR-010 | Explicit out-of-scope boundaries | BR-013 | Non-reporting behavior excluded |
| FR-011 | Evidence to declare legacy path empty | BR-001, BR-002, BR-003, BR-004, BR-013 | Requires full parity pass plus gate closure |
| FR-012 | Preserve SA and architecture trace constraints | BR-013 | SA-001 and C-001 constraints reflected in contract |

## Acceptance Tests to Rule Mapping

| Test ID | Test Artifact | Business Rule Links | Expected Parity Outcome |
|---------|---------------|---------------------|--------------------------|
| RP-001 | `tests/acceptance-customer-report.md` | BR-001, BR-002, BR-013 | Customer bill rows and totals match exactly |
| RP-002 | `tests/acceptance-monthly-report.md` | BR-003, BR-013 | Month-filtered aggregates and totals match |
| RP-003 | `tests/acceptance-revenue-report.md` | BR-002, BR-004, BR-013 | Revenue summaries match for customer/category views |
| RP-004 | `tests/parity-assertions.md` (error-path section) | BR-001 | Missing/invalid customer behavior remains equivalent |

## SA/Architecture Constraint Mapping

| Constraint Source | Reporting Rewrite Constraint | Coverage Artifact |
|-------------------|------------------------------|-------------------|
| SA-001 | Remove direct JSP SQL ownership from final target path while preserving observed semantics | `contracts/reporting-query-contract.md` |
| C-001 | Reporting access must flow through reporting query boundary contract | `contracts/reporting-query-contract.md` |

## End-to-End Coverage Verdict

- Requirement coverage: PASS (12/12 FR mapped)
- Acceptance coverage: PASS (4/4 parity test groups mapped)
- Constraint coverage: PASS (SA-001 and C-001 captured)
- Scope containment: PASS (reporting only)
