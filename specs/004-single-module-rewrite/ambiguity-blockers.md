# Reporting Ambiguity Blockers

Use this log when reporting behavior is ambiguous, missing from business rules, or contradictory across evidence sources.

## Rule

Do not invent behavior. Block and clarify before finalizing requirements, tests, or contract semantics.

## Blocker Template

| Blocker ID | Trigger Artifact | Ambiguity Description | Candidate Interpretations | Required Owner | Status | Resolution Reference |
|------------|------------------|-----------------------|---------------------------|----------------|--------|----------------------|
| AB-### | path/to/file | Describe ambiguity | A/B options | Product/Domain owner | Open | (fill when resolved) |

## Current Blockers

| Blocker ID | Trigger Artifact | Ambiguity Description | Candidate Interpretations | Required Owner | Status | Resolution Reference |
|------------|------------------|-----------------------|---------------------------|----------------|--------|----------------------|
| AB-001 | `open-questions.md#OQ-007` | Canonical ownership split between JSP SQL and service reporting logic | A: JSP observed behavior is canonical for parity; B: service path is canonical | App maintainer | Open | Pending governance confirmation |

## Handling Workflow

1. Detect ambiguity during requirement/test/contract drafting.
2. Add blocker entry with evidence links.
3. Halt finalization of affected artifact sections.
4. Request clarification from named owner.
5. Update resolved behavior and reference closure evidence.
