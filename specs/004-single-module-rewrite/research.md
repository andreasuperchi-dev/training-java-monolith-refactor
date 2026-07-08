# Phase 0 Research: Reporting Module Rewrite (Phase 3b)

## Decision Log

### 1) Selected module scope for this iteration
- Decision: Limit this phase-3b iteration to the Reporting Query Domain only.
- Rationale: User selected reporting as the first rewrite slice, and the feature mandates single-module scope per iteration.
- Alternatives considered: Time Entry, Billing Calculation, Customer, Billing Catalog, Identity/Auth, Integration Adapter.
- Traceability: `specs/004-single-module-rewrite/spec.md#FR-001`; `specs/003-define-target-architecture/architecture.md` section 5 (Reporting Query Domain).

### 2) Legacy reporting behavior source of truth
- Decision: Treat report behavior currently observable through `reports.jsp` as canonical for parity contract unless explicitly superseded by validated business-rule evidence.
- Rationale: Existing production-facing report outputs are generated directly in JSP SQL paths and are the externally observed behavior consumers use today.
- Alternatives considered: Treat `BillingService` reporting methods as canonical source for all report behavior.
- Traceability: `specs/001-rediscover-legacy-monolith/business-rules.md#BR-013`; `src/main/webapp/reports.jsp`; `specs/001-rediscover-legacy-monolith/open-questions.md#OQ-007`.

### 3) Acceptance-test baseline strategy
- Decision: Define behavior-preserving acceptance tests directly from mapped business rules and mirror report outputs for customer, monthly, and revenue report flows.
- Rationale: Feature success requires legacy and rewritten implementations to produce identical results; rule-linked tests provide objective parity evidence.
- Alternatives considered: Rewrite-first then derive tests from new behavior.
- Traceability: `specs/004-single-module-rewrite/spec.md#FR-004`; `#FR-005`; `specs/001-rediscover-legacy-monolith/business-rules.md#BR-002`; `#BR-003`; `#BR-004`; `#BR-013`.

### 4) Interface compatibility boundary for gradual switching
- Decision: Contract preserves report request parameters, response semantics, calculation rules, ordering behavior, and error-surface expectations as externally observed.
- Rationale: Strangler migration requires clients to switch traffic between legacy and rewritten implementations without externally visible behavior changes.
- Alternatives considered: Introduce normalized/new report contract in this loop.
- Traceability: `specs/004-single-module-rewrite/spec.md#FR-007`; `#FR-008`; `#FR-009`; `specs/001-rediscover-legacy-monolith/contracts/ui-and-reporting-contracts.md` section 3.

### 5) Ambiguity handling policy
- Decision: Any reporting behavior lacking explicit `BR-*` evidence or conflicting across sources is recorded as a blocker for clarification, not inferred.
- Rationale: Feature constraint explicitly forbids invented behavior.
- Alternatives considered: Fill missing behavior using implementation assumptions.
- Traceability: `specs/004-single-module-rewrite/spec.md#FR-006`; `specs/001-rediscover-legacy-monolith/open-questions.md`.

## Best-Practice Findings Applied

- Use requirement-to-rule trace matrix to enforce 1..n mapping between each functional requirement and preserved business rules.
- Define parity tests as black-box behavior checks over stable report scenarios (inputs, sorting, totals, null/empty handling, and failure cases).
- Separate module scope from implementation decisions; this phase defines what must be preserved, not how code is written.
- Keep contract drift checks explicit by comparing legacy request/response semantics against rewritten outputs before traffic shifting.

## Clarification Resolution Status

- Resolved: module selection for this iteration (`Reporting Query Domain`).
- Remaining known ambiguity references retained (not invented):
  - canonical ownership split between JSP SQL vs service methods (`OQ-007`), handled by taking externally observed reporting outputs as parity baseline.
  - any newly discovered unmapped behavior during implementation must trigger clarification per `FR-006`.
