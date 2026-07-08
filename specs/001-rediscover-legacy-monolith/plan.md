# Implementation Plan: Rediscover Legacy Monolith

**Branch**: `[001-rediscover-legacy-monolith]` | **Date**: 2026-07-08 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/001-rediscover-legacy-monolith/spec.md`

## Summary

Produce evidence-based rediscovery documentation for the legacy billing monolith without changing implementation behavior. The plan delivers four analysis artifacts: a business-rules narrative with concrete repository references, a data-model and invariants summary, an integration inventory, and an explicit open-questions list for anything code/configuration cannot prove.

## Technical Context

**Language/Version**: Java 21, JSP, Jakarta EE 10

**Primary Dependencies**: Open Liberty Gradle plugin 3.8.2, Apache Derby 10.17.1.0, Apache Commons DBCP2 2.11.0, Apache Commons Pool2 2.12.0, Joda-Time 2.12.5, Jakarta Servlet/CDI APIs

**Storage**: Embedded Derby at `./data/bigbadmonolith` (fallback mode) or Liberty-managed Derby datasource at `${server.output.dir}/data/bigbadmonolith`

**Testing**: Gradle + JUnit 5 (`./gradlew test`)

**Target Platform**: Server-side Java web app on Open Liberty (default HTTP 9080, HTTPS 9443) with WAR packaging

**Project Type**: Monolithic web application (JSP presentation + DAO/service backend)

**Performance Goals**: Analysis throughput sufficient to produce full evidence-backed domain documentation in one pass; verification success metric aligned with spec SC-007 (>=95% reviewer traceability on sampled rules)

**Constraints**: No code/config/runtime asset modifications; no modernization recommendations; no inferred behavior stated as fact; all findings require concrete code/config evidence

**Scale/Scope**: Current repository scope: core Java package under `src/main/java/com/sourcegraph/demo/bigbadmonolith`, JSP views in `src/main/webapp`, Liberty config in `src/main/liberty/config`, and Gradle build/runtime scripts

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Constitution file at `.specify/memory/constitution.md` is a placeholder template with no ratified principles or enforceable gates.
- Gate evaluation result (pre-research): PASS by default (no active constitutional constraints defined).
- Working policy for this feature comes from the approved feature specification constraints and success criteria.
- Post-design re-check: PASS by default (same condition, no constitutional rule set present).

## Project Structure

### Documentation (this feature)

```text
specs/001-rediscover-legacy-monolith/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/com/sourcegraph/demo/bigbadmonolith/
│   │   ├── StartupListener.java
│   │   ├── dao/
│   │   ├── entity/
│   │   ├── service/
│   │   └── util/
│   ├── webapp/
│   │   ├── index.jsp
│   │   ├── customers.jsp
│   │   ├── users.jsp
│   │   ├── categories.jsp
│   │   ├── hours.jsp
│   │   ├── reports.jsp
│   │   └── WEB-INF/web.xml
│   └── liberty/config/
│       ├── server.xml
│       ├── bootstrap.properties
│       └── jvm.options
└── test/
    └── java/

gradle/
├── wrapper/
└── ...

build.gradle
README.md
SETUP.md
liberty-dev.sh
liberty-start.sh
```

**Structure Decision**: Single-project monolith structure. Documentation and validation focus on existing Java/JSP/Liberty artifacts; no source layout changes are required.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |

## Phase 0 Outcome

- `research.md` generated with explicit decision log for evidence confidence, invariant classification, and integration inventory methodology.
- No unresolved technical-context placeholders remain.

## Phase 1 Outcome

- `data-model.md` generated with entities, relationships, invariants, enforcement-source labeling, and open questions.
- `contracts/` generated with interface contracts and integration inventory.
- `quickstart.md` generated with validation scenarios and expected outcomes.

## Agent Context Update

- No dedicated repository script for "update agent context" was found under `.specify/scripts/`.
- Context update captured by writing completed Phase 0/1 artifacts in the active feature directory for downstream `speckit.tasks` consumption.

## Constitution Re-Check (Post-Design)

- Result: PASS by default (constitution file remains a template without enforceable gates).
- Design artifacts comply with spec constraints: no implementation changes proposed, no modernization recommendations included.
