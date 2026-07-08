# Quickstart Validation Guide: Reporting Module Rewrite (Phase 3b)

This guide validates planning artifacts for the Reporting Query Domain rewrite specification.

## Prerequisites

- Feature artifacts exist:
  - `specs/004-single-module-rewrite/spec.md`
  - `specs/004-single-module-rewrite/plan.md`
  - `specs/004-single-module-rewrite/research.md`
  - `specs/004-single-module-rewrite/data-model.md`
  - `specs/004-single-module-rewrite/contracts/reporting-query-contract.md`
- Baseline evidence available:
  - `specs/001-rediscover-legacy-monolith/business-rules.md`
  - `specs/001-rediscover-legacy-monolith/contracts/ui-and-reporting-contracts.md`
  - `src/main/webapp/reports.jsp`

## Validation Scenario 1: Requirement-to-Rule Traceability

1. Open `spec.md` and list all functional requirements.
2. Verify each requirement has one or more explicit `BR-*` mappings in the phase artifacts.
3. Confirm all mappings are reporting-scope only.

Expected outcome:
- 100% requirement coverage with at least one preserved business-rule reference per requirement (`SC-001`).

## Validation Scenario 2: Acceptance-Test Derivation Readiness

1. Review acceptance scenarios in `spec.md` and parity intent in `research.md`.
2. Verify expected outcomes for legacy and rewritten reporting behavior are defined for each report type.

Expected outcome:
- Test definitions are directly derivable from `BR-001`, `BR-002`, `BR-003`, `BR-004`, and `BR-013`.

## Validation Scenario 3: Interface Contract Equivalence

1. Compare `contracts/reporting-query-contract.md` with legacy reporting behavior in `reports.jsp` and UI/reporting contracts.
2. Verify operation inputs, output semantics, ordering, and calculation rules are equivalent.

Expected outcome:
- Contract remains legacy-equivalent and supports gradual traffic switching with no externally visible drift (`SC-004`).

## Validation Scenario 4: Single-Module Scope Containment

1. Review all feature-004 artifacts.
2. Confirm only Reporting Query Domain behaviors are specified.
3. Confirm non-reporting module requirements are absent.

Expected outcome:
- Zero non-reporting requirements in this phase-3b iteration (`SC-006`).

## Validation Scenario 5: Ambiguity Handling Compliance

1. Inspect artifacts for any unresolved behavior assumptions.
2. Confirm unresolved behavior is tracked as blocker/clarification and not treated as final behavior.

Expected outcome:
- No invented behavior in requirements, tests, or contract (`SC-005`).

## Suggested Parity Test Matrix (for downstream /speckit.tasks)

| Test ID | Report Type | Core Rule Anchors | Expected Parity Assertion |
|---------|-------------|-------------------|---------------------------|
| RP-001 | customer | `BR-001`, `BR-002`, `BR-013` | Customer bill rows and totals are identical across legacy and rewritten implementations |
| RP-002 | monthly | `BR-003`, `BR-013` | Monthly customer aggregates and totals match for same year/month inputs |
| RP-003 | revenue | `BR-002`, `BR-004`, `BR-013` | Revenue-by-customer and revenue-by-category outputs match including ordering |
| RP-004 | error path | `BR-001` | Missing/invalid customer behavior remains equivalent |

## Reviewer Handoff Checklist

- Confirm reporting module is explicitly selected in `FR-001`.
- Confirm every functional requirement is trace-linked to phase-1 business rules.
- Confirm contract parity for customer/monthly/revenue report flows.
- Confirm gradual switch gates are defined and test-driven.
- Confirm ambiguity policy is enforced: stop and ask, never invent behavior.

## Independent Story Validation Order

1. Validate US1 artifacts (`reporting-functional-requirements.md`, `traceability-matrix.md`, `requirements-validation.md`).
2. Validate US2 artifacts (`acceptance-*.md`, `parity-assertions.md`, `tests-validation.md`).
3. Validate US3 artifacts (`reporting-query-contract.md`, `contract-equivalence-checklist.md`, `contract-validation.md`, `traffic-switch-gates.md`).
4. Run final readiness review and confirm all success criteria are covered.

## Terminology Consistency Notes

- Use "Reporting Query Domain" as canonical module name.
- Use "legacy-equivalent" for compatibility language across requirements, tests, and contracts.
- Use "parity" to refer to legacy-vs-rewrite outcome equivalence.

## Notes

Implementation tasks and executable test suites are intentionally deferred to `/speckit.tasks` and `/speckit.implement`.
