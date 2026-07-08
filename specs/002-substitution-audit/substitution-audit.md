# Substitution Audit: Rediscovery Artifacts (Phase 1)

## Audit Metadata

- Feature: `002-substitution-audit`
- Source baseline: `specs/001-rediscover-legacy-monolith/`
- Audit mode: analysis-only (no target architecture design)
- Proposal taxonomy: `keep-as-is`, `replace-with-library`, `replace-with-platform`, `retire`
- Citation mode: each row must reference specific rediscovery entries

## Scope Boundary Header

This deliverable classifies substitution options and trade-offs for legacy elements using rediscovery evidence. It does not define target architecture, migration sequencing, or implementation-level redesign.

## Proposal Taxonomy Definitions

- `keep-as-is`: retain current element in foreseeable horizon with explicit rationale.
- `replace-with-library`: substitute bespoke/internal logic with maintained external library capability.
- `replace-with-platform`: shift responsibility to managed platform/runtime capability.
- `retire`: remove element because it is obsolete, risky, or unsupported.

## Row ID and Citation Convention

- Row ID format: `SA-###`
- Citation format: `artifact#entry`
- Allowed artifacts:
  - `business-rules.md`
  - `data-model.md`
  - `contracts/integration-inventory.md`
  - `contracts/ui-and-reporting-contracts.md`
  - `open-questions.md`
  - `research.md`

Examples:

- `contracts/integration-inventory.md#INT-001`
- `business-rules.md#BR-015`
- `open-questions.md#OQ-005`

## Canonical Rediscovery Source Index

| Source | Primary Use |
|--------|-------------|
| `business-rules.md` | Rule-level behavior and contested findings |
| `data-model.md` | Invariant enforcement and persistence constraints |
| `contracts/integration-inventory.md` | External systems, endpoints, filesystem dependencies |
| `contracts/ui-and-reporting-contracts.md` | Interface boundaries and runtime access contracts |
| `open-questions.md` | Unresolved policy/intent ambiguities |
| `research.md` | Confidence rubric, evidence handling decisions |

## Substitution-Audit Table

| Row ID | Legacy element | Proposal | Reason | Trade-off | Rediscovery references | Assumption |
|--------|----------------|----------|--------|-----------|------------------------|------------|
| SA-001 | Direct JDBC SQL in JSP reporting path | replace-with-library | Reporting logic currently executes SQL/scriptlets in view layer (`business-rules.md#BR-013`) creating high coupling and low testability. | Adds dependency and refactor effort; potential behavior drift if SQL semantics are not mirrored exactly. | `business-rules.md#BR-013`; `contracts/ui-and-reporting-contracts.md#3`; `open-questions.md#OQ-007` | No |
| SA-002 | Runtime datasource fallback logic split across Liberty JNDI and embedded Derby manager | replace-with-platform | Current dual-mode persistence fallback is operationally fragile in platformized environments and indicates manual runtime responsibility (`business-rules.md#BR-015`). | Increased platform dependency and reduced local-mode flexibility without equivalent platform emulation. | `business-rules.md#BR-015`; `contracts/integration-inventory.md#INT-001`; `contracts/integration-inventory.md#INT-002` | No |
| SA-003 | Embedded Derby as primary local store (`./data/bigbadmonolith`) | replace-with-platform | File-backed embedded store path implies node-local state and cloud-hostile assumptions (`contracts/integration-inventory.md#FS-001`). | Platform database adoption introduces cost, operational policy constraints, and migration complexity. | `contracts/integration-inventory.md#INT-001`; `contracts/integration-inventory.md#FS-001`; `research.md#Filesystem dependency as integration` | No |
| SA-004 | Liberty basic registry single-user auth configuration | replace-with-platform | Auth model appears config-only/basic and unresolved for production policy (`contracts/integration-inventory.md#INT-003`, `open-questions.md#OQ-006`). | Platform IAM integration can increase configuration complexity and identity governance overhead. | `contracts/integration-inventory.md#INT-003`; `open-questions.md#OQ-006`; `contracts/ui-and-reporting-contracts.md#4` | Yes: assumes production hardening required |
| SA-005 | Documentation-only API base claim (`/api/`) without in-repo implementation evidence | retire | Rediscovery marks endpoint evidence gap (`open-questions.md#OQ-005`); non-implemented interface claims should not remain authoritative. | Removing docs may break expectations for consumers relying on undocumented external codebases. | `contracts/integration-inventory.md#EP-005`; `open-questions.md#OQ-005` | Yes: assumes current repo is source-of-truth scope |
| SA-006 | User email uniqueness at DB constraint level | keep-as-is | Constraint is explicit and robust (`data-model.md#Dedicated DB-Constraint-Only Invariants`) and supports identity integrity. | Keeps coupling to schema-level enforcement; may require app-level UX improvements for conflict handling. | `business-rules.md#BR-006`; `data-model.md#Dedicated DB-Constraint-Only Invariants` | No |
| SA-007 | Customer email non-uniqueness in schema | replace-with-library | Identity policy ambiguity (`open-questions.md#OQ-004`) suggests need for explicit domain policy enforcement layer beyond ad-hoc checks. | Library-level validation may not resolve canonical identity policy without stakeholder decision. | `data-model.md#Open Data-Model Questions (Code Alone Cannot Answer)`; `open-questions.md#OQ-004` | Yes: assumes duplicate email is undesirable |
| SA-008 | Service validation bypass via direct JSP write path (`hours.jsp`) | replace-with-library | Policy enforcement is inconsistent between service and UI path (`business-rules.md#CF-002`, `open-questions.md#OQ-002`). | Introducing shared validation library may expose latent data-quality issues during normalization. | `business-rules.md#CF-002`; `open-questions.md#OQ-002`; `data-model.md#Mixed-Enforcement Invariants and Precedence` | No |
| SA-009 | Weekend-hours warning semantics (advisory-only) | keep-as-is | Behavior is explicitly warning-oriented and unresolved at policy level (`business-rules.md#BR-009`, `open-questions.md#OQ-003`), so replacement now would overreach evidence. | Retaining advisory mode may preserve compliance ambiguity until policy is clarified. | `business-rules.md#BR-009`; `open-questions.md#OQ-003` | No |
| SA-010 | Hard-coded localhost endpoint assumptions in docs/config | replace-with-platform | Endpoint assumptions are environment-bound and not cloud-portable by default (`contracts/integration-inventory.md#EP-001`, `EP-002`, `EP-004`). | Platform endpoint abstraction may reduce local onboarding simplicity. | `contracts/integration-inventory.md#EP-001`; `contracts/integration-inventory.md#EP-002`; `contracts/integration-inventory.md#EP-004` | No |
| SA-011 | Build-time dependency source pinned to Maven Central only | keep-as-is | Current build-source integration is standard and evidence does not indicate immediate risk requiring substitution (`contracts/integration-inventory.md#INT-004`). | Retaining status quo may limit supply-chain controls if organizational policy evolves. | `contracts/integration-inventory.md#INT-004`; `research.md#Project technical context resolution` | No |
| SA-012 | Driver library path assumption `${server.config.dir}/derby` | replace-with-platform | Server-local driver fileset path indicates host-coupled runtime packaging (`contracts/integration-inventory.md#FS-003`). | Moving to platform-managed drivers may constrain runtime choice and version flexibility. | `contracts/integration-inventory.md#FS-003`; `contracts/ui-and-reporting-contracts.md#2` | No |

## Category Coverage Tracker

| Category | Status | Row IDs | Evidence-backed not-found |
|----------|--------|---------|---------------------------|
| home-grown code with modern equivalents | Covered | SA-001, SA-008 | N/A |
| dated integrations | Covered | SA-004, SA-005, SA-012 | N/A |
| unfit data stores | Covered | SA-003 | N/A |
| EOL runtimes/frameworks | Not-found (in-scope evidence) | N/A | No explicit EOL statement exists in rediscovery artifacts; evidence set documents current stack but not lifecycle status. Revisit with external lifecycle sources. References: `research.md#Platform and runtime stack`, `contracts/integration-inventory.md#Final Completeness Check` |
| cloud-hostile operational assumptions | Covered | SA-003, SA-010, SA-012 | N/A |

## Category Completeness Summary

| Check | Result |
|-------|--------|
| All five required categories represented | PASS |
| Covered categories include at least one row | PASS |
| Not-found categories include explicit evidence references | PASS |

## Assumption and Unresolved-Evidence Rules Applied

- Any row with incomplete policy certainty is marked `Assumption=Yes`.
- Assumption rows are bounded to classification only and do not introduce architecture design.
- Unresolved domain intent remains linked to `open-questions.md` entries.

## Scope Guard Checklist

- No target architecture diagrams included.
- No migration-wave or sequencing plan included.
- No implementation-level component redesign included.
- All recommendations remain classification-oriented.

## Out-of-Scope Declaration

Out of scope for this phase:

- architecture target-state design
- migration roadmap or sequencing
- detailed platform topology design
- implementation-level code refactoring plans

## Row-Level Traceability Sampling Log

Sample size: 10 rows (`SA-001` through `SA-010`)

| Row ID | Traceability Check | Notes |
|--------|--------------------|-------|
| SA-001 | PASS | BR and interface contract references align with direct JSP SQL observation |
| SA-002 | PASS | BR/runtime and integration IDs align with dual datasource behavior |
| SA-003 | PASS | Filesystem + DB integration entries validate host-local store assumption |
| SA-004 | PASS | Config-only auth + open question evidence supports substitution rationale |
| SA-005 | PASS | Documentation-only API claim linked to explicit unresolved question |
| SA-006 | PASS | DB uniqueness evidence is explicit and stable |
| SA-007 | PASS | Open question + model evidence supports ambiguity-based classification |
| SA-008 | PASS | Contested finding and open question references are consistent |
| SA-009 | PASS | Warning semantics and unresolved policy references align |
| SA-010 | PASS | Endpoint config/docs references support environment-bound assumption |

Sampling outcome: 10/10 pass (100% traceability in sampled set)

## Final Audit-Only Compliance Signoff

- Reviewer check 1 (classification scope): PASS
- Reviewer check 2 (no architecture design drift): PASS
- Reviewer check 3 (all rows evidence-linked): PASS
- Reviewer check 4 (contract column/proposal compliance): PASS

Status: Ready for feature-level review and implementation task completion validation.