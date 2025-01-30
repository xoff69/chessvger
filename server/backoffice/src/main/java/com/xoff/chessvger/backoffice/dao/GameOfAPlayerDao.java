package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.gameofplayer.GameOfAPlayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameOfAPlayerDao {

  private static final String INSERT_SQL =
      "INSERT INTO %s.game_of_a_player (igameId, playerId) " + "VALUES ( ?,?)";

  public static void insert(Connection connection,String schemaName, Long id, String playerName, long playerFideId)
      throws SQLException {

    String sql = String.format(INSERT_SQL, schemaName);
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setLong(1, id);
      // FIXME : trouver le nom du player dans la table des plauers cache Redis
      preparedStatement.setLong(2, playerFideId==0?1:playerFideId);

      preparedStatement.executeUpdate();

  }
}
