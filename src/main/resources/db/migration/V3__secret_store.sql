-- src/main/resources/db/migration/V3__secret_store.sql
CREATE TABLE secret_store (
  id BIGSERIAL PRIMARY KEY,
  ref VARCHAR(160) NOT NULL UNIQUE,
  cipher_text TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  rotated_at TIMESTAMPTZ
);
CREATE UNIQUE INDEX ux_secret_store_ref ON secret_store(ref);