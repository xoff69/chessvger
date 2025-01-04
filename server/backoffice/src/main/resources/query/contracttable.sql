CREATE TABLE common.contract (
       id SERIAL PRIMARY KEY,   price BIGINT NOT NULL,
         durationDay INT NOT NULL,   startDate TIMESTAMP NOT NULL,
         endDate TIMESTAMP NOT NULL,    databaseId BIGINT NOT NULL);