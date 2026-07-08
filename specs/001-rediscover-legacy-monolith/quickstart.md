# Quickstart Validation Guide

This guide validates the rediscovery artifacts end-to-end using repository evidence and runtime smoke checks.

## Prerequisites

- Java 21+ available
- Project dependencies resolved by Gradle wrapper
- Working directory: repository root

## Deliverables Index

- Business rules: `business-rules.md`
- Data model summary: `data-model.md`
- Integration inventory: `contracts/integration-inventory.md`
- Interface contracts: `contracts/ui-and-reporting-contracts.md`
- Open questions: `open-questions.md`
- Research and rubric: `research.md`

## Cross-Artifact Traceability Matrix (Shell)

| Artifact | Key IDs/Sections | Evidence Source Families | Open Question Linkage |
|----------|------------------|---------------------------|-----------------------|
| `business-rules.md` | `BR-*`, `CF-*` | Service, DAO, JSP, config | `OQ-001`, `OQ-002`, `OQ-007` |
| `data-model.md` | Entity/Invariant sections | Entity classes, DAO, DDL, JSP joins | `OQ-003`, `OQ-004` |
| `contracts/integration-inventory.md` | `INT-*`, `FS-*`, `EP-*` | Config, code paths, docs | `OQ-005`, `OQ-006`, `OQ-007` |
| `contracts/ui-and-reporting-contracts.md` | Interface sections 1-6 | JSP routes, server.xml, service/DAO | `OQ-005`, `OQ-006`, `OQ-007` |

## 1) Build and baseline checks

Run:

```bash
./gradlew clean build
```

Expected outcome:
- Build succeeds.
- WAR artifact is generated.

## 2) Start application (Liberty dev mode)

Run:

```bash
./liberty-dev.sh
```

Expected outcome:
- Server starts and app is reachable.
- Startup logs indicate either Liberty datasource mode or embedded fallback mode.

## 3) Validate interface contracts

Use `contracts/ui-and-reporting-contracts.md` while verifying the following pages:

- `http://localhost:9080/big-bad-monolith/`
- `http://localhost:9080/big-bad-monolith/customers.jsp`
- `http://localhost:9080/big-bad-monolith/users.jsp`
- `http://localhost:9080/big-bad-monolith/categories.jsp`
- `http://localhost:9080/big-bad-monolith/hours.jsp`
- `http://localhost:9080/big-bad-monolith/reports.jsp`

Expected outcome:
- Each page responds.
- Form parameters and report behaviors align with documented contract sections.

## 4) Validate data-model assertions

Cross-check key data model entries using `data-model.md` and referenced sources:

- Entity fields: `entity/*.java`
- Constraints and relationships: DDL in connection manager classes
- Enforcement source split: service validation vs DAO defensive checks vs DB constraints

Expected outcome:
- Each modeled invariant has explicit source evidence.
- Mixed enforcement invariants are clearly labeled.

## 5) Validate research decisions and confidence model

Review `research.md` and confirm:

- Evidence confidence levels are defined and usable.
- Integration confidence categories can classify all discovered integration entries.

Expected outcome:
- No rediscovery claim requires unstated assumptions.
- Uncertain areas are expressed as open questions.

## 6) Reviewer traceability sample

Pick at least 10 documented rules/invariants/integration entries from final rediscovery outputs and test traceability:

1. Read the plain-English statement.
2. Follow cited file references.
3. Confirm the claim in code/config.
4. Mark pass/fail.

Expected outcome:
- At least 95% traceability pass rate for the sample (matches SC-007).

Traceability sampling guidance:

- Ensure sample includes at least:
	- 4 business rules (`BR-*`)
	- 3 data-model invariants/relationships
	- 3 integration entries (`INT-*`, `FS-*`, or `EP-*`)
- Mark each sample check as `PASS`, `FAIL`, or `OPEN`.
- Any `FAIL` or `OPEN` item must map to an existing open question or create a new question entry.

## 7) Known open validation areas

- Confirm whether README-mentioned REST API endpoints exist in code or are stale documentation.
- Confirm intended behavior for weekend hour logging (warning vs policy violation).
- Confirm whether `users.jsp` constructor argument order is intentional or defect.

## Reviewer Handoff Checklist

- Confirm each business rule in `business-rules.md` has explicit evidence and example.
- Confirm each invariant in `data-model.md` includes enforcement-source labeling.
- Confirm each integration entry includes confidence label and evidence reference.
- Confirm unresolved items are captured in `open-questions.md` and not presented as facts.
- Confirm traceability sample achieves target threshold or record exceptions.
