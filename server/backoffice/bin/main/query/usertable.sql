CREATE TABLE admin.users
  (
     id           SERIAL PRIMARY KEY,
     login        VARCHAR(50) NOT NULL UNIQUE,
     description  TEXT,
     password     VARCHAR(255) NOT NULL,
     date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     date_update  TIMESTAMP,
     profil       BOOLEAN DEFAULT false
  ); -- Profil: admin (TRUE) ou utilisateur normal (FALSE)
