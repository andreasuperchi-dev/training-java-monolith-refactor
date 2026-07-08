# Quickstart Validation Guide: Substitution Audit (Phase 1)

This guide validates the planning artifacts and expected implementation outcome for the substitution audit feature.

## Prerequisites

- Existing rediscovery artifacts present under `specs/001-rediscover-legacy-monolith/`.
- Current feature directory: `specs/002-substitution-audit/`.

## Planned Deliverables

- `plan.md`
- `research.md`
- `data-model.md`
- `contracts/substitution-audit-contract.md`
- `tasks.md` (generated in next command phase)

## Validation Scenario 1: Output Structure Compliance

1. Open substitution audit output artifact (to be produced in implementation phase).
2. Confirm table headers are exactly:
   - legacy element
   - proposal
   - reason
   - trade-off
3. Confirm every `proposal` value is in allowed set.

Expected outcome:
- Output conforms to contract section 1 and 2 in `contracts/substitution-audit-contract.md`.

## Validation Scenario 2: Row-Level Traceability

1. Sample at least 10 substitution rows.
2. For each row, follow the rediscovery reference.
3. Confirm the referenced entry exists and supports the row claim.

Expected outcome:
- At least 95% of sampled rows are traceable and evidence-consistent.

## Validation Scenario 3: Category Coverage Completeness

1. Check output for all five mandatory categories:
   - home-grown code with modern equivalents
   - dated integrations
   - unfit data stores
   - EOL runtimes/frameworks
   - cloud-hostile operational assumptions
2. Confirm each category is represented as either:
   - covered by one or more rows, or
   - explicit evidence-backed not-found statement.

Expected outcome:
- 100% category representation achieved.

## Validation Scenario 4: Scope Boundary Guard

1. Scan output for prohibited content:
   - architecture diagrams
   - migration sequencing
   - component topology redesign
2. Confirm recommendations remain classification-oriented only.

Expected outcome:
- No architecture-design content appears.

## Validation Outcome Recording (Expected)

| Scenario | Expected Result | Evidence Location |
|----------|-----------------|-------------------|
| Scenario 1: Output Structure Compliance | PASS | `substitution-audit.md` table headers and taxonomy section |
| Scenario 2: Row-Level Traceability | PASS (>=95%) | `substitution-audit.md` traceability sampling log |
| Scenario 3: Category Coverage Completeness | PASS | `substitution-audit.md` category tracker and completeness summary |
| Scenario 4: Scope Boundary Guard | PASS | `substitution-audit.md` scope guard checklist |

## Final Reviewer Handoff Checklist

- Confirm substitution table columns exactly match contract (`legacy element`, `proposal`, `reason`, `trade-off`).
- Confirm every row includes one or more specific rediscovery references.
- Confirm all proposals use allowed taxonomy values only.
- Confirm all five required categories are covered or explicitly marked not-found with evidence.
- Confirm assumption-marked rows are explicitly labeled and bounded.
- Confirm no target architecture, migration sequencing, or implementation redesign appears.
- Confirm sampled traceability rate is >=95%.
- Confirm contract compliance against `contracts/substitution-audit-contract.md`.

## Handoff Notes

- Use `contracts/substitution-audit-contract.md` as the authoritative acceptance contract during implementation and review.
- Use `data-model.md` to validate row schema and relationship completeness.
- Use `research.md` to justify output design decisions during audit review.