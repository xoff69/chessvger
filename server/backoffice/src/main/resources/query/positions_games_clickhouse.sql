DROP TABLE IF EXISTS position_games;

CREATE TABLE position_games (
    tenantId UInt32,
    databaseId UInt32,
    positionId UInt32,
    gameIds Array(UInt32)
) ENGINE = MergeTree()
ORDER BY positionId;
