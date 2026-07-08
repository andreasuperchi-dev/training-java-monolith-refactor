# Tasks: Time Entry Module Rewrite (Phase 3b)

**Input**: Design documents from `/specs/005-time-entry-module-rewrite/`
**Prerequisites**: `plan.md`, `spec.md`, `research.md`, `data-model.md`, `contracts/time-entry-contract.md`, `quickstart.md`

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Prepare workspace and test scaffolding for Time Entry rewrite execution

- [X] T001 Create Time Entry implementation notes scaffold in `specs/005-time-entry-module-rewrite/implementation-notes.md`
- [X] T002 [P] Create Time Entry integration test package scaffold in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/package-info.java`
- [X] T003 [P] Create Time Entry contract test package scaffold in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/package-info.java`
- [X] T004 [P] Create Time Entry service test package scaffold in `src/test/java/com/sourcegraph/demo/bigbadmonolith/service/package-info.java`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Define shared parity and ambiguity controls required before story implementation

- [X] T005 Create Time Entry requirement-to-rule matrix in `specs/005-time-entry-module-rewrite/traceability-matrix.md`
- [X] T006 [P] Create Time Entry legacy parity fixture catalog in `specs/005-time-entry-module-rewrite/fixtures/legacy-time-entry-scenarios.md`
- [X] T007 [P] Create Time Entry ambiguity blocker register in `specs/005-time-entry-module-rewrite/ambiguity-blockers.md`
- [X] T008 Create Time Entry parity assertion criteria in `specs/005-time-entry-module-rewrite/parity-criteria.md`
- [X] T009 [P] Create Time Entry traffic-switch gates definition in `specs/005-time-entry-module-rewrite/traffic-switch-gates.md`

**Checkpoint**: Foundational parity and governance controls are ready

---

## Phase 3: User Story 1 - Define Time Entry Functional Requirements (Priority: P1) 🎯 MVP

**Goal**: Implement Time Entry behavior in a dedicated service path with preserved validation semantics and traceability

**Independent Test**: Time Entry functional behavior can be executed through service methods and maps to `BR-008`..`BR-011` without non-Time Entry scope leakage

### Tests for User Story 1

- [X] T010 [P] [US1] Add unit tests for Time Entry required input validation behavior in `src/test/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryServiceValidationTest.java`
- [X] T011 [P] [US1] Add unit tests for date defaulting and future-date handling in `src/test/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryServiceDateRulesTest.java`

### Implementation for User Story 1

- [X] T012 [P] [US1] Create Time Entry request model for logging flow in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryRequest.java`
- [X] T013 [P] [US1] Create Time Entry validation result model in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryValidationResult.java`
- [X] T014 [US1] Implement Time Entry validation and log orchestration service in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryService.java`
- [X] T015 [US1] Implement weekend warning parity handling in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryService.java`
- [X] T016 [US1] Add Time Entry rule trace references for implemented behaviors in `specs/005-time-entry-module-rewrite/traceability-matrix.md`
- [X] T017 [US1] Validate US1 requirement coverage against FR-002/FR-003/FR-005/FR-006/FR-007/FR-008 in `specs/005-time-entry-module-rewrite/requirements-validation.md`

**Checkpoint**: Time Entry service behavior is implemented and trace-mapped

---

## Phase 4: User Story 2 - Specify Behavior-Preserving Time Entry Acceptance Tests (Priority: P1)

**Goal**: Establish parity tests that verify legacy and rewritten Time Entry outcomes are equivalent

**Independent Test**: Acceptance parity suite proves equivalent outcomes for required inputs, date behavior, warnings, and persistence success/error paths

### Tests for User Story 2

- [X] T018 [P] [US2] Add integration parity tests for required-field validation outcomes in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryRequiredFieldsParityIT.java`
- [X] T019 [P] [US2] Add integration parity tests for default-date behavior in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryDefaultDateParityIT.java`
- [X] T020 [P] [US2] Add integration parity tests for non-positive and future-date rejection behavior in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryInvalidValuesParityIT.java`
- [X] T021 [P] [US2] Add integration parity tests for weekend warning behavior in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryWeekendWarningParityIT.java`

### Implementation for User Story 2

- [X] T022 [US2] Create Time Entry acceptance test definitions and expected outcomes in `specs/005-time-entry-module-rewrite/tests/acceptance-time-entry.md`
- [X] T023 [US2] Create Time Entry parity assertion checklist for legacy-vs-rewrite comparison in `specs/005-time-entry-module-rewrite/tests/parity-assertions.md`
- [X] T024 [US2] Document ambiguity test gates for unresolved policy behaviors in `specs/005-time-entry-module-rewrite/tests/ambiguity-test-gates.md`
- [X] T025 [US2] Validate US2 coverage against FR-004/SC-002/SC-003 in `specs/005-time-entry-module-rewrite/tests/tests-validation.md`

**Checkpoint**: Time Entry parity test package is complete and independently executable

---

## Phase 5: User Story 3 - Preserve Legacy Time Entry Interface for Gradual Switching (Priority: P2)

**Goal**: Keep hours.jsp interface behavior equivalent while switching execution to the rewritten Time Entry service path

**Independent Test**: Contract and integration tests verify equivalent interface and switch-gate compatibility without observable drift

### Tests for User Story 3

- [X] T026 [P] [US3] Add contract test for Time Entry input schema and required/optional parameters in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/TimeEntryInputContractTest.java`
- [X] T027 [P] [US3] Add contract test for Time Entry success/error response semantics in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/TimeEntryOutcomeContractTest.java`

### Implementation for User Story 3

- [X] T028 [US3] Refactor hours logging flow to call TimeEntryService while preserving interface behavior in `src/main/webapp/hours.jsp`
- [X] T029 [US3] Preserve legacy-equivalent user message semantics for validation and persistence outcomes in `src/main/webapp/hours.jsp`
- [X] T030 [US3] Update Time Entry contract with executed interface parity details in `specs/005-time-entry-module-rewrite/contracts/time-entry-contract.md`
- [X] T031 [US3] Create contract-equivalence checklist for Time Entry interface in `specs/005-time-entry-module-rewrite/contracts/contract-equivalence-checklist.md`
- [X] T032 [US3] Validate US3 coverage against FR-009/FR-011/SC-004 in `specs/005-time-entry-module-rewrite/contracts/contract-validation.md`

**Checkpoint**: Time Entry interface is switch-ready and contract-verified

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Finalize consistency, traceability, and readiness package for Time Entry module handoff

- [X] T033 [P] Run terminology consistency pass across all Time Entry artifacts in `specs/005-time-entry-module-rewrite/quickstart.md`
- [X] T034 [P] Finalize independent story validation execution order in `specs/005-time-entry-module-rewrite/quickstart.md`
- [X] T035 Validate end-to-end requirement/test/contract traceability in `specs/005-time-entry-module-rewrite/traceability-matrix.md`
- [X] T036 Validate unresolved behavior handling and blocker state in `specs/005-time-entry-module-rewrite/ambiguity-blockers.md`
- [X] T037 Execute final readiness review against SC-001..SC-006 in `specs/005-time-entry-module-rewrite/final-readiness-review.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: starts immediately
- **Phase 2 (Foundational)**: depends on Phase 1 completion and blocks user-story work
- **Phase 3 (US1)**: depends on Phase 2 completion
- **Phase 4 (US2)**: depends on Phase 2 and consumes US1 behavior mappings
- **Phase 5 (US3)**: depends on Phase 2 and uses US1/US2 parity outputs
- **Phase 6 (Polish)**: depends on completion of US1, US2, and US3

### User Story Dependencies

- **US1 (P1)**: no dependency on other stories after Foundational
- **US2 (P1)**: depends on US1 service behavior baseline for parity execution
- **US3 (P2)**: depends on US1/US2 outputs for interface switch-gate validation

### Within Each User Story

- Write tests first for that story and confirm they capture expected behavior
- Implement service/interface changes after tests
- Complete story-specific validation artifact before moving on

### Parallel Opportunities

- Setup tasks marked [P] run in parallel
- Foundational tasks `T006`, `T007`, `T009` run in parallel
- US1 test tasks `T010` and `T011` run in parallel
- US2 integration parity tasks `T018`..`T021` run in parallel
- US3 contract tests `T026` and `T027` run in parallel
- Polish tasks `T033` and `T034` run in parallel

---

## Parallel Example: User Story 2

```bash
Task: "T018 Add required-field validation parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryRequiredFieldsParityIT.java"
Task: "T019 Add default-date parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryDefaultDateParityIT.java"
Task: "T020 Add invalid-values parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryInvalidValuesParityIT.java"
Task: "T021 Add weekend-warning parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryWeekendWarningParityIT.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1 and Phase 2
2. Complete US1 tests and service implementation
3. Validate requirement-to-rule traceability
4. Freeze MVP baseline before parity expansion

### Incremental Delivery

1. Setup + Foundational
2. Deliver US1 functional behavior package
3. Deliver US2 parity acceptance package
4. Deliver US3 interface compatibility and switch package
5. Run final readiness review

### Parallel Team Strategy

1. Contributor A handles foundational artifacts
2. Contributor B handles US1 service and unit tests
3. Contributor C handles US2 parity integrations
4. Contributor D handles US3 contract tests and interface compatibility

---

## Notes

- All tasks follow required checklist format and include explicit file paths
- Story labels are present only in user-story phases
- Tests are included because feature requirements explicitly require acceptance parity validation
- If ambiguous behavior appears during execution, stop and record blocker before continuing
