package com.xoff.chessvger.dao;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.time.Instant;
@Slf4j
public class PositionClickHouseDao {


private static final String GET="SELECT\n" +
        "  positionId,\n" +
        "  groupArrayMerge(gameIds) AS allGames\n" +
        "FROM position_games_agg\n" +
        "GROUP BY positionId\n;";

    public static void insert(Connection connection, String schemaName, long tenantId, long databaseId, Long gameId, List<CoupleZobristMaterial> list)
        throws SQLException {

    final String sql = "INSERT INTO position_games (tenantId, databaseId, positionId, gameIds) VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
        for (CoupleZobristMaterial czm : list) {
            insertStmt.setLong(1, tenantId);
            insertStmt.setLong(2, databaseId);
            insertStmt.setLong(3, czm.getZobrist());

            Integer[] games = new Integer[]{gameId.intValue()};
            Array array = connection.createArrayOf("Int32", games);
            insertStmt.setArray(4, array);

            insertStmt.addBatch();
        }

        insertStmt.executeBatch();
    } catch (SQLException e) {
        log.error("error insert position", e);
        e.printStackTrace();
        throw e; // remonte l'exception si besoin
    }
}
    public static void main(String[] args) {
        System.out.printf(GET, "tenantId", "databaseId", "gameId", "allGames");
        try (Connection connection = CommonDao.getConnectionClickHouse()) {
            long tenantId = 10L;
            long databaseId = 1L;
            Long gameId = 123456L;

            List<CoupleZobristMaterial> list = new ArrayList<>();
            list.add(new CoupleZobristMaterial(987654321L,0L));
            list.add(new CoupleZobristMaterial(123456789L,0L));

            PositionClickHouseDao.insert(connection, "default", tenantId, databaseId, gameId, list);
            System.out.println("Insert success!");

        } catch (SQLException e) {

            System.err.println("Insert failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}