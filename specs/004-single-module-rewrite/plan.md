# Implementation Plan: Reporting Module Rewrite (Phase 3b)

**Branch**: `[004-single-module-rewrite]` | **Date**: 2026-07-08 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/004-single-module-rewrite/spec.md`

## Summary

Define a single-module rewrite specification for the Reporting Query Domain from phase 3a boundaries. The plan produces module-scoped requirements, behavior-preserving acceptance tests mapped to phase-1 business rules, and a legacy-equivalent interface contract that supports gradual traffic switching. Scope is limited to reporting module behavior and excludes architecture redesign and multi-module rewrite decomposition.

## Technical Context

**Language/Version**: Java 8-era web monolith behavior baseline (JSP + servlet container), documented as implementation-agnostic module contract artifacts

**Primary Dependencies**: Existing report behavior in `src/main/webapp/reports.jsp`; service-level report semantics in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java`; phase artifacts from features 001-003

**Storage**: Legacy baseline uses relational Derby-backed tables (`customers`, `users`, `billing_categories`, `billable_hours`) for report reads; this phase defines behavior contracts only

**Testing**: Behavior-preserving acceptance test definitions and parity scenarios (legacy vs rewritten reporting module)

**Target Platform**: Current monolith runtime baseline for behavior capture, with contract intended for strangler migration in cloud-managed target architecture

**Project Type**: Documentation-first module rewrite specification and contract planning

**Performance Goals**: Preserve externally observed reporting response behavior and totals semantics; no new throughput target introduced in this planning phase

**Constraints**: Single-module scope only (Reporting Query Domain), no invented behavior, stop on ambiguity, all functional requirements map to `BR-*`, contract must remain legacy-equivalent for gradual switching

**Scale/Scope**: Reporting slice only: customer bill report, monthly summary report, and revenue summary report interfaces and outcomes

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Constitution file at `.specify/memory/constitution.md` is currently a template with placeholder content and no enforceable project-specific MUST rules.
- Pre-research gate result: PASS by default.
- Feature-level governing constraints are enforced from `spec.md` and prior phase artifacts.
- Post-design gate result: PASS by default.

## Project Structure

### Documentation (this feature)

```text
specs/004-single-module-rewrite/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── reporting-query-contract.md
└── tasks.md  # Produced by /speckit.tasks, not by /speckit.plan
```

### Source Code (repository root)

```text
src/
└── main/
    ├── java/com/sourcegraph/demo/bigbadmonolith/
    │   ├── service/BillingService.java
    │   ├── dao/
    │   └── entity/
    └── webapp/
        ├── reports.jsp
        └── WEB-INF/web.xml

specs/
├── 001-rediscover-legacy-monolith/
├── 002-substitution-audit/
├── 003-define-target-architecture/
└── 004-single-module-rewrite/
```

**Structure Decision**: Keep this phase documentation-only in `specs/004-single-module-rewrite/`, using source files only as behavior evidence for reporting-module requirement and contract parity.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |

## Phase 0 Outcome

- `research.md` resolves reporting-module technical decisions and ambiguity handling boundaries using evidence from phase 1, phase 2, and phase 3a.
- No unresolved technical-context placeholders remain in `plan.md`.

## Phase 1 Outcome

- `data-model.md` defines reporting-module data entities, projections, and parity-relevant fields.
- `contracts/reporting-query-contract.md` defines legacy-equivalent reporting interface contract for gradual traffic switching.
- `quickstart.md` defines parity validation scenarios and acceptance-check workflow for the reporting rewrite slice.

## Agent Context Update

- No dedicated agent-context update script exists under `.specify/scripts/`.
- Agent context is updated through generated phase artifacts in `specs/004-single-module-rewrite/` for downstream `/speckit.tasks`.

## Constitution Re-Check (Post-Design)

- PASS by default (constitution file remains template-only).
- Produced artifacts satisfy phase constraints: single-module scope, no invented behavior, business-rule traceability, and legacy-equivalent interface contract intent.
