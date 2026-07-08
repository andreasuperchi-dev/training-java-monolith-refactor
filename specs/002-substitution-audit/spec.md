# Feature Specification: Substitution Audit over Rediscovery Artifacts (Phase 1)

**Feature Branch**: `[002-substitution-audit]`

**Created**: 2026-07-08

**Status**: Draft

**Input**: User description: "Specify a substitution audit over the rediscovery artifacts (phase 1). Deliverables: (1) a substitution-audit table with columns *legacy element*, *proposal* (keep-as-is | replace-with-library | replace-with-platform | retire), *reason*, *trade-off*; (2) coverage spanning home-grown code with modern equivalents, dated integrations, unfit data stores, EOL runtimes/frameworks, and cloud-hostile operational assumptions. Constraint: audit only — do not design the new architecture. Success: every row links back to a specific rediscovery entry."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Produce a Traceable Substitution Table (Priority: P1)

A reviewer receives a substitution-audit table that classifies legacy elements and links each row to concrete entries in existing rediscovery artifacts.

**Why this priority**: The table is the primary decision-support artifact and is required before any planning discussion can proceed.

**Independent Test**: A reviewer samples rows in the substitution-audit table and verifies each one has all required columns populated and points to a specific rediscovery entry.

**Acceptance Scenarios**:

1. **Given** existing rediscovery artifacts are available, **When** the substitution audit is completed, **Then** the output includes a table with columns: legacy element, proposal, reason, and trade-off.
2. **Given** any table row, **When** a reviewer follows its traceability link, **Then** the linked rediscovery entry exists and supports the row claim.

---

### User Story 2 - Ensure Full Coverage of Risk Categories (Priority: P1)

A reviewer can confirm the audit explicitly addresses all requested replacement and retirement risk categories within phase-1 scope.

**Why this priority**: Incomplete category coverage weakens audit usefulness and can hide modernization risk in later phases.

**Independent Test**: A reviewer checks the final audit against the required category checklist and confirms each category has explicit row coverage or explicit not-found notation backed by rediscovery references.

**Acceptance Scenarios**:

1. **Given** the substitution audit table, **When** category coverage is reviewed, **Then** it includes home-grown code equivalents, dated integrations, unfit data stores, EOL runtimes/frameworks, and cloud-hostile operational assumptions.
2. **Given** a category has no candidates in scope, **When** the audit is reviewed, **Then** the audit states the absence explicitly and links to the rediscovery evidence used to confirm it.

---

### User Story 3 - Preserve Audit-Only Scope Boundaries (Priority: P2)

A stakeholder can use the audit to discuss substitutions without it drifting into architecture design.

**Why this priority**: The stated constraint prohibits architecture design in this phase, so scope discipline is required for acceptance.

**Independent Test**: A reviewer scans the output and confirms it contains no target-state architecture, migration sequencing, or implementation design details.

**Acceptance Scenarios**:

1. **Given** completed audit content, **When** scope is checked, **Then** proposals are limited to keep-as-is, replace-with-library, replace-with-platform, or retire classifications with rationale and trade-offs.
2. **Given** recommendations in the audit, **When** they are reviewed, **Then** none include architecture blueprints, implementation-level migration plans, or component design.

---

### Edge Cases

- What happens when one legacy element maps to multiple rediscovery entries with conflicting signals?
- How is a legacy element handled when evidence indicates partial fit across two proposal classes (for example, keep-as-is in one context but retire in another)?
- How does the audit record elements that appear in documentation but have no confirming executable evidence?
- How does the audit handle requested categories that have zero in-scope findings?
- What happens when a rediscovery entry is too coarse to support a single, unambiguous substitution row?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The feature MUST produce a substitution-audit deliverable for phase-1 rediscovery artifacts.
- **FR-002**: The deliverable MUST include a table with exactly these columns: legacy element, proposal, reason, trade-off.
- **FR-003**: The proposal column MUST restrict values to: keep-as-is, replace-with-library, replace-with-platform, retire.
- **FR-004**: Every table row MUST include at least one explicit link or citation to a specific rediscovery entry that supports the row.
- **FR-005**: The audit MUST include coverage for home-grown code with modern equivalents.
- **FR-006**: The audit MUST include coverage for dated integrations.
- **FR-007**: The audit MUST include coverage for unfit data stores.
- **FR-008**: The audit MUST include coverage for EOL runtimes/frameworks.
- **FR-009**: The audit MUST include coverage for cloud-hostile operational assumptions.
- **FR-010**: For any requested category with no findings, the audit MUST include an explicit not-found statement linked to rediscovery evidence.
- **FR-011**: The audit MUST remain analysis-only and MUST NOT include target architecture design.
- **FR-012**: The audit MUST NOT include migration implementation sequencing, code-level redesign, or platform topology proposals.
- **FR-013**: Each substitution row MUST include a concise reason and at least one trade-off that is understandable to technical and domain reviewers.
- **FR-014**: The audit MUST separate evidence-backed conclusions from assumptions, and assumptions MUST be explicitly labeled.

### Key Entities *(include if feature involves data)*

- **Substitution Audit Row**: A structured record containing legacy element, proposal class, reason, trade-off, and rediscovery reference link.
- **Coverage Category**: A required audit domain bucket (home-grown equivalents, dated integrations, unfit data stores, EOL runtime/framework, cloud-hostile assumptions).
- **Rediscovery Reference**: A pointer to a specific entry in phase-1 rediscovery artifacts used as evidence for a substitution row.
- **Scope Boundary Rule**: A control statement that enforces audit-only behavior and excludes architecture design outputs.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of substitution rows include all required columns populated.
- **SC-002**: 100% of substitution rows include at least one valid link/citation to a specific rediscovery entry.
- **SC-003**: 100% of required coverage categories are represented by one or more rows, or an explicit evidence-linked not-found statement.
- **SC-004**: 0 audit sections contain target-state architecture designs, migration sequencing plans, or implementation-level solution design.
- **SC-005**: At least 95% of sampled rows are judged traceable and sufficiently justified by reviewers using only linked rediscovery entries.

## Assumptions

- Phase-1 rediscovery artifacts are available, stable, and considered the authoritative evidence source for this audit.
- The audit focuses on substitution suitability, not execution planning or solution architecture.
- A "specific rediscovery entry" may be a section, table row, or explicitly identified item within the existing rediscovery documents.
- Proposal classes are mutually exclusive per row unless explicitly split into separate rows for clarity.
- If evidence is incomplete, the row remains in scope only when assumptions are clearly labeled and bounded.