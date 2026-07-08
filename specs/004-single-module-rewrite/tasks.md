# Tasks: Reporting Module Rewrite (Phase 3b)

**Input**: Design documents from `/specs/004-single-module-rewrite/`
**Prerequisites**: `plan.md` (required), `spec.md` (required for user stories), `research.md`, `data-model.md`, `contracts/`, `quickstart.md`

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Initialize feature-004 execution workspace for reporting-module rewrite artifacts

- [x] T001 Create task execution baseline and section scaffolding in `specs/004-single-module-rewrite/tasks.md`
- [x] T002 [P] Create reporting parity test workspace scaffold in `tests/integration/reporting/README.md`
- [x] T003 [P] Create reporting contract validation workspace scaffold in `tests/contract/reporting/README.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Define shared parity harness and traceability assets required before user-story execution

- [x] T004 Create requirement-to-business-rule trace matrix for reporting scope in `specs/004-single-module-rewrite/traceability-matrix.md`
- [x] T005 [P] Define canonical legacy reporting fixtures for customer/monthly/revenue scenarios in `specs/004-single-module-rewrite/fixtures/legacy-report-scenarios.md`
- [x] T006 [P] Define ambiguity blocker log template for unresolved reporting behavior in `specs/004-single-module-rewrite/ambiguity-blockers.md`
- [x] T007 Define parity assertion criteria and comparison tolerances in `specs/004-single-module-rewrite/parity-criteria.md`
- [x] T008 [P] Define traffic switch gate checklist for reporting route cutover in `specs/004-single-module-rewrite/traffic-switch-gates.md`

**Checkpoint**: Foundation ready; user stories can proceed with shared parity and traceability controls

---

## Phase 3: User Story 1 - Define Module-Specific Functional Requirements (Priority: P1) 🎯 MVP

**Goal**: Produce reporting-module functional requirements with explicit `BR-*` mappings and no cross-module scope bleed

**Independent Test**: Reviewer can verify every reporting requirement maps to one or more phase-1 business rules and all requirements stay within Reporting Query Domain

### Implementation for User Story 1

- [x] T009 [US1] Draft reporting module requirement catalog aligned to customer/monthly/revenue flows in `specs/004-single-module-rewrite/requirements/reporting-functional-requirements.md`
- [x] T010 [P] [US1] Map each reporting requirement to preserved business rules in `specs/004-single-module-rewrite/traceability-matrix.md`
- [x] T011 [US1] Add explicit non-reporting out-of-scope boundary statements in `specs/004-single-module-rewrite/requirements/reporting-functional-requirements.md`
- [x] T012 [US1] Add ambiguity-stop checkpoints for requirement finalization in `specs/004-single-module-rewrite/requirements/reporting-functional-requirements.md`
- [x] T013 [US1] Validate requirement completeness against `FR-002`, `FR-003`, `FR-006`, and `FR-010` in `specs/004-single-module-rewrite/requirements/requirements-validation.md`

**Checkpoint**: Reporting requirements are complete, trace-linked, and scope-contained

---

## Phase 4: User Story 2 - Specify Behavior-Preserving Acceptance Tests (Priority: P1)

**Goal**: Define parity-focused acceptance tests derived from reporting business rules

**Independent Test**: Reviewer can verify acceptance tests are rule-derived and encode legacy-vs-rewrite expected parity outcomes

### Implementation for User Story 2

- [x] T014 [US2] Define customer bill parity acceptance tests from `BR-001`, `BR-002`, and `BR-013` in `specs/004-single-module-rewrite/tests/acceptance-customer-report.md`
- [x] T015 [P] [US2] Define monthly summary parity acceptance tests from `BR-003` and `BR-013` in `specs/004-single-module-rewrite/tests/acceptance-monthly-report.md`
- [x] T016 [P] [US2] Define revenue summary parity acceptance tests from `BR-002`, `BR-004`, and `BR-013` in `specs/004-single-module-rewrite/tests/acceptance-revenue-report.md`
- [x] T017 [US2] Define parity assertions for ordering, totals, and error-path equivalence in `specs/004-single-module-rewrite/tests/parity-assertions.md`
- [x] T018 [US2] Define ambiguity blocker test handling workflow in `specs/004-single-module-rewrite/tests/ambiguity-test-gates.md`
- [x] T019 [US2] Validate acceptance test suite coverage against `FR-004`, `FR-005`, `SC-002`, and `SC-003` in `specs/004-single-module-rewrite/tests/tests-validation.md`

**Checkpoint**: Reporting acceptance tests are fully specified for parity validation

---

## Phase 5: User Story 3 - Preserve Legacy Interface for Gradual Traffic Switching (Priority: P2)

**Goal**: Finalize a legacy-equivalent reporting interface contract and switch gates for incremental migration

**Independent Test**: Reviewer can compare contract semantics against legacy reporting interface and confirm gradual-switch readiness criteria

### Implementation for User Story 3

- [x] T020 [US3] Refine customer bill contract operation semantics and parameter constraints in `specs/004-single-module-rewrite/contracts/reporting-query-contract.md`
- [x] T021 [P] [US3] Refine monthly summary contract operation semantics and date-window behavior in `specs/004-single-module-rewrite/contracts/reporting-query-contract.md`
- [x] T022 [P] [US3] Refine revenue summary contract operation semantics and aggregate ordering rules in `specs/004-single-module-rewrite/contracts/reporting-query-contract.md`
- [x] T023 [US3] Define contract-equivalence checklist for legacy and rewritten reporting interfaces in `specs/004-single-module-rewrite/contracts/contract-equivalence-checklist.md`
- [x] T024 [US3] Define gradual traffic-switch gate execution sequence in `specs/004-single-module-rewrite/traffic-switch-gates.md`
- [x] T025 [US3] Validate contract and switch-readiness against `FR-007`, `FR-008`, `FR-009`, and `SC-004` in `specs/004-single-module-rewrite/contracts/contract-validation.md`

**Checkpoint**: Reporting contract is migration-ready and legacy-equivalent

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Finalize consistency, traceability, and handoff readiness across all reporting-module artifacts

- [x] T026 [P] Run terminology and naming consistency pass across reporting artifacts in `specs/004-single-module-rewrite/quickstart.md`
- [x] T027 [P] Finalize quickstart execution instructions for independent story validation in `specs/004-single-module-rewrite/quickstart.md`
- [x] T028 Validate end-to-end traceability from requirements to tests to contract in `specs/004-single-module-rewrite/traceability-matrix.md`
- [x] T029 Validate no unresolved invented behavior and log all blockers in `specs/004-single-module-rewrite/ambiguity-blockers.md`
- [x] T030 Execute final readiness review against all success criteria in `specs/004-single-module-rewrite/final-readiness-review.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: No dependencies; starts immediately
- **Phase 2 (Foundational)**: Depends on Phase 1; blocks all user-story work
- **Phase 3 (US1)**: Depends on Phase 2 completion
- **Phase 4 (US2)**: Depends on Phase 2 and uses US1 requirement mappings
- **Phase 5 (US3)**: Depends on Phase 2 and consumes US1/US2 outputs
- **Phase 6 (Polish)**: Depends on completion of US1, US2, and US3

### User Story Dependencies

- **US1 (P1)**: Starts after Foundational; no dependency on other stories
- **US2 (P1)**: Starts after Foundational; depends on US1 requirement mappings for full trace coverage
- **US3 (P2)**: Starts after Foundational; depends on US1 requirements and US2 parity assertions for switch gates

### Within Each User Story

- Requirement definitions before requirement validation
- Acceptance test definitions before test coverage validation
- Contract refinements before contract/switch-readiness validation

### Parallel Opportunities

- Setup: `T002`, `T003` can run in parallel
- Foundational: `T005`, `T006`, `T008` can run in parallel
- US1: `T010` can run in parallel with drafting after initial requirement skeleton exists
- US2: `T015` and `T016` can run in parallel after `T014`
- US3: `T021` and `T022` can run in parallel after `T020`
- Polish: `T026` and `T027` can run in parallel

---

## Parallel Example: User Story 2

```bash
# In parallel after T014 baseline customer-report tests exist:
Task: "T015 Define monthly summary parity acceptance tests in specs/004-single-module-rewrite/tests/acceptance-monthly-report.md"
Task: "T016 Define revenue summary parity acceptance tests in specs/004-single-module-rewrite/tests/acceptance-revenue-report.md"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1 and Phase 2
2. Complete Phase 3 (US1)
3. Validate requirement-to-rule traceability independently
4. Freeze MVP documentation baseline

### Incremental Delivery

1. Complete Setup + Foundational
2. Deliver US1 requirements package
3. Deliver US2 acceptance parity package
4. Deliver US3 contract + traffic-switch package
5. Run Phase 6 readiness review

### Parallel Team Strategy

1. One contributor handles foundational artifacts (`T004`-`T008`)
2. One contributor handles US1 requirement package (`T009`-`T013`)
3. One contributor handles US2 test package (`T014`-`T019`) after US1 trace matrix baseline
4. One contributor handles US3 contract package (`T020`-`T025`) with shared parity criteria

---

## Notes

- All tasks follow required checklist format: `- [ ] T### [P?] [US?] Description with file path`
- Story labels are applied only to user-story phases
- Tasks are specific and immediately executable for LLM-driven implementation
- If a business-rule gap appears during execution, stop and raise blocker in `specs/004-single-module-rewrite/ambiguity-blockers.md`
