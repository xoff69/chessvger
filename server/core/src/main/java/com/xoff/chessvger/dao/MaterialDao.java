package com.xoff.chessvger.dao;

import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class MaterialDao {


     private static final String INSERT_SQL =
            "INSERT INTO %s.material_games (value, game_ids) " +
                    "VALUES (?, ARRAY[?]::BIGINT[]) " +
                    "ON CONFLICT (value) DO UPDATE " +
                    "SET game_ids = %s.material_games.game_ids || EXCLUDED.game_ids";

    public static void insert(Connection connection, String schemaName, Long gameId, List<CoupleZobristMaterial> list)
            throws SQLException {

        Set<AbstractMap.SimpleEntry<Long, Long>> set = new HashSet<>();


        for (CoupleZobristMaterial czm : list) {
            set.add(new AbstractMap.SimpleEntry<>(gameId, czm.getMaterial()));
        }


        PreparedStatement insertEntityStmt = null;
        String sql = String.format(INSERT_SQL, schemaName, schemaName);

        try {

            insertEntityStmt =
                    connection.prepareStatement(sql);
            for (AbstractMap.SimpleEntry<Long, Long> entry : set) {
                insertEntityStmt.setLong(1, gameId);
                insertEntityStmt.setLong(2, entry.getValue());
                insertEntityStmt.executeUpdate();
            }


            insertEntityStmt.close();
        } catch (SQLException e) {
            log.error(sql, e);
        }

    }
}
