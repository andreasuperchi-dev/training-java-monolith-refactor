# Integration Inventory

Inventory scope: repository static analysis only (code, config, scripts, docs).

## Confidence Labels

- `CONFIRMED_CODE_BINDING`: executable code directly performs integration call/lookup.
- `CONFIRMED_CONFIG_LINKED`: config declaration linked to executable lookup/binding.
- `INFERRED_CONFIG_ONLY`: configuration mentions integration without executable binding in analyzed code.
- `INFERRED_DOC_ONLY`: appears only in docs/setup instructions.

## Reconciled Integration Categories

- Database
- Runtime platform service
- Filesystem dependency
- Inbound endpoint
- Outbound endpoint
- Build/dependency source
- Documentation-only reference

## External Systems and Platform Integrations

| ID | Category | Integration | Direction | Evidence | Confidence |
|----|----------|-------------|-----------|----------|------------|
| INT-001 | Database | Apache Derby via JDBC (`jdbc:derby:./data/bigbadmonolith;create=true`) | Outbound | `dao/ConnectionManager.java`, `webapp/reports.jsp` | CONFIRMED_CODE_BINDING |
| INT-002 | Runtime platform service | Liberty JNDI datasource (`jdbc/DefaultDataSource`) | Outbound | `dao/LibertyConnectionManager.java`, `liberty/config/server.xml` | CONFIRMED_CONFIG_LINKED |
| INT-003 | Runtime platform service | Liberty basic user registry (`user1`) + application security | Inbound auth path | `liberty/config/server.xml` | INFERRED_CONFIG_ONLY |
| INT-004 | Build/dependency source | Maven Central repository | Build-time outbound | `build.gradle` | CONFIRMED_CODE_BINDING |
| INT-005 | Distribution endpoint (doc) | Git clone source `https://github.com/sourcegraph/training-java-monolith-refactor.git` | Developer workflow outbound | `SETUP.md` | INFERRED_DOC_ONLY |

## Filesystem Path Dependencies

| ID | Path | Role | Evidence | Confidence |
|----|------|------|----------|------------|
| FS-001 | `./data/bigbadmonolith` | Embedded Derby database storage path | `dao/ConnectionManager.java`, `README.md` | CONFIRMED_CODE_BINDING |
| FS-002 | `${server.output.dir}/data/bigbadmonolith` | Liberty datasource DB path | `liberty/config/server.xml`, `liberty/config/bootstrap.properties` | CONFIRMED_CONFIG_LINKED |
| FS-003 | `${server.config.dir}/derby` | Derby driver library fileset directory | `liberty/config/server.xml` | INFERRED_CONFIG_ONLY |
| FS-004 | `build/libs/big-bad-monolith-1.0-SNAPSHOT.war` | Deployable artifact path | `README.md` | INFERRED_DOC_ONLY |
| FS-005 | `src/main/liberty/config` | Liberty server config directory used by Gradle Liberty plugin | `build.gradle` | CONFIRMED_CODE_BINDING |

Filesystem completeness review note:

- Inventory includes runtime DB path(s), server library directory, config directory, and deployment artifact path references discovered in code/config/docs.

## Hard-Coded Endpoints

| ID | Endpoint | Context | Evidence | Confidence |
|----|----------|---------|----------|------------|
| EP-001 | `http://localhost:9080/big-bad-monolith/` | Documented app access point | `README.md`, `SETUP.md` | INFERRED_DOC_ONLY |
| EP-002 | `https://localhost:9443/big-bad-monolith/` | Documented HTTPS access point | `README.md` | INFERRED_DOC_ONLY |
| EP-003 | Context root `/big-bad-monolith` | Runtime web app mount point | `liberty/config/server.xml` | CONFIRMED_CONFIG_LINKED |
| EP-004 | HTTP port `9080` and HTTPS port `9443` | Liberty endpoint configuration | `liberty/config/server.xml`, `bootstrap.properties` | CONFIRMED_CONFIG_LINKED |
| EP-005 | Claimed API base `http://localhost:[port]/big-bad-monolith/api/` | Documentation claim only | `README.md` | INFERRED_DOC_ONLY |

Endpoint completeness review note:

- Inventory includes explicit context root, configured Liberty ports, and documented localhost access URLs; no confirmed outbound HTTP endpoint clients were discovered.

## Negative Findings (Useful Absence)

- No confirmed outbound HTTP client integration classes/calls were observed in analyzed Java sources.
- No messaging/broker integrations (JMS/Kafka/Rabbit) were observed.
- No external SMTP/email API integration calls were observed.

Negative-findings validation notes:

- Validation basis: static scan of in-scope Java/JSP/config artifacts listed in `research.md` canonical source inventory.
- Limitation: absence findings are repository-scope only and do not exclude environment-injected runtime integrations outside visible code/config.

## Open Integration Questions

- Are README-documented API endpoints implemented in code not currently present in this repository tree, or are they stale docs?
- Is Liberty basic auth intended for all environments or development only?
- Should direct JDBC usage in `reports.jsp` be considered authoritative integration behavior or legacy bypass path relative to datasource abstraction?

## Final Completeness Check

- External systems: documented with confidence labels and evidence links.
- Filesystem dependencies: documented with role, path, and evidence links.
- Hard-coded endpoints: documented with context and confidence labels.
- Unknowns/ambiguities: linked to open questions for closure.

Result: Complete for repository-visible scope, with unresolved items explicitly tracked in `open-questions.md`.

## Cross-Artifact Terminology Consistency Notes

- Use `integration inventory` as canonical artifact name.
- Use `confidence label` terms exactly as defined in this file.
- Use `documentation-only reference` when evidence is present only in README/SETUP text.
