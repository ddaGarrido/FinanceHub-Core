-- Seed dev data
INSERT INTO app_user(name, email, password_hash, role) VALUES
 ('Alice Doe', 'alice@example.com', '$argon2id$v=19$m=65536,t=3,p=1$ZmFrZXNhbHQ$Wb3B0q2fD8kO2z2m8G1f7Q', 'USER'),
 ('Bob Admin', 'bob@example.com', '$argon2id$v=19$m=65536,t=3,p=1$ZmFrZXNhbHQ$Wb3B0q2fD8kO2z2m8G1f7Q', 'ADMIN');

INSERT INTO bank_account(user_id, name, type, balance, institution) VALUES
 (1,'Main Checking','checking', 2500.00,'ACME Bank'),
 (1,'Emergency Savings','savings', 15000.00,'ACME Bank');

INSERT INTO tx_transaction(bank_account_id, description, amount, type, date, category, external_ref) VALUES
 (1,'Salary', 5000.00,'income','2025-07-30','Income','PAYROLL123'),
 (1,'Groceries', -320.45,'expense','2025-08-01','Groceries',null),
 (1,'Electricity Bill', -210.00,'expense','2025-08-05','Utilities',null);

INSERT INTO billing_source(name, type, website) VALUES
 ('LightCo','energy','https://light.example.com'),
 ('TeleFast','telecom','https://telefast.example.com');

INSERT INTO recurring_bill(billing_source_id, payer_user_id, name, type, category, amount_policy, fixed_amount, billing_cycle, due_day, notes) VALUES
 (1,1,'Home Electricity','energy','Utilities','variable',null,'monthly',10,'Billed per kWh'),
 (2,1,'Internet Fiber','telecom','Internet','fixed',120.00,'monthly',5,'500Mbps');

INSERT INTO bill_charge(recurring_bill_id, reference_month, amount, status, due_date, paid_date) VALUES
 (1,'2025-07', 205.30,'paid','2025-07-10','2025-07-09'),
 (1,'2025-08', 210.00,'open','2025-08-10',null),
 (2,'2025-07', 120.00,'paid','2025-07-05','2025-07-05'),
 (2,'2025-08', 120.00,'open','2025-08-05',null);

INSERT INTO budget(user_id, period, notes) VALUES (1,'2025-08','August budget');
INSERT INTO budget_category(budget_id, category_name, limit_amount) VALUES
 (1,'Groceries', 1000.00),
 (1,'Utilities', 500.00),
 (1,'Transport', 400.00);

INSERT INTO goal(user_id, title, target_amount, deadline_date, current_progress) VALUES
 (1,'New Laptop', 8000.00, '2025-12-01', 2500.00);
