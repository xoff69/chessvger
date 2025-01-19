CREATE TABLE common.users
  (
     id           SERIAL PRIMARY KEY,
     login        VARCHAR(50) NOT NULL UNIQUE,
     description  TEXT,
     password     VARCHAR(255) NOT NULL,
     date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     date_updated  TIMESTAMP,
     profil       BOOLEAN DEFAULT false,
     tenant_id INT NOT NULL
     -- TODO FOREIGN KEY (tenantId) REFERENCES common.tenant(tenantId) ON DELETE CASCADE
  ); -- Profil: admin (TRUE) ou utilisateur normal (FALSE)
