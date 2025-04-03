package com.xoff.chessvger.dao;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class GameOfAPlayerDao {

    public static final String SELECT_COMPLEXE = """
            SELECT id_player as id, COUNT(id_game) AS game_count
            FROM main.game_of_a_player
            GROUP BY id_player
            HAVING COUNT(id_game) > 0
            ORDER BY game_count DESC
            LIMIT ? OFFSET ?
            """;
    private static final String INSERT_SQL =
            "INSERT INTO %s.game_of_a_player (id_game, id_player) " + "VALUES ( ?,?)";

    public static void insert(Connection connection, String schemaName, Long id, String playerName, long playerFideId) {

        try {
            String sql = String.format(INSERT_SQL, schemaName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);
            // FIXME : trouver le nom du player dans la table des plauers cache Redis
            long idPlayer = playerFideId == 0 ? PlayerDao.findOrCreate(playerName) : playerFideId;
            if (idPlayer != 0) {
                preparedStatement.setLong(2, idPlayer);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Error in GameOfAPlayerDao.insert " + e);
        }
    }
}
