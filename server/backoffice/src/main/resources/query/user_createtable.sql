CREATE TABLE common.users
  (
     id           SERIAL PRIMARY KEY,
     login        VARCHAR(50) NOT NULL UNIQUE,
     description  TEXT,
     password     VARCHAR(255) NOT NULL,
     date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     date_update  TIMESTAMP,
     profil       BOOLEAN DEFAULT false,
     tenantId INT NOT NULL,
     FOREIGN KEY (tenantId) REFERENCES tenant(tenantId) ON DELETE CASCADE
  ); -- Profil: admin (TRUE) ou utilisateur normal (FALSE)
