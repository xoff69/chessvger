CREATE TABLE common.tenant (
                        tenant_id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        startDate DATE NOT NULL DEFAULT CURRENT_DATE,
                        lastUpdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);