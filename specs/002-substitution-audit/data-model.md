# Data Model: Substitution Audit over Rediscovery Artifacts (Phase 1)

This data model defines entities required to produce a traceable, category-complete substitution audit.

## Entity: SubstitutionAuditRow

### Purpose
Represents one audit judgment for a legacy element candidate.

### Fields
- `rowId`: unique row identifier within the audit artifact.
- `legacyElement`: concise name of the legacy component, practice, runtime, store, or integration.
- `proposal`: one of `keep-as-is`, `replace-with-library`, `replace-with-platform`, `retire`.
- `reason`: concise evidence-based rationale for the proposal.
- `tradeOff`: at least one downside, risk, or cost associated with the proposal.
- `referenceIds`: one or more pointers to specific rediscovery entries from feature `001-rediscover-legacy-monolith`.
- `assumptionFlag`: boolean indicating whether the row relies on explicit assumptions.
- `assumptionNote`: required when `assumptionFlag=true`; describes uncertainty boundary.

### Validation Rules
- `proposal` MUST match one of four allowed values.
- `legacyElement`, `reason`, and `tradeOff` MUST be non-empty.
- `referenceIds` MUST contain at least one resolvable rediscovery reference.
- `assumptionNote` MUST be non-empty when `assumptionFlag=true`.

## Entity: CoverageCategory

### Purpose
Tracks required coverage buckets mandated by the feature specification.

### Fields
- `categoryId`: stable identifier.
- `name`: one of:
  - home-grown code with modern equivalents
  - dated integrations
  - unfit data stores
  - EOL runtimes/frameworks
  - cloud-hostile operational assumptions
- `status`: `covered` or `not-found`.
- `evidenceRefs`: references proving either coverage or evidence-backed absence.
- `notes`: optional clarification.

### Validation Rules
- All five required categories MUST exist exactly once.
- `status=covered` requires at least one linked `SubstitutionAuditRow`.
- `status=not-found` requires explicit `evidenceRefs` and explanatory note.

## Entity: RediscoveryReference

### Purpose
Connects substitution findings to concrete prior rediscovery evidence.

### Fields
- `refId`: stable reference token.
- `artifactPath`: path under `specs/001-rediscover-legacy-monolith/`.
- `entryLocator`: section heading, table key, or row identifier.
- `entrySummary`: short summary of referenced rediscovery evidence.

### Validation Rules
- `artifactPath` MUST point to an existing phase-1 rediscovery artifact.
- `entryLocator` MUST identify a concrete location within that artifact.

## Entity: ScopeBoundaryRule

### Purpose
Encodes constraints that prevent this feature from drifting into architecture design.

### Fields
- `ruleId`: stable identifier.
- `statement`: auditable rule text.
- `violationExamples`: disallowed output patterns (architecture diagrams, migration sequencing, component topology).

### Validation Rules
- At least one scope boundary rule MUST be present.
- All generated outputs MUST comply with all boundary rules.

## Relationships

- `CoverageCategory` 1..* `SubstitutionAuditRow` (for `covered` categories).
- `SubstitutionAuditRow` *..* `RediscoveryReference`.
- `ScopeBoundaryRule` 1..* output artifacts (constraint relationship).

## State Model

- `SubstitutionAuditRow`: `draft` -> `evidence-linked` -> `review-ready`.
- `CoverageCategory`: `unchecked` -> `covered` or `not-found`.

## Derived Integrity Checks

- Coverage completeness check: all required categories have terminal status (`covered` or `not-found`).
- Traceability check: every row has at least one valid rediscovery reference.
- Scope check: zero rows contain architecture-design content.