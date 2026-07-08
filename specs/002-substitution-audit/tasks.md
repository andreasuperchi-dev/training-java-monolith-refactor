# Tasks: Substitution Audit over Rediscovery Artifacts (Phase 1)

**Input**: Design documents from `/specs/002-substitution-audit/`

**Prerequisites**: `plan.md` (required), `spec.md` (required for user stories), `research.md`, `data-model.md`, `contracts/`

**Tests**: No explicit TDD/test-task request was specified in the feature specification; this task list focuses on analysis deliverables and validation workflows.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story?] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., `US1`, `US2`, `US3`)
- Every task includes an exact file path

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Establish substitution-audit document scaffolding and traceability anchors.

- [x] T001 Create substitution-audit deliverable scaffold in `specs/002-substitution-audit/substitution-audit.md`
- [x] T002 [P] Add audit metadata and scope-boundary header section in `specs/002-substitution-audit/substitution-audit.md`
- [x] T003 [P] Add required proposal taxonomy definitions (`keep-as-is`, `replace-with-library`, `replace-with-platform`, `retire`) in `specs/002-substitution-audit/substitution-audit.md`
- [x] T004 Add row-reference ID convention and citation syntax section in `specs/002-substitution-audit/substitution-audit.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Build shared structures required by all user stories.

**CRITICAL**: No user story phase should begin until this phase is complete.

- [x] T005 Build canonical rediscovery source index for traceability mapping in `specs/002-substitution-audit/substitution-audit.md`
- [x] T006 [P] Create mandatory substitution-audit table skeleton with exact columns in `specs/002-substitution-audit/substitution-audit.md`
- [x] T007 [P] Create mandatory category coverage tracker section in `specs/002-substitution-audit/substitution-audit.md`
- [x] T008 Add assumption-labeling and unresolved-evidence handling rules in `specs/002-substitution-audit/substitution-audit.md`
- [x] T009 Add scope-guard checklist to block architecture-design drift in `specs/002-substitution-audit/substitution-audit.md`

**Checkpoint**: Table schema, category tracker, and traceability framework are ready for story-level execution.

---

## Phase 3: User Story 1 - Produce a Traceable Substitution Table (Priority: P1) MVP

**Goal**: Produce a substitution-audit table where every row is traceable to specific rediscovery entries.

**Independent Test**: A reviewer samples substitution rows and validates required columns plus evidence links for each row.

- [x] T010 [US1] Populate substitution rows for home-grown code candidates with proposal/reason/trade-off in `specs/002-substitution-audit/substitution-audit.md`
- [x] T011 [P] [US1] Populate substitution rows for integration/runtime/store candidates with proposal/reason/trade-off in `specs/002-substitution-audit/substitution-audit.md`
- [x] T012 [US1] Add rediscovery entry references for every substitution row in `specs/002-substitution-audit/substitution-audit.md`
- [x] T013 [US1] Validate proposal values conform exactly to allowed taxonomy in `specs/002-substitution-audit/substitution-audit.md`
- [x] T014 [US1] Add assumption flags and notes for any partial-evidence rows in `specs/002-substitution-audit/substitution-audit.md`
- [x] T015 [US1] Run row-level traceability sampling log and outcomes section in `specs/002-substitution-audit/substitution-audit.md`

**Checkpoint**: Substitution table is complete, taxonomy-valid, and row-traceable.

---

## Phase 4: User Story 2 - Ensure Full Coverage of Risk Categories (Priority: P1)

**Goal**: Ensure all required risk categories are explicitly covered or marked as evidence-backed not-found.

**Independent Test**: A reviewer checks each required category and confirms row coverage or not-found statement with references.

- [x] T016 [US2] Map substitution rows to required category `home-grown code with modern equivalents` in `specs/002-substitution-audit/substitution-audit.md`
- [x] T017 [P] [US2] Map substitution rows to required category `dated integrations` in `specs/002-substitution-audit/substitution-audit.md`
- [x] T018 [P] [US2] Map substitution rows to required category `unfit data stores` in `specs/002-substitution-audit/substitution-audit.md`
- [x] T019 [P] [US2] Map substitution rows to required category `EOL runtimes/frameworks` in `specs/002-substitution-audit/substitution-audit.md`
- [x] T020 [P] [US2] Map substitution rows to required category `cloud-hostile operational assumptions` in `specs/002-substitution-audit/substitution-audit.md`
- [x] T021 [US2] Add explicit evidence-backed `not-found` entries for any uncovered category in `specs/002-substitution-audit/substitution-audit.md`
- [x] T022 [US2] Add category completeness summary table with pass/fail status in `specs/002-substitution-audit/substitution-audit.md`

**Checkpoint**: Category coverage is complete and auditable across all required domains.

---

## Phase 5: User Story 3 - Preserve Audit-Only Scope Boundaries (Priority: P2)

**Goal**: Ensure output stays audit-only and excludes architecture design content.

**Independent Test**: A reviewer verifies no target architecture, migration sequencing, or implementation redesign appears.

- [x] T023 [US3] Run scope-boundary scrub to remove target-architecture statements in `specs/002-substitution-audit/substitution-audit.md`
- [x] T024 [P] [US3] Run scope-boundary scrub to remove migration sequencing language in `specs/002-substitution-audit/substitution-audit.md`
- [x] T025 [P] [US3] Run scope-boundary scrub to remove implementation-level redesign statements in `specs/002-substitution-audit/substitution-audit.md`
- [x] T026 [US3] Add explicit out-of-scope declaration section for architecture and migration design in `specs/002-substitution-audit/substitution-audit.md`
- [x] T027 [US3] Add final audit-only compliance checklist and reviewer signoff lines in `specs/002-substitution-audit/substitution-audit.md`

**Checkpoint**: Deliverable remains classification-focused and compliant with audit-only constraint.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final consistency, contract compliance, and handoff readiness checks.

- [x] T028 [P] Run terminology consistency pass (`legacy element`, `proposal`, `trade-off`, `reference`) in `specs/002-substitution-audit/substitution-audit.md`
- [x] T029 [P] Validate substitution output against contract requirements in `specs/002-substitution-audit/contracts/substitution-audit-contract.md`
- [x] T030 Validate quickstart scenarios and record expected validation outcomes in `specs/002-substitution-audit/quickstart.md`
- [x] T031 Add final reviewer handoff checklist for substitution audit acceptance in `specs/002-substitution-audit/quickstart.md`
- [x] T032 Verify every substitution row has at least one rediscovery reference and no uncited rows remain in `specs/002-substitution-audit/substitution-audit.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: No dependencies; start immediately.
- **Phase 2 (Foundational)**: Depends on Phase 1; blocks all user stories.
- **Phase 3 (US1)**: Depends on Phase 2; recommended MVP first.
- **Phase 4 (US2)**: Depends on Phase 2; can run in parallel with US1 once foundational setup is complete.
- **Phase 5 (US3)**: Depends on Phase 2 and should run after core row/category population is available.
- **Phase 6 (Polish)**: Depends on completion of Phases 3-5.

### User Story Dependency Graph

- **US1**: Independent after foundational setup; required for MVP.
- **US2**: Depends on having substitution rows (US1 output) to complete category mapping.
- **US3**: Depends on populated audit content (US1/US2 outputs) to enforce scope boundaries.

Suggested completion order (single-threaded): `US1 -> US2 -> US3`

### Within Each User Story

- Populate content before validation passes.
- Add references before traceability checks.
- Resolve assumptions before final story checkpoint.

---

## Parallel Execution Examples

### User Story 1

```bash
Task: "T010 [US1] Populate substitution rows for home-grown code candidates in specs/002-substitution-audit/substitution-audit.md"
Task: "T011 [US1] Populate substitution rows for integration/runtime/store candidates in specs/002-substitution-audit/substitution-audit.md"
```

### User Story 2

```bash
Task: "T018 [US2] Map rows to unfit data stores category in specs/002-substitution-audit/substitution-audit.md"
Task: "T019 [US2] Map rows to EOL runtimes/frameworks category in specs/002-substitution-audit/substitution-audit.md"
Task: "T020 [US2] Map rows to cloud-hostile operational assumptions category in specs/002-substitution-audit/substitution-audit.md"
```

### User Story 3

```bash
Task: "T024 [US3] Remove migration-sequencing language in specs/002-substitution-audit/substitution-audit.md"
Task: "T025 [US3] Remove implementation-redesign language in specs/002-substitution-audit/substitution-audit.md"
```

---

## Implementation Strategy

### MVP First (User Story 1)

1. Complete Phase 1 and Phase 2.
2. Complete US1 tasks (T010-T015).
3. Validate row-level traceability and taxonomy conformance.
4. Share MVP substitution table for early reviewer feedback.

### Incremental Delivery

1. Complete Setup + Foundational once.
2. Deliver US1 substitution table.
3. Add US2 category-completeness mapping and not-found coverage.
4. Add US3 audit-only compliance controls.
5. Run Polish phase for final contract and handoff readiness.

### Parallel Team Strategy

1. Contributor A prepares foundational scaffolding (Phase 1-2).
2. Contributor B populates US1 rows while Contributor C prepares US2 category mapping framework.
3. Contributor D performs US3 scope scrub and compliance checks once content stabilizes.
4. Merge and finalize in Phase 6.