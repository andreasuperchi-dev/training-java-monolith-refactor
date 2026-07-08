# Phase 0 Research: Substitution Audit over Rediscovery Artifacts (Phase 1)

## Decision Log

### 1) Audit-only scope enforcement
- Decision: Restrict outputs to substitution classification and evidence-backed rationale only.
- Rationale: The feature explicitly prohibits architecture design and migration planning.
- Alternatives considered: Including phased migration approaches was rejected as out-of-scope.

### 2) Canonical substitution proposal taxonomy
- Decision: Use only four proposal values: `keep-as-is`, `replace-with-library`, `replace-with-platform`, `retire`.
- Rationale: The specification requires a closed set of proposal classes for consistent review.
- Alternatives considered: Additional values such as `defer` or `investigate` were rejected to preserve comparability.

### 3) Traceability granularity standard
- Decision: Every audit row must reference a concrete rediscovery entry ID, heading, or table row in `specs/001-rediscover-legacy-monolith/` artifacts.
- Rationale: Success criteria require row-level traceability to prior rediscovery evidence.
- Alternatives considered: File-level references alone were rejected as insufficiently precise.

### 4) Coverage handling for empty categories
- Decision: If no findings exist for a required coverage category, record an explicit `not-found` statement with supporting rediscovery evidence.
- Rationale: Completeness requires all five requested categories to be represented, including confirmed absence.
- Alternatives considered: Omitting empty categories was rejected because it creates ambiguity.

### 5) Evidence confidence and assumption labeling
- Decision: Keep conclusions evidence-backed by default; mark unresolved interpretations as assumptions.
- Rationale: Avoids over-claiming while still enabling audit completion when evidence is partial.
- Alternatives considered: Deferring all partial cases would reduce actionable coverage.

## Best-Practice Findings for Audit Design

### Substitution-audit formatting practice
- Decision: Standardize each row to: legacy element, proposal, reason, trade-off, and rediscovery reference.
- Rationale: Structured rows improve reviewer consistency and downstream decision readiness.
- Alternatives considered: Narrative-only findings were rejected because they are harder to diff and verify.

### Category-first coverage validation
- Decision: Validate final output against five mandatory categories before completion.
- Rationale: Prevents accidental undercoverage in large audits.
- Alternatives considered: Post-hoc informal checks were rejected due to higher omission risk.

### Trade-off statement practice
- Decision: Require one explicit trade-off per proposal row (e.g., cost, lock-in, skills, latency, operability).
- Rationale: Substitution quality depends on downside visibility, not recommendation strength alone.
- Alternatives considered: Optional trade-offs were rejected as too weak for governance review.

## Integration Pattern Findings

### Dependency on prior rediscovery artifacts
- Pattern: This feature consumes and reinterprets outputs from `001-rediscover-legacy-monolith`.
- Decision: Treat those artifacts as authoritative evidence input and avoid re-discovering raw source behavior in this phase.
- Rationale: Maintains phase boundaries and reduces duplicate analysis effort.

### Cross-artifact mapping model
- Pattern: One legacy element may map to multiple rediscovery entries (rules, data-model invariants, integration inventory, open questions).
- Decision: Allow multi-reference rows while requiring a single dominant rationale statement.
- Rationale: Preserves fidelity without fragmenting the audit into contradictory micro-rows.

## Open Research Resolutions

- No unresolved technical-context items remain for planning.
- Remaining uncertainty is domain interpretation of specific substitutions, which belongs to implementation and reviewer validation, not plan setup.