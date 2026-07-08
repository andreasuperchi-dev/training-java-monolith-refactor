# Tasks: Rediscover Legacy Monolith

**Input**: Design documents from `/specs/001-rediscover-legacy-monolith/`

**Prerequisites**: `plan.md` (required), `spec.md` (required for user stories), `research.md`, `data-model.md`, `contracts/`

**Tests**: No explicit TDD/test-task request was specified in the feature specification; this task list focuses on analysis deliverables and validation workflows.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story?] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., `US1`, `US2`, `US3`)
- Every task includes an exact file path

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Establish rediscovery documentation scaffolding and traceability format.

- [x] T001 Create business-rules deliverable scaffold in `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T002 Create open-questions deliverable scaffold in `specs/001-rediscover-legacy-monolith/open-questions.md`
- [x] T003 [P] Create evidence-citation convention section in `specs/001-rediscover-legacy-monolith/research.md`
- [x] T004 [P] Add deliverables index section to `specs/001-rediscover-legacy-monolith/quickstart.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Build shared evidence inventory and traceability controls required by all user stories.

**CRITICAL**: No user story phase should begin until this phase is complete.

- [x] T005 Build canonical source-file inventory table in `specs/001-rediscover-legacy-monolith/research.md`
- [x] T006 [P] Add confidence labeling rubric (`E1`-`E4`) with examples in `specs/001-rediscover-legacy-monolith/research.md`
- [x] T007 [P] Define enforcement-source taxonomy (application validation, defensive code, DB constraint, mixed) in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T008 Add cross-artifact traceability matrix shell in `specs/001-rediscover-legacy-monolith/quickstart.md`
- [x] T009 Add unresolved-evidence handling rules to `specs/001-rediscover-legacy-monolith/open-questions.md`

**Checkpoint**: Shared rubric, inventory, and traceability structure are ready for all stories.

---

## Phase 3: User Story 1 - Validate Business Rules from Code (Priority: P1) MVP

**Goal**: Produce a plain-English business-rules document where every rule has concrete code references and examples.

**Independent Test**: A reviewer can randomly sample rules from `business-rules.md` and verify each rule statement and example directly against cited source locations.

- [x] T010 [US1] Extract billing workflow rules and evidence into `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T011 [P] [US1] Extract customer and user lifecycle rules with source references into `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T012 [P] [US1] Extract category/rate and reporting computation rules with source references into `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T013 [US1] Add at least one concrete, evidence-backed example per documented rule in `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T014 [US1] Annotate each rule with confidence level and weakest-link rationale in `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T015 [US1] Add contradiction handling section with explicit contested findings in `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T016 [US1] Record unresolved business-rule ambiguities discovered during extraction in `specs/001-rediscover-legacy-monolith/open-questions.md`

**Checkpoint**: `business-rules.md` is reviewer-traceable and independently verifiable.

---

## Phase 4: User Story 2 - Understand Domain Data Model and Invariants (Priority: P1)

**Goal**: Produce a data-model summary with entities, relationships, and invariants labeled by enforcement source.

**Independent Test**: A reviewer can validate each entity, relationship, and invariant in `data-model.md` and determine enforcement source without additional interpretation.

- [x] T017 [US2] Expand entity attribute completeness checks for `User`, `Customer`, `BillingCategory`, and `BillableHour` in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T018 [P] [US2] Add relationship cardinality and join-evidence cross-checks in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T019 [US2] Normalize invariant entries to include enforcement source and evidence link in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T020 [P] [US2] Add dedicated subsection for defensive-only invariants in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T021 [P] [US2] Add dedicated subsection for DB-constraint-only invariants in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T022 [US2] Document mixed-enforcement invariants and precedence notes in `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T023 [US2] Add unresolved data-model questions and closure criteria in `specs/001-rediscover-legacy-monolith/open-questions.md`

**Checkpoint**: `data-model.md` independently supports entity/invariant review and enforcement-source auditing.

---

## Phase 5: User Story 3 - Audit External Dependencies and Unknowns (Priority: P2)

**Goal**: Produce a complete integration inventory and explicit open-questions list for uncertain behavior.

**Independent Test**: A reviewer can verify each inventory entry (external system, filesystem path, hard-coded endpoint) and confirm unresolved findings are explicitly tracked as open questions.

- [x] T024 [US3] Reconcile integration categories and confidence labels across all entries in `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`
- [x] T025 [P] [US3] Complete filesystem dependency coverage review and evidence references in `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`
- [x] T026 [P] [US3] Complete hard-coded endpoint coverage review and evidence references in `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`
- [x] T027 [US3] Add negative-findings validation notes (absence evidence) in `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`
- [x] T028 [US3] Consolidate integration-related unknowns into actionable question entries in `specs/001-rediscover-legacy-monolith/open-questions.md`
- [x] T029 [US3] Add cross-links between interface contracts and integration inventory in `specs/001-rediscover-legacy-monolith/contracts/ui-and-reporting-contracts.md`
- [x] T030 [US3] Add final completeness check section for inventory scope in `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`

**Checkpoint**: Integration scope is documented end-to-end; unknowns are explicit and not guessed.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final consistency and readiness checks across all deliverables.

- [x] T031 [P] Run cross-artifact terminology consistency pass across `specs/001-rediscover-legacy-monolith/business-rules.md`
- [x] T032 [P] Run cross-artifact terminology consistency pass across `specs/001-rediscover-legacy-monolith/data-model.md`
- [x] T033 [P] Run cross-artifact terminology consistency pass across `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`
- [x] T034 Validate traceability sampling workflow and expected outcomes in `specs/001-rediscover-legacy-monolith/quickstart.md`
- [x] T035 Add final reviewer handoff checklist in `specs/001-rediscover-legacy-monolith/quickstart.md`
- [x] T036 Verify every unresolved item is captured and closure-ready in `specs/001-rediscover-legacy-monolith/open-questions.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: No dependencies; start immediately.
- **Phase 2 (Foundational)**: Depends on Phase 1; blocks all user stories.
- **Phase 3 (US1)**: Depends on Phase 2; recommended MVP first.
- **Phase 4 (US2)**: Depends on Phase 2; can run in parallel with US1 if staffed.
- **Phase 5 (US3)**: Depends on Phase 2; can run in parallel with US1/US2 if staffed.
- **Phase 6 (Polish)**: Depends on completion of desired user story phases.

### User Story Dependency Graph

- **US1**: Independent after foundational setup; recommended first for MVP.
- **US2**: Independent after foundational setup; no hard dependency on US1 outputs.
- **US3**: Independent after foundational setup; no hard dependency on US1/US2 outputs.

Suggested completion order (single-threaded): `US1 -> US2 -> US3`

### Within Each User Story

- Evidence extraction before confidence labeling.
- Confidence labeling before contradiction/open-question finalization.
- Open-question entries must be added before story completion.

---

## Parallel Execution Examples

### User Story 1

```bash
Task: "T011 [US1] Extract customer and user lifecycle rules with source references in specs/001-rediscover-legacy-monolith/business-rules.md"
Task: "T012 [US1] Extract category/rate and reporting computation rules with source references in specs/001-rediscover-legacy-monolith/business-rules.md"
```

### User Story 2

```bash
Task: "T020 [US2] Add defensive-only invariants subsection in specs/001-rediscover-legacy-monolith/data-model.md"
Task: "T021 [US2] Add DB-constraint-only invariants subsection in specs/001-rediscover-legacy-monolith/data-model.md"
```

### User Story 3

```bash
Task: "T025 [US3] Complete filesystem dependency coverage review in specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md"
Task: "T026 [US3] Complete hard-coded endpoint coverage review in specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md"
```

---

## Implementation Strategy

### MVP First (User Story 1)

1. Complete Phase 1 and Phase 2.
2. Complete US1 tasks (T010-T016).
3. Validate US1 independently using its test criterion.
4. Share business-rules deliverable for early reviewer feedback.

### Incremental Delivery

1. Complete Setup + Foundational once.
2. Deliver US1 (business rules).
3. Deliver US2 (data model + invariant enforcement clarity).
4. Deliver US3 (integration inventory + unknowns).
5. Run Polish phase for final cross-artifact consistency.

### Parallel Team Strategy

1. One contributor executes Phase 1-2.
2. After foundation:
   - Contributor A: US1
   - Contributor B: US2
   - Contributor C: US3
3. Merge outputs and execute Phase 6 jointly.
