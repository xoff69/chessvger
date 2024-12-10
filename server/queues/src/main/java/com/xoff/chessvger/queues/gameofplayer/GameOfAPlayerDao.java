package com.xoff.chessvger.queues.gameofplayer;

import com.xoff.chessvger.queues.util.CommonDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameOfAPlayerDao {
  private static final String INSERT_SQL =
      "INSERT INTO chessvger.game_of_a_player (igameId, playerId) " + "VALUES ( ?,?)";

  public void insertEntity(GameOfAPlayer player) throws SQLException, ClassNotFoundException {

    // TODO faire un upsert
    Connection connection = CommonDao.getConnection();

    // Setup the connection (replace with your actual database connection details)
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);

      preparedStatement.setLong(1, player.getGameId());
      preparedStatement.setLong(2, player.getPlayerId());

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error while inserting player into game of player table.", e);
    }
  }
}
