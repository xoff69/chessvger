CREATE TABLE common.tenants (
                        tenant_id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        date_updated  TIMESTAMP
);
ALTER TABLE common.tenants ADD CONSTRAINT unique_name UNIQUE (name);
