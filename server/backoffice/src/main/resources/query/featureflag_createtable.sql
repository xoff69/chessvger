CREATE TABLE common.feature_flag (
          id SERIAL PRIMARY KEY,    project VARCHAR(10) NOT NULL,
         name VARCHAR(50) NOT NULL,   allowed_ids JSONB NOT NULL,
         forbidden_ids JSONB NOT NULL,    start_date DATE NOT NULL,
          end_date DATE NOT NULL,   active BOOLEAN NOT NULL,
         environment VARCHAR(10) NOT NULL);