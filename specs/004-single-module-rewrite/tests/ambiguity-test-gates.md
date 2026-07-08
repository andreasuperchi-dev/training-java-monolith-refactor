# Ambiguity Test Gates (US2)

## Purpose

Define how test authoring/execution halts when required behavior is ambiguous.

## Gates

### Gate AT-1: Rule Mapping Completeness
- Each test case must cite one or more BR anchors.
- If missing, test remains BLOCKED.

### Gate AT-2: Expected Outcome Certainty
- Expected result must be derivable from legacy behavior evidence.
- If uncertain, open blocker entry and halt affected test finalization.

### Gate AT-3: Conflicting Legacy Signals
- If service/JSP/report-contract evidence conflicts, default to blocker and request owner clarification.

### Gate AT-4: Execution Readiness
- Only PASS or approved deferred blockers may proceed to parity execution.

## Escalation

- Record blocker in `ambiguity-blockers.md`.
- Reference blocked test id and required owner.
- Resume after documented resolution.
