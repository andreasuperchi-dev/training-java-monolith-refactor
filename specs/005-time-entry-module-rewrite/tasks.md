# Tasks: Time Entry Module Rewrite (Phase 3b)

**Input**: Design documents from `/specs/005-time-entry-module-rewrite/`

**Prerequisites**: `plan.md` (required), `spec.md` (required), `research.md`, `data-model.md`, `contracts/time-entry-contract.md`, `quickstart.md`

**Tests**: Included because `spec.md` requires acceptance parity validation and equivalent outcomes between legacy and rewritten paths.

**Organization**: Tasks are grouped by user story so each story can be implemented and validated independently.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Prepare rewrite scaffolding and evidence documents for Time Entry execution.

- [X] T001 Create implementation worklog scaffold in `specs/005-time-entry-module-rewrite/implementation-notes.md`
- [X] T002 [P] Create integration test package marker in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/package-info.java`
- [X] T003 [P] Create contract test package marker in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/package-info.java`
- [X] T004 [P] Create fixtures folder placeholder for Time Entry parity scenarios in `specs/005-time-entry-module-rewrite/fixtures/legacy-time-entry-scenarios.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Build traceability and governance artifacts that block all story work until complete.

**CRITICAL**: No user-story implementation begins before this phase is complete.

- [X] T005 Build requirement-to-rule matrix for FR-001..FR-012 to BR/SA anchors in `specs/005-time-entry-module-rewrite/traceability-matrix.md`
- [X] T006 [P] Create parity criteria baseline for validation, warning, and persistence outcomes in `specs/005-time-entry-module-rewrite/parity-criteria.md`
- [X] T007 [P] Create ambiguity blocker register with stop-and-ask protocol in `specs/005-time-entry-module-rewrite/ambiguity-blockers.md`
- [X] T008 [P] Define traffic switch gate checklist and rollback criteria in `specs/005-time-entry-module-rewrite/traffic-switch-gates.md`
- [X] T009 Record source-of-truth evidence map for hours.jsp and BillingService behavior anchors in `specs/005-time-entry-module-rewrite/requirements-validation.md`

**Checkpoint**: Foundational controls are complete and user stories can proceed.

---

## Phase 3: User Story 1 - Define Time Entry Functional Requirements (Priority: P1) MVP

**Goal**: Deliver module-scoped Time Entry functionality with explicit rule mapping and no policy drift.

**Independent Test**: Reviewer confirms all implemented Time Entry behaviors map to BR anchors and no non-Time Entry behavior is included.

### Tests for User Story 1

- [X] T010 [P] [US1] Add required-input validation unit tests for customer/user/category/hours in `src/test/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryServiceValidationTest.java`
- [X] T011 [P] [US1] Add date defaulting and future-date unit tests in `src/test/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryServiceDateRulesTest.java`
- [X] T012 [P] [US1] Add weekend warning unit tests preserving warning-not-reject behavior in `src/test/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryServiceWeekendRulesTest.java`

### Implementation for User Story 1

- [X] T013 [P] [US1] Implement request DTO for Time Entry inputs in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryRequest.java`
- [X] T014 [P] [US1] Implement validation result model for errors and warnings in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryValidationResult.java`
- [X] T015 [US1] Implement core validation semantics (required fields, positive hours, future-date checks) in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryService.java`
- [X] T016 [US1] Implement default-date and weekend warning parity behavior in `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/TimeEntryService.java`
- [X] T017 [US1] Add US1 trace links from implemented behaviors to BR-008..BR-011 in `specs/005-time-entry-module-rewrite/traceability-matrix.md`
- [X] T018 [US1] Validate US1 against FR-002, FR-003, FR-005, FR-006, FR-007, and FR-008 in `specs/005-time-entry-module-rewrite/requirements-validation.md`

**Checkpoint**: Time Entry service behavior is functionally complete and trace-mapped.

---

## Phase 4: User Story 2 - Specify Behavior-Preserving Time Entry Acceptance Tests (Priority: P1)

**Goal**: Provide parity acceptance tests that prove equivalent legacy-vs-rewrite outcomes.

**Independent Test**: Parity suite demonstrates equivalent results for required-input, date, invalid values, weekend warning, and persistence semantics.

### Tests for User Story 2

- [X] T019 [P] [US2] Add required-field parity integration tests in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryRequiredFieldsParityIT.java`
- [X] T020 [P] [US2] Add default-date parity integration tests in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryDefaultDateParityIT.java`
- [X] T021 [P] [US2] Add invalid-values parity integration tests for non-positive hours and future dates in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryInvalidValuesParityIT.java`
- [X] T022 [P] [US2] Add weekend-warning parity integration tests in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryWeekendWarningParityIT.java`
- [X] T023 [P] [US2] Add persistence parity integration tests for valid reference inputs in `src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryPersistenceParityIT.java`

### Implementation for User Story 2

- [X] T024 [US2] Define acceptance test matrix with explicit parity outcomes for TE-001..TE-005 in `specs/005-time-entry-module-rewrite/tests/acceptance-time-entry.md`
- [X] T025 [US2] Define parity assertion checklist for legacy-vs-rewrite comparisons in `specs/005-time-entry-module-rewrite/tests/parity-assertions.md`
- [X] T026 [US2] Define ambiguity test gates for unresolved validation-policy conflicts in `specs/005-time-entry-module-rewrite/tests/ambiguity-test-gates.md`
- [X] T027 [US2] Validate US2 against FR-004, SC-002, and SC-003 in `specs/005-time-entry-module-rewrite/tests/tests-validation.md`

**Checkpoint**: Acceptance parity package is complete and independently executable.

---

## Phase 5: User Story 3 - Preserve Legacy Time Entry Interface for Gradual Switching (Priority: P2)

**Goal**: Preserve legacy-equivalent external interface while routing through rewritten Time Entry behavior.

**Independent Test**: Contract and integration checks confirm no externally observable drift across switch scenarios.

### Tests for User Story 3

- [X] T028 [P] [US3] Add contract tests for required and optional Time Entry input parameters in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/TimeEntryInputContractTest.java`
- [X] T029 [P] [US3] Add contract tests for success and error message semantics in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/TimeEntryOutcomeContractTest.java`
- [X] T030 [P] [US3] Add contract tests for date-default and weekend-warning output behavior in `src/test/java/com/sourcegraph/demo/bigbadmonolith/contract/timeentry/TimeEntryDateAndWarningContractTest.java`

### Implementation for User Story 3

- [X] T031 [US3] Route `action=log` flow through TimeEntryService while preserving request field names in `src/main/webapp/hours.jsp`
- [X] T032 [US3] Preserve legacy-equivalent validation and persistence message prefixes in `src/main/webapp/hours.jsp`
- [X] T033 [US3] Update contract execution notes with observed parity outcomes in `specs/005-time-entry-module-rewrite/contracts/time-entry-contract.md`
- [X] T034 [US3] Create contract equivalence checklist for switch readiness in `specs/005-time-entry-module-rewrite/contracts/contract-equivalence-checklist.md`
- [X] T035 [US3] Validate US3 against FR-009, FR-011, and SC-004 in `specs/005-time-entry-module-rewrite/contracts/contract-validation.md`

**Checkpoint**: Time Entry interface is switch-ready and contract-verified.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Complete final consistency, traceability, and readiness review across all stories.

- [X] T036 [P] Run terminology consistency pass across spec artifacts in `specs/005-time-entry-module-rewrite/quickstart.md`
- [X] T037 [P] Finalize independent story validation run order in `specs/005-time-entry-module-rewrite/quickstart.md`
- [X] T038 Validate full FR/SC traceability to tests and contracts in `specs/005-time-entry-module-rewrite/traceability-matrix.md`
- [X] T039 Validate ambiguity blockers are unresolved-or-cleared with evidence in `specs/005-time-entry-module-rewrite/ambiguity-blockers.md`
- [X] T040 Execute final readiness review against SC-001..SC-006 in `specs/005-time-entry-module-rewrite/final-readiness-review.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- Setup (Phase 1): no dependencies
- Foundational (Phase 2): depends on Setup and blocks all user stories
- User Story phases (Phase 3+): depend on Foundational completion
- Polish (Phase 6): depends on US1, US2, and US3 completion

### User Story Dependencies

- US1 (P1): starts immediately after Foundational
- US2 (P1): starts after Foundational, then depends on US1 behavior baseline for parity comparison
- US3 (P2): starts after Foundational, then depends on US1 and US2 outputs for switch-gate proof

### Parallel Opportunities

- Phase 1 parallel tasks: T002, T003, T004
- Phase 2 parallel tasks: T006, T007, T008
- US1 parallel tests/models: T010, T011, T012, T013, T014
- US2 parallel parity tests: T019, T020, T021, T022, T023
- US3 parallel contract tests: T028, T029, T030
- Polish parallel tasks: T036, T037

---

## Parallel Example: User Story 2

```bash
Task: "T019 [US2] Add required-field parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryRequiredFieldsParityIT.java"
Task: "T020 [US2] Add default-date parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryDefaultDateParityIT.java"
Task: "T021 [US2] Add invalid-values parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryInvalidValuesParityIT.java"
Task: "T022 [US2] Add weekend-warning parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryWeekendWarningParityIT.java"
Task: "T023 [US2] Add persistence parity integration tests in src/test/java/com/sourcegraph/demo/bigbadmonolith/integration/timeentry/TimeEntryPersistenceParityIT.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1 and Phase 2.
2. Complete US1 tests and implementation (T010-T018).
3. Validate US1 independently before US2/US3.

### Incremental Delivery

1. Setup + Foundational.
2. Deliver US1 functional baseline.
3. Deliver US2 parity suite.
4. Deliver US3 interface and switch-gate package.
5. Run final readiness review.

### Parallel Team Strategy

1. Engineer A: Foundational artifacts (T005-T009).
2. Engineer B: US1 unit tests and service implementation (T010-T018).
3. Engineer C: US2 integration parity suite (T019-T027).
4. Engineer D: US3 contract tests and interface compatibility (T028-T035).

---

## Notes

- Every task uses the required checklist format: `- [ ] T### [P?] [US?] Description with file path`.
- Story labels are included only for user-story phases.
- Ambiguous behavior must be blocked and tracked before implementation proceeds.
