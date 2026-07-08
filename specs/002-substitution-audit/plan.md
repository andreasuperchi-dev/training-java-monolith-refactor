# Implementation Plan: Substitution Audit over Rediscovery Artifacts (Phase 1)

**Branch**: `[002-substitution-audit]` | **Date**: 2026-07-08 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/002-substitution-audit/spec.md`

## Summary

Produce a phase-1 substitution audit deliverable that classifies legacy elements using a constrained proposal taxonomy (`keep-as-is`, `replace-with-library`, `replace-with-platform`, `retire`) and links every row to specific rediscovery evidence from feature `001-rediscover-legacy-monolith`. The planning approach emphasizes strict audit-only scope, complete coverage across five required risk categories, and row-level traceability without proposing target architecture.

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: Markdown documentation artifacts in repository workflow context (no runtime implementation language change in this phase)

**Primary Dependencies**: Existing rediscovery artifacts under `specs/001-rediscover-legacy-monolith/`; Spec Kit planning/tasking scripts

**Storage**: N/A (documentation-only output)

**Testing**: Contract and traceability validation via checklist-driven review (no code test harness required in planning phase)

**Target Platform**: Repository documentation under `specs/002-substitution-audit/`

**Project Type**: Analysis/documentation feature

**Performance Goals**: Reviewer can validate sampled substitution rows with >=95% traceability success (aligned to SC-005)

**Constraints**: Audit-only output; no architecture design; no migration sequencing; proposals limited to four allowed values; every row must link to specific rediscovery entry

**Scale/Scope**: Substitution assessment over rediscovery artifacts generated in feature `001-rediscover-legacy-monolith`, with mandatory coverage across five risk categories

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Constitution at `.specify/memory/constitution.md` is currently an unfilled template with placeholder tokens and no enforceable principles.
- Pre-research gate result: PASS by default (no active constitutional MUST constraints available).
- Effective governing constraints come from `spec.md` requirements and explicit user constraint: audit only, no architecture design.
- Post-design re-check result: PASS by default (same condition).

## Project Structure

### Documentation (this feature)

```text
specs/002-substitution-audit/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

```text
specs/
├── 001-rediscover-legacy-monolith/
│   ├── business-rules.md
│   ├── data-model.md
│   ├── open-questions.md
│   ├── research.md
│   ├── quickstart.md
│   └── contracts/
│       ├── integration-inventory.md
│       └── ui-and-reporting-contracts.md
└── 002-substitution-audit/
    ├── plan.md
    ├── research.md
    ├── data-model.md
    ├── quickstart.md
    ├── contracts/
    │   └── substitution-audit-contract.md
    └── tasks.md
```

**Structure Decision**: Documentation-only feature structure under `specs/002-substitution-audit` that consumes evidence from `specs/001-rediscover-legacy-monolith` and produces audit artifacts/contracts without source-code changes.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |

## Phase 0 Outcome

- `research.md` generated with decisions covering scope boundaries, proposal taxonomy, traceability granularity, empty-category handling, and assumption labeling.
- All Technical Context fields resolved; no `NEEDS CLARIFICATION` items remain for planning.

## Phase 1 Outcome

- `data-model.md` generated to define audit row schema, coverage categories, rediscovery references, and scope boundary rules.
- `contracts/substitution-audit-contract.md` generated with output table contract, allowed proposal set, traceability and category coverage requirements, and disallowed scope items.
- `quickstart.md` generated with validation scenarios for structure compliance, traceability sampling, category completeness, and scope guard checks.

## Agent Context Update

- No dedicated repository script for agent-context refresh was found under `.specify/scripts/`.
- Planning context updated through generated artifacts in `specs/002-substitution-audit/`, ready for `/speckit.tasks` consumption.

## Constitution Re-Check (Post-Design)

- Result: PASS by default because constitution remains an uninstantiated template.
- Produced artifacts respect active governing constraints from spec: audit-only output and no architecture design.
