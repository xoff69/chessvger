CREATE TABLE common.database ( id SERIAL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
      description TEXT,
      date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      date_update TIMESTAMP
      );