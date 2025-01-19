CREATE TABLE common.tenant (
                        tenant_id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        date_updated  TIMESTAMP
);