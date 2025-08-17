# Finance Hub Backend (Java 21 + Spring Boot + PostgreSQL)

Production-ready backend for a Personal Finance Hub.

## Stack
- Java 21, Spring Boot 3.x (Web, Validation, Security, Data JPA, Actuator)
- PostgreSQL 16 + Flyway
- JWT (access/refresh) with rotation
- MapStruct, Lombok
- OpenAPI 3 (springdoc-openapi + Swagger UI)
- Observability: Micrometer + Prometheus, JSON logs, correlation id
- Test: JUnit 5, Spring Boot Test, Testcontainers
- Lint/Style: Spotless + Google Java Format, Checkstyle
- Dev via Docker Compose (Postgres, Vault, App)

## Quick Start (Dev)
```bash
# 1) Build
mvn -q -DskipTests package

# 2) Run infra + app (dev profile uses LocalInMemory SecretProvider)
docker compose up --build
```

Services:
- App: http://localhost:8080  (Swagger: /swagger-ui.html)
- Postgres: localhost:5432 (db: financehub, user: financehub, pass: financehub)
- Vault (optional in dev): http://localhost:8200 (token: root)

### Environment
Key env variables (see `docker-compose.yml` and `application.yml`):
- `JWT_ACCESS_SECRET` (required)
- `JWT_REFRESH_SECRET` (required)
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`

## Run Tests
```bash
mvn -q -P test verify
```

## OpenAPI
- Generated at runtime at `/v3/api-docs`
- Swagger UI at `/swagger-ui.html`
- You can export to `openapi.json` via:
  ```bash
  curl -s http://localhost:8080/v3/api-docs > openapi.json
  ```

## Profiles
- `dev` (default when using Compose): seed data, LocalInMemory SecretProvider
- `test`: Testcontainers
- `prod`: hardened defaults, Vault SecretProvider

## Troubleshooting
- If migrations fail, ensure the DB is clean or drop the schema and restart.
- For Vault integration locally, keep using the LocalInMemory provider unless you need real Vault.

