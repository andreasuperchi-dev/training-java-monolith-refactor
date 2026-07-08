# Contract: Substitution Audit Output and Traceability

This contract defines the required output structure for the substitution audit deliverable.

## 1) Required Output Table

The audit MUST contain a substitution table with the following columns exactly:

- `legacy element`
- `proposal`
- `reason`
- `trade-off`

## 2) Proposal Value Contract

The `proposal` field MUST be one of:

- `keep-as-is`
- `replace-with-library`
- `replace-with-platform`
- `retire`

No other value is allowed.

## 3) Traceability Contract

Each table row MUST include a link or citation to at least one specific entry in:

- `specs/001-rediscover-legacy-monolith/business-rules.md`
- `specs/001-rediscover-legacy-monolith/data-model.md`
- `specs/001-rediscover-legacy-monolith/contracts/integration-inventory.md`
- `specs/001-rediscover-legacy-monolith/contracts/ui-and-reporting-contracts.md`
- `specs/001-rediscover-legacy-monolith/open-questions.md`
- `specs/001-rediscover-legacy-monolith/research.md`

Accepted locator forms:

- section heading reference
- row identifier reference
- explicit question ID reference

## 4) Coverage Contract

The audit MUST show coverage for all required categories:

1. home-grown code with modern equivalents
2. dated integrations
3. unfit data stores
4. EOL runtimes/frameworks
5. cloud-hostile operational assumptions

If a category has no findings, output MUST include an explicit `not-found` line with supporting rediscovery reference(s).

## 5) Scope Boundary Contract

The audit MUST remain analysis-only.

The following are explicitly disallowed:

- target architecture diagrams
- migration wave or sequencing plans
- implementation-level component redesign
- platform topology design

## 6) Minimum Row Quality Contract

For each substitution row:

- `reason` MUST explain why the proposal fits the cited evidence.
- `trade-off` MUST include at least one downside or risk.
- assumptions (if any) MUST be explicitly labeled.

## 7) Validation Checklist

A completed audit output is contract-compliant only if all conditions are true:

- table column set is exact
- proposal values are from allowed set only
- every row is traceable to rediscovery evidence
- every required category is represented as covered or evidence-backed not-found
- no disallowed architecture-design content is present