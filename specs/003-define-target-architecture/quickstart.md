# Quickstart Validation Guide: Target Architecture for Modernized System

This guide validates architecture planning artifacts for feature `003-define-target-architecture`.

## Prerequisites

- Input artifacts available:
  - `specs/001-rediscover-legacy-monolith/*`
  - `specs/002-substitution-audit/substitution-audit.md`
- Current feature artifacts generated:
  - `specs/003-define-target-architecture/plan.md`
  - `specs/003-define-target-architecture/research.md`
  - `specs/003-define-target-architecture/data-model.md`
  - `specs/003-define-target-architecture/contracts/target-architecture-contracts.md`

## Validation Scenario 1: Runtime and Platform Decision Coverage

1. Open `plan.md` and confirm explicit target runtime/platform architecture choices.
2. Verify each runtime/platform choice has traceability to substitution rows or preserved rules.

Expected outcome:
- Runtime/platform section is complete and traceable to `SA-002`, `SA-003`, `SA-010`, `SA-012`, and `BR-015` evidence anchors.

## Validation Scenario 2: Module/Service Boundary Completeness

1. Review boundary definitions in `plan.md` summary/structure and supporting rationale in `research.md`.
2. Confirm boundaries cover Billing, Time Entry, Customer, Reporting, Identity/Access, and Integration Adapter concerns.

Expected outcome:
- Boundary model is explicit and linked to preserved business behavior evidence (`BR-001` to `BR-013`) and substitution findings (`SA-001`, `SA-008`).

## Validation Scenario 3: Data Model and Migration Path Validity

1. Review `data-model.md` target domains and migration table.
2. Confirm each legacy structure has a target mapping and preservation goal.
3. Verify mappings cite preserved rule evidence or substitution rows.

Expected outcome:
- 100% in-scope legacy domains are mapped with traceability and migration guardrails.

## Validation Scenario 4: Integration Contract Replacement Coverage

1. Review `contracts/target-architecture-contracts.md` entries C-001 through C-007.
2. Cross-check that each flagged legacy integration substitution row is covered.

Expected outcome:
- Every flagged integration row has a replacement contract definition.

## Validation Scenario 5: Cross-Cutting Concerns and Migration Strategy

1. Verify architecture decisions include auth, logging, config, secrets, observability.
2. Verify migration strategy defaults to strangler-fig and remains architecture-only.

Expected outcome:
- Cross-cutting coverage complete and migration strategy stated with no implementation task breakdown.

## Validation Scenario 6: Risk Register Readiness

1. Confirm `plan.md` includes architecture risks with mitigation direction.
2. Validate risks cover platform, data migration, integration drift, and rule-preservation regression.

Expected outcome:
- High-impact risks are listed and mitigation intent is documented.

## Traceability Sampling Procedure

1. Sample at least 12 architecture decisions across runtime, boundaries, data, integration, cross-cutting, and migration sections.
2. For each sample, verify at least one source trace points to:
   - a preserved business rule (`BR-*`) or
   - a substitution-audit row (`SA-*`).

Expected outcome:
- >=95% sampled decisions are traceable and evidence-consistent (`SC-006`).

## Reviewer Handoff Checklist

- Confirm architecture remains architecture-only (no code, no rewrite task breakdowns).
- Confirm every major decision includes `BR-*` or `SA-*` traceability.
- Confirm replacement contracts cover all flagged integration substitutions.
- Confirm data model mapping preserves bill, reporting, and identity semantics.
- Confirm strangler-fig is declared as default migration strategy.
- Confirm risk register includes mitigation direction for high-impact risks.

## Notes

Implementation sequencing, per-module rewrite tasks, and code-level design details are intentionally deferred to subsequent phases (`/speckit.tasks` and `/speckit.implement`).

## Validation Outcome Recording (Expected)

| Scenario | Expected Result | Evidence Location |
|----------|-----------------|-------------------|
| Scenario 1: Runtime and Platform Decision Coverage | PASS | `architecture.md` sections 3 and 4 |
| Scenario 2: Module/Service Boundary Completeness | PASS | `architecture.md` section 5 |
| Scenario 3: Data Model and Migration Path Validity | PASS | `architecture.md` section 6 and `data-model.md` |
| Scenario 4: Integration Contract Replacement Coverage | PASS | `architecture.md` section 7 and `contracts/target-architecture-contracts.md` |
| Scenario 5: Cross-Cutting Concerns and Migration Strategy | PASS | `architecture.md` sections 8 and 9 |
| Scenario 6: Risk Register Readiness | PASS | `architecture.md` section 10 |
| Traceability Sampling Procedure | PASS (>=95%) | `architecture.md` sections 3, 6, 7, 8, 9, 13 |

## Final Reviewer Handoff Checklist

- Confirm the architecture document remains architecture-only (no code and no per-module rewrite task plan).
- Confirm every major architecture decision has one or more `BR-*` or `SA-*` traces.
- Confirm all flagged integration-related substitution rows are mapped to replacement contracts.
- Confirm target data domains and migration guardrails preserve bill/report/identity semantics.
- Confirm cross-cutting concern coverage includes auth, logging, config, secrets, and observability.
- Confirm strangler-fig is the declared default migration strategy.
- Confirm risk register includes high-impact architecture risks with mitigation direction.