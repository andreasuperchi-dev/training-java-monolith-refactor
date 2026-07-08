# Phase 0 Research: Time Entry Module Rewrite (Phase 3b)

## Decision Log

### 1) Selected module scope for this iteration
- Decision: Scope this phase-3b iteration to Time Entry Domain only.
- Rationale: Time Entry is the next migration slice after reporting and is a policy hotspot (`SA-008`).
- Alternatives considered: Customer, Billing Catalog, Billing Calculation, Identity, Integration Adapter.
- Traceability: `spec.md#FR-001`, architecture boundary catalog (Time Entry Domain).

### 2) Legacy Time Entry behavior source of truth
- Decision: Use externally observed `hours.jsp` behavior as interface baseline and `BR-008`..`BR-011` as rule anchors.
- Rationale: Existing user-facing semantics must be preserved for gradual switching.
- Alternatives considered: Service-only validation semantics as sole baseline.
- Traceability: `business-rules.md#BR-008`, `#BR-009`, `#BR-010`, `#BR-011`, `src/main/webapp/hours.jsp`.

### 3) Validation-policy parity strategy
- Decision: Preserve current effective validation semantics while explicitly recording path divergence as controlled risk.
- Rationale: Split validation paths are known (`CF-002` / `SA-008`) and must be normalized without behavior invention.
- Alternatives considered: Immediate strict unification to service-level validation behavior.
- Traceability: `business-rules.md#CF-002`, `substitution-audit.md#SA-008`, `open-questions.md#OQ-002`.

### 4) Interface compatibility for gradual switching
- Decision: Preserve Time Entry input contract (`customerId`, `userId`, `categoryId`, `hours`, optional `note`, optional `date`) and equivalent success/error outcomes.
- Rationale: Strangler switch requires stable request/response behavior across legacy and rewritten paths.
- Alternatives considered: Introducing new request schema during first rewrite slice.
- Traceability: `ui-and-reporting-contracts.md` section `hours.jsp`; `spec.md#FR-009`, `#FR-011`.

### 5) Ambiguity handling policy
- Decision: Any unresolved Time Entry behavior remains blocked until clarified; no inferred behavior is allowed.
- Rationale: Feature constraint requires stop-and-ask discipline.
- Alternatives considered: Filling gaps from developer interpretation.
- Traceability: `spec.md#FR-010`; `open-questions.md#OQ-002`, `#OQ-003`.

## Best-Practice Findings Applied

- Enforce requirement-to-rule mapping for all Time Entry requirements.
- Define parity tests as black-box behavior checks (inputs, validation outcomes, persisted effects, and user-visible messages).
- Keep scope narrow to Time Entry to prevent cross-module creep.
- Explicitly gate migration switching on parity evidence.

## Clarification Resolution Status

No unresolved `NEEDS CLARIFICATION` markers remain in this spec.
Known policy ambiguities remain tracked as blockers and are not treated as confirmed behavior.
