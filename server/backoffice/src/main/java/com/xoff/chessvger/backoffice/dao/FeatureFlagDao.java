package com.xoff.chessvger.backoffice.dao;

public class FeatureFlagDao {
  public static final String TABLE_FEATURE_FLAG ="CREATE TABLE feature_flag (\n" +
      "    id SERIAL PRIMARY KEY,\n" + "    project VARCHAR(10) NOT NULL,\n" +
      "    name VARCHAR(50) NOT NULL,\n" + "    allowed_ids JSONB NOT NULL,\n" +
      "    forbidden_ids JSONB NOT NULL,\n" + "    start_date DATE NOT NULL,\n" +
      "    end_date DATE NOT NULL,\n" + "    active BOOLEAN NOT NULL,\n" +
      "    environment VARCHAR(10) NOT NULL\n" + ");";
  public static final String INSERT_FEATURE_FLAG="INSERT INTO feature_flag (project, name, allowed_ids, forbidden_ids, start_date, end_date, active, environment)\n" +
      "VALUES (\n" + "    'EQ',\n" + "    'PartnerFee',\n" +
      "    '[1, 2, 3]', -- JSON pour allowed_ids\n" +
      "    '[4, 5, 6]', -- JSON pour forbidden_ids\n" + "    '2024-01-01',\n" +
      "    '2024-12-31',\n" + "    true,\n" + "    'dev'\n" + ");";
}
