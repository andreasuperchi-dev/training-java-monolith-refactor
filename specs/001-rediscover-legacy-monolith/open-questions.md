# Open Questions: Rediscover Legacy Monolith

This file tracks uncertainties that cannot be concluded from code/configuration alone.

## Unresolved-Evidence Handling Rules

- Do not treat unresolved behavior as fact in any deliverable.
- Every open question must include:
  - Why evidence is insufficient
  - Candidate interpretations
  - Required validator/owner
  - Closure criteria
- If conflicting evidence exists, preserve both interpretations and link each to source artifacts.
- Questions remain open until closure criteria can be objectively satisfied.

## Question Backlog

### OQ-001 Is user name/email mapping in users JSP intentional or defect?

- Story linkage: `US1`, `US2`
- Evidence:
  - `src/main/webapp/users.jsp` (`new User(name, email)`)
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/User.java` constructor order `(email, name)`
- Why unresolved: Code indicates potential swap, but intended UX/legacy semantics are unknown.
- Candidate interpretations:
  - A) Constructor call is a defect and persisted data is swapped.
  - B) Constructor or form labels are intentionally unconventional.
- Needed validator: Product/domain owner plus data steward.
- Closure criteria: Confirm intended canonical mapping and validate with production-like sample records.

### OQ-002 Which validation path is authoritative for hours logging policy?

- Story linkage: `US1`, `US2`
- Evidence:
  - `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` (`validateBillableHour`)
  - `src/main/webapp/hours.jsp` direct DAO save path
- Why unresolved: Service validation expresses policy, but primary UI write path bypasses it.
- Candidate interpretations:
  - A) Service validation is intended policy but not consistently wired.
  - B) JSP checks are intended minimum policy; service checks are advisory/unused.
- Needed validator: Engineering owner plus billing process owner.
- Closure criteria: Confirm authoritative entry path and policy source in production operations.

### OQ-003 Are weekend billable hours valid or policy violations?

- Story linkage: `US1`, `US2`
- Evidence:
  - `BillingService.validateBillableHour` warning message for weekend logging.
- Why unresolved: Code emits warning but does not block; policy intent is not explicit.
- Candidate interpretations:
  - A) Weekend work is valid with advisory warning.
  - B) Weekend work should be rejected, but enforcement is missing.
- Needed validator: Billing compliance owner.
- Closure criteria: Approved policy statement with expected system behavior.

### OQ-004 Should duplicate customer emails be allowed?

- Story linkage: `US2`
- Evidence:
  - `customers` schema has no uniqueness constraint on `email`.
- Why unresolved: Schema permits duplicates, but business identity policy is not encoded.
- Candidate interpretations:
  - A) Duplicates are acceptable (email not a unique customer key).
  - B) Duplicates are not acceptable and schema constraint is missing.
- Needed validator: Customer operations owner.
- Closure criteria: Business identity policy documented and matched to enforcement strategy.

### OQ-005 Are README-mentioned REST API endpoints implemented or stale?

- Story linkage: `US3`
- Evidence:
  - `README.md` references `/api/` access.
  - Current source scan did not find JAX-RS/servlet endpoint classes exposing that path.
- Why unresolved: Documentation and observed code are inconsistent.
- Candidate interpretations:
  - A) Endpoint code exists outside current repository scope.
  - B) Documentation is stale and no API exists in this codebase.
- Needed validator: Repository maintainer.
- Closure criteria: Locate endpoint source evidence or update authoritative docs scope statement.

### OQ-006 Is basic auth in Liberty config development-only or broader policy?

- Story linkage: `US3`
- Evidence:
  - `src/main/liberty/config/server.xml` basic registry and `applicationSecurity` settings.
- Why unresolved: Configuration exists, but environment policy boundaries are undocumented.
- Candidate interpretations:
  - A) Intended for local/dev only.
  - B) Intended as baseline auth mode in deployed environments.
- Needed validator: Security/platform owner.
- Closure criteria: Environment-specific auth policy documented and mapped to runtime configuration.

### OQ-007 Should reporting logic be service-owned or JSP-owned in current-state documentation?

- Story linkage: `US1`, `US3`
- Evidence:
  - Service provides reporting/billing methods.
  - `reports.jsp` runs direct SQL calculations.
- Why unresolved: Multiple logic owners exist; authoritative one is unclear.
- Candidate interpretations:
  - A) JSP SQL is canonical in this legacy app.
  - B) Service methods are intended canonical path; JSP is bypass drift.
- Needed validator: Application maintainer.
- Closure criteria: Confirm canonical report path and annotate legacy drift status.

## Status Summary

- Open: 7
- Closed: 0
- High-impact for reviewer validation: `OQ-001`, `OQ-002`, `OQ-005`

## Integration-Related Question Consolidation

- Integration scope questions: `OQ-005`, `OQ-006`, `OQ-007`.
- External endpoint uncertainty: captured in `OQ-005` and linked from integration inventory.
- Auth/runtime policy uncertainty: captured in `OQ-006` and linked from interface contracts.
- Canonical reporting path uncertainty: captured in `OQ-007` and linked from business-rules/integration artifacts.

## Closure-Readiness Checklist

- Each open question has explicit evidence citations.
- Each open question names a validator/owner.
- Each open question includes objective closure criteria.
- No unresolved question is represented as a confirmed rule in `business-rules.md`.
- Integration-related unknowns are cross-linked from `contracts/integration-inventory.md`.

Result: all current unresolved items are capture-ready for domain review closure.
