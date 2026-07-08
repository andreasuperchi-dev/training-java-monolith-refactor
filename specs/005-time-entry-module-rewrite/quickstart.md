# Quickstart Validation Guide: Time Entry Module Rewrite (Phase 3b)

This guide validates Time Entry planning artifacts for feature `005-time-entry-module-rewrite`.

## Prerequisites

- Feature artifacts available:
  - `spec.md`
  - `plan.md`
  - `research.md`
  - `data-model.md`
  - `contracts/time-entry-contract.md`
- Baseline evidence available:
  - `specs/001-rediscover-legacy-monolith/business-rules.md`
  - `src/main/webapp/hours.jsp`
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`

## Validation Scenario 1: Requirement-to-Rule Traceability

1. Review all functional requirements in `spec.md`.
2. Confirm each requirement maps to at least one preserved `BR-*` anchor.
3. Confirm mappings are Time Entry scoped.

Expected outcome:
- 100% requirement-to-rule mapping coverage (`SC-001`).

## Validation Scenario 2: Acceptance-Test Readiness

1. Review Time Entry acceptance scenarios in `spec.md`.
2. Confirm test expectations can be derived from `BR-008`..`BR-011` and interface behavior evidence.

Expected outcome:
- Acceptance definitions are rule-derived with explicit parity outcomes (`SC-002`).

## Validation Scenario 3: Interface Contract Equivalence

1. Compare `contracts/time-entry-contract.md` with `hours.jsp` behavior.
2. Confirm required/optional inputs, validation outcomes, date defaulting, and success/error semantics are equivalent.

Expected outcome:
- Contract reflects externally observable legacy Time Entry behavior (`SC-004`).

## Validation Scenario 4: Scope Containment

1. Confirm artifacts remain Time Entry-only.
2. Confirm non-Time Entry requirements are excluded.

Expected outcome:
- Scope containment passes with zero cross-module requirement leakage (`SC-006`).

## Validation Scenario 5: Ambiguity Handling Compliance

1. Check for unresolved behavior assumptions.
2. Confirm unresolved items are tracked as blockers and not finalized as behavior.

Expected outcome:
- No invented behavior in requirements/tests/contracts (`SC-005`).

## Suggested Parity Test Matrix (for downstream /speckit.tasks)

| Test ID | Scenario | Rule Anchors | Expected Parity Assertion |
|---------|----------|--------------|---------------------------|
| TE-001 | required input validation | BR-010 | Missing required fields produce equivalent validation error outcomes |
| TE-002 | default date behavior | BR-011 | Missing date defaults to current-date-equivalent behavior |
| TE-003 | positivity and future-date checks | BR-008 | Invalid hours/date handling remains equivalent |
| TE-004 | weekend warning semantics | BR-009 | Weekend entry warning behavior remains equivalent |
| TE-005 | persistence with valid references | BR-005 | Valid entries persist with equivalent relationship semantics |

## Reviewer Handoff Checklist

- Confirm Time Entry Domain is explicitly selected.
- Confirm all requirements map to preserved business rules.
- Confirm contract preserves externally observable Time Entry behavior.
- Confirm switch-gate compatibility criteria are defined.
- Confirm ambiguity handling policy is active (stop and ask; never invent behavior).

## Notes

Implementation tasks and executable test suites are intentionally deferred to `/speckit.tasks` and `/speckit.implement`.

## Terminology Consistency Pass

- "Time Entry" is used consistently for module scope.
- "Parity" refers to externally observable behavior equivalence between legacy and rewritten paths.
- "Contract" refers to request/validation/outcome interface semantics.

## Independent Story Validation Order

1. US1: run service-focused unit tests and verify requirement mapping.
2. US2: run integration parity tests for required fields, date defaults, invalid values, and weekend behavior.
3. US3: run contract tests and verify switch-gate readiness.
