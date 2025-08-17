-- Users
CREATE TABLE app_user (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(190) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
CREATE INDEX idx_user_email ON app_user(email);

-- BankAccount
CREATE TABLE bank_account (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
  name VARCHAR(120) NOT NULL,
  type VARCHAR(30) NOT NULL,
  balance NUMERIC(18,2) NOT NULL DEFAULT 0,
  institution VARCHAR(120) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
CREATE INDEX idx_bank_account_user ON bank_account(user_id);

-- Transaction
CREATE TABLE tx_transaction (
  id BIGSERIAL PRIMARY KEY,
  bank_account_id BIGINT NOT NULL REFERENCES bank_account(id) ON DELETE RESTRICT,
  description VARCHAR(255) NOT NULL,
  amount NUMERIC(18,2) NOT NULL,
  type VARCHAR(20) NOT NULL,
  date DATE NOT NULL,
  category VARCHAR(80) NOT NULL,
  external_ref VARCHAR(120),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
CREATE INDEX idx_tx_account ON tx_transaction(bank_account_id);
CREATE INDEX idx_tx_date ON tx_transaction(date);
CREATE INDEX idx_tx_category ON tx_transaction(category);

-- BillingSource
CREATE TABLE billing_source (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(160) NOT NULL,
  type VARCHAR(40) NOT NULL,
  website VARCHAR(200),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

-- RecurringBill
CREATE TABLE recurring_bill (
  id BIGSERIAL PRIMARY KEY,
  billing_source_id BIGINT NOT NULL REFERENCES billing_source(id) ON DELETE RESTRICT,
  payer_user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
  name VARCHAR(160) NOT NULL,
  type VARCHAR(40) NOT NULL,
  category VARCHAR(80) NOT NULL,
  amount_policy VARCHAR(20) NOT NULL,
  fixed_amount NUMERIC(18,2),
  billing_cycle VARCHAR(40) NOT NULL,
  due_day SMALLINT NOT NULL,
  notes VARCHAR(500),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

-- BillCharge
CREATE TABLE bill_charge (
  id BIGSERIAL PRIMARY KEY,
  recurring_bill_id BIGINT NOT NULL REFERENCES recurring_bill(id) ON DELETE RESTRICT,
  reference_month VARCHAR(7) NOT NULL, -- YYYY-MM
  amount NUMERIC(18,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  due_date DATE NOT NULL,
  paid_date DATE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
CREATE INDEX idx_charge_recurring ON bill_charge(recurring_bill_id);
CREATE INDEX idx_charge_month ON bill_charge(reference_month);
CREATE INDEX idx_charge_status ON bill_charge(status);

-- Budget
CREATE TABLE budget (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
  period VARCHAR(20) NOT NULL,
  notes VARCHAR(400),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

-- BudgetCategory
CREATE TABLE budget_category (
  id BIGSERIAL PRIMARY KEY,
  budget_id BIGINT NOT NULL REFERENCES budget(id) ON DELETE RESTRICT,
  category_name VARCHAR(80) NOT NULL,
  limit_amount NUMERIC(18,2) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

-- Goal
CREATE TABLE goal (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
  title VARCHAR(160) NOT NULL,
  target_amount NUMERIC(18,2) NOT NULL,
  deadline_date DATE NOT NULL,
  current_progress NUMERIC(18,2),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

-- Credentials (polymorphic owner)
CREATE TABLE credentials (
  id BIGSERIAL PRIMARY KEY,
  owner_type VARCHAR(40) NOT NULL,
  owner_id BIGINT NOT NULL,
  username VARCHAR(160) NOT NULL,
  secret_ref VARCHAR(160),
  token_ref VARCHAR(160),
  provider_notes VARCHAR(400),
  rotated_at TIMESTAMP WITH TIME ZONE,
  last_success_at TIMESTAMP WITH TIME ZONE,
  error_count INT DEFAULT 0 NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
CREATE INDEX idx_credentials_owner ON credentials(owner_type, owner_id);

-- RefreshToken (for rotation)
CREATE TABLE refresh_token (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
  token_hash VARCHAR(255) NOT NULL,
  expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
  revoked BOOLEAN NOT NULL DEFAULT false,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
CREATE INDEX idx_refresh_user ON refresh_token(user_id);
