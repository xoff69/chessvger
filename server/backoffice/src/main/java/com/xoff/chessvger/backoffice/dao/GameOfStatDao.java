package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameOfStatDao {
    private static final String INSERT_SQL = "INSERT INTO  %s.game_of_stat (game_id,stat_id) VALUES (?,?)";


    public static void insert(Connection connection, String schemaName, long gameId, long statId)
            throws SQLException {

        PreparedStatement insertEntityStmt = null;
        String sql = String.format(INSERT_SQL, schemaName);
        try {

            insertEntityStmt =
                    connection.prepareStatement(sql);

            insertEntityStmt.setLong(1, gameId);
            insertEntityStmt.setLong(2, statId);
            insertEntityStmt.executeUpdate();


            connection.commit();
            insertEntityStmt.close();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Annuler la transaction en cas d'erreur
            }
            e.printStackTrace();
        }
    }
}
