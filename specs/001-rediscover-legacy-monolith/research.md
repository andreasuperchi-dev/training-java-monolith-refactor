# Phase 0 Research: Rediscover Legacy Monolith

## Evidence Citation Convention

- Cite each claim with repository-relative file paths and a short locator note (method name, SQL block, or config element).
- Use confidence labels consistently (`E1`-`E4`) and record weakest-link rationale when multiple evidence sources are involved.
- Separate confirmed findings from unresolved findings; unresolved findings must link to `open-questions.md`.
- For contradictions, preserve both findings with evidence and mark as contested.

## Canonical Source-File Inventory

| Area | Primary Sources | Notes |
|------|------------------|-------|
| Runtime startup and mode selection | `src/main/java/com/sourcegraph/demo/bigbadmonolith/StartupListener.java`, `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/LibertyConnectionManager.java`, `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java` | Defines datasource selection and initialization behavior |
| Core billing logic | `src/main/java/com/sourcegraph/demo/bigbadmonolith/service/BillingService.java` | Rule computation and validation signals |
| Persistence schema and constraints | `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/ConnectionManager.java`, `src/main/java/com/sourcegraph/demo/bigbadmonolith/dao/LibertyConnectionManager.java` | Source of DB constraints and relationships |
| Entity definitions | `src/main/java/com/sourcegraph/demo/bigbadmonolith/entity/*.java` | Data shape used by DAO/service layers |
| UI action paths | `src/main/webapp/*.jsp` | Entry-path behavior and direct SQL/scriptlet logic |
| Server and security configuration | `src/main/liberty/config/server.xml`, `src/main/liberty/config/bootstrap.properties` | Ports, context root, datasource, registry/auth |
| Build/dependency context | `build.gradle`, `README.md`, `SETUP.md` | Build-time integrations and documented endpoints |

## Confidence Labeling Rubric (E1-E4)

- `E1 Confirmed Code Path`: Executable path clearly observed in code flow with concrete effect.
- `E2 Deterministic Static Evidence`: Deterministic static logic suggests behavior, but end-to-end operational context is not fully proven.
- `E3 Config or Schema Evidence`: Behavior implied by configuration or schema constraints without direct business-path confirmation.
- `E4 Inferred or Unverified`: Plausible interpretation lacks sufficient confirmatory evidence.

Examples:

- `E1`: Customer bill total loop in `BillingService.generateCustomerBill`.
- `E2`: Dashboard revenue reflecting current category rates from JSP computation path.
- `E3`: `users.email UNIQUE` and billable-hour foreign keys in schema DDL.
- `E4`: README API claims without discoverable endpoint implementation in scope.

## Decision Log

### 1) Evidence confidence model for rediscovery outputs
- Decision: Use a four-level evidence model for all findings: `E1 Confirmed Code Path`, `E2 Deterministic Static Evidence`, `E3 Config/Schema Evidence`, `E4 Inferred/Unverified`.
- Rationale: The specification requires reviewer-verifiable claims and explicit open questions instead of guessing.
- Alternatives considered: Binary proven/unproven was too coarse for mixed evidence chains.

### 2) Rule confidence aggregation strategy
- Decision: Assign each documented rule the confidence of its weakest required evidence link (trigger, logic, persistence/output).
- Rationale: End-to-end correctness is bounded by the least certain segment, which prevents overclaiming.
- Alternatives considered: Averaging confidence or strongest-link confidence would mask weak points.

### 3) Invariant classification model
- Decision: Classify invariants by enforcement source as `Application Validation`, `Defensive Code`, `Database Constraint`, or `Mixed Enforcement`.
- Rationale: The requested deliverables explicitly call out invariants enforced by defensive code or DB constraints.
- Alternatives considered: A single validation bucket loses accountability and audit clarity.

### 4) Contradictory evidence policy
- Decision: Preserve contradictions as contested findings and create explicit open questions.
- Rationale: Legacy monolith behavior can diverge between JSP, service, DAO, and DB layers; forced interpretation violates the no-guessing constraint.
- Alternatives considered: Picking one interpretation would not be evidence-safe.

### 5) Integration inventory taxonomy
- Decision: Inventory integrations by category: `Database`, `Runtime Platform Services`, `Filesystem Dependencies`, `Inbound Endpoints`, `Outbound Endpoints`, `Build/Tooling External Sources`.
- Rationale: The feature requires every external system, filesystem path, and hard-coded endpoint.
- Alternatives considered: URL-only extraction would miss JNDI/data source and filesystem integrations.

### 6) Integration confirmation criteria
- Decision: Use confidence labels for integration entries: `CONFIRMED_CODE_BINDING`, `CONFIRMED_CONFIG_LINKED`, `INFERRED_CONFIG_ONLY`, `INFERRED_DOC_ONLY`, and explicit negative findings where no evidence exists.
- Rationale: Keeps the inventory complete while separating confirmed runtime behavior from textual/config declarations.
- Alternatives considered: Single confidence label for all entries was insufficiently precise.

### 7) Project technical context resolution
- Decision: Resolve all technical context from repository artifacts and avoid `NEEDS CLARIFICATION` markers in the plan.
- Rationale: Build/runtime stack is explicit in `build.gradle`, Liberty config, and startup classes.
- Alternatives considered: Deferring unknowns would block Phase 1 despite available evidence.

## Repository-Grounded Findings Used for Design

### Platform and runtime stack
- Decision: Treat this as a Java 21 Jakarta EE monolith on Open Liberty with Derby-backed persistence.
- Rationale: `build.gradle` sets Java 21 and Liberty plugin; `server.xml` configures Jakarta EE and Derby data source.
- Alternatives considered: Treating it as generic servlet app without Liberty-specific behavior was rejected.

### Dual database access behavior
- Decision: Model persistence as two-mode access: Liberty JNDI datasource first, embedded Derby fallback.
- Rationale: `LibertyConnectionManager` attempts JNDI lookup then falls back to `ConnectionManager`.
- Alternatives considered: Single-mode DB behavior was rejected as incomplete.

### Filesystem dependency as integration
- Decision: Explicitly track DB and library filesystem paths as integration surfaces.
- Rationale: DB path is hard-coded/configured in multiple places and directly affects runtime data and portability.
- Alternatives considered: Treating local filesystem as internal-only and out-of-scope was rejected.

### Reporting logic source of truth
- Decision: Treat reports as SQL-computed outputs in JSP, not service-layer reports only.
- Rationale: `reports.jsp` executes SQL directly via `DriverManager` and calculates totals.
- Alternatives considered: Assuming all report logic goes through `BillingService` was rejected.

## Clarification Status

All Technical Context placeholders in `plan.md` were resolved from repository evidence. Remaining unknowns are domain-level (business intent not inferable from code) and are captured as open questions in design artifacts.
