# Implementation Plan: Time Entry Module Rewrite (Phase 3b)

**Branch**: `[005-time-entry-module-rewrite]` | **Date**: 2026-07-08 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/005-time-entry-module-rewrite/spec.md`

## Summary

Define a single-module rewrite specification for the Time Entry Domain from phase 3a boundaries. This plan produces module-scoped functional requirements, behavior-preserving acceptance tests mapped to Time Entry business rules, and a legacy-equivalent Time Entry interface contract for gradual traffic switching. The plan addresses validation-path divergence identified in `SA-008` while preserving legacy semantics for required inputs, date handling, positivity/future-date checks, and weekend warning behavior.

## Technical Context

**Language/Version**: Java 8-era monolith behavior baseline (JSP + DAO + service), captured as implementation-agnostic contract/test artifacts

**Primary Dependencies**: `src/main/webapp/hours.jsp` behavior baseline; `BillingService.validateBillableHour`; phase artifacts from features 001-003

**Storage**: Legacy relational model (`billable_hours` with customer/user/category references) remains baseline for parity specification

**Testing**: Behavior-preserving acceptance parity definitions for Time Entry input, validation, persistence semantics, and visible outcomes

**Target Platform**: Current monolith runtime behavior as parity baseline; contract intended for strangler migration to target architecture

**Project Type**: Documentation-first module rewrite specification and contract planning

**Performance Goals**: Preserve correctness and validation semantics; no new throughput target in this planning phase

**Constraints**: Time Entry module only, no invented behavior, explicit blocker handling for ambiguity, legacy-equivalent interface semantics, and switch-gate compatibility criteria

**Scale/Scope**: Time Entry slice only: hours logging inputs, validation semantics, date defaulting, and output/error behavior used by existing flow

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Constitution file at `.specify/memory/constitution.md` remains template-only with no enforceable project-specific MUST rules.
- Pre-research gate result: PASS by default.
- Feature-level constraints are enforced from `spec.md` and prior-phase evidence.
- Post-design gate result: PASS by default.

## Project Structure

### Documentation (this feature)

```text
specs/005-time-entry-module-rewrite/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── time-entry-contract.md
└── tasks.md  # Produced by /speckit.tasks
```

### Source Code (repository root)

```text
src/
└── main/
    ├── java/com/sourcegraph/demo/bigbadmonolith/
    │   ├── service/BillingService.java
    │   ├── dao/BillableHourDAO.java
    │   └── entity/BillableHour.java
    └── webapp/
        └── hours.jsp

specs/
├── 001-rediscover-legacy-monolith/
├── 002-substitution-audit/
├── 003-define-target-architecture/
├── 004-single-module-rewrite/
└── 005-time-entry-module-rewrite/
```

**Structure Decision**: Documentation-only planning in `specs/005-time-entry-module-rewrite/`, with source files used as evidence anchors for Time Entry behavior parity.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |

## Phase 0 Outcome

- `research.md` captures Time Entry decisions for validation-policy parity, interface compatibility, and ambiguity handling constraints.
- No unresolved technical-context placeholders remain in plan.

## Phase 1 Outcome

- `data-model.md` captures Time Entry entities and parity-critical fields/invariants.
- `contracts/time-entry-contract.md` defines legacy-equivalent Time Entry interface for gradual switching.
- `quickstart.md` defines validation scenarios and parity review workflow.

## Agent Context Update

- No dedicated agent-context update script exists under `.specify/scripts/`.
- Agent context is updated via generated artifacts in `specs/005-time-entry-module-rewrite/`.

## Constitution Re-Check (Post-Design)

- PASS by default (constitution remains template-only).
- Artifacts satisfy phase constraints: single-module scope, no invented behavior, rule traceability, and legacy-equivalent contract intent.
