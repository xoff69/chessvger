package com.xoff.chessvger.backoffice.game;


import com.xoff.chessvger.backoffice.util.CommonDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonGameDao {

  private static String SCHEMA = "tenant_admin";

  private static final String INSERT_SQL =
      "INSERT INTO "+SCHEMA+".common_game (event, site, partie_analysee, date, event_date, round, result, " +
          "white_title, black_title, white_elo, black_elo, eco, opening, white_fide_id, black_fide_id, " +
          "nb_coups, last_position, informations_fait_de_jeu, last_update, is_deleted, first_move, moves, " +
          "interet, theorique, favori,id,white_player,black_player) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

  public int count() throws SQLException {
    try (Connection connection = CommonDao.getConnection()) {
      ;

      String sqlQuery = "SELECT COUNT(*) FROM "+SCHEMA+".common_game";
      PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
      ;
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          int count = resultSet.getInt(1);
          return count;
        }
      }
    }
    return 0;
  }

  public void insertCommonGame(CommonGame commonGame) throws SQLException, ClassNotFoundException {

    // TODO faire un upsert
    try (Connection connection = CommonDao.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

      preparedStatement.setString(1, commonGame.getEvent());
      preparedStatement.setString(2, commonGame.getSite());
      preparedStatement.setBoolean(3, commonGame.isPartieAnalysee());
      preparedStatement.setDate(4, commonGame.getDate() != null ? commonGame.getDate() : null);
      preparedStatement.setDate(5,
          commonGame.getEventDate() != null ? commonGame.getEventDate() : null);
      preparedStatement.setString(6, commonGame.getRound());
      preparedStatement.setString(7, commonGame.getResult());
      preparedStatement.setString(8, commonGame.getWhiteTitle());
      preparedStatement.setString(9, commonGame.getBlackTitle());
      preparedStatement.setInt(10, commonGame.getWhiteElo());
      preparedStatement.setInt(11, commonGame.getBlackElo());
      preparedStatement.setString(12, commonGame.getEco());
      preparedStatement.setString(13, commonGame.getOpening());
      preparedStatement.setLong(14, commonGame.getWhiteFideId());
      preparedStatement.setLong(15, commonGame.getBlackFideId());
      preparedStatement.setInt(16, commonGame.getNbcoups());
      preparedStatement.setInt(17, commonGame.getLastPosition());
      preparedStatement.setLong(18, commonGame.getInformationsFaitDeJeu());
      preparedStatement.setLong(19, commonGame.getLastUpdate());
      preparedStatement.setBoolean(20, commonGame.isDeleted());
      preparedStatement.setString(21, commonGame.getFirstMove());
      preparedStatement.setString(22, commonGame.getMoves());
      preparedStatement.setInt(23, commonGame.getInteret());
      preparedStatement.setBoolean(24, commonGame.isTheorique());
      preparedStatement.setBoolean(25, commonGame.isFavori());
      preparedStatement.setLong(26, commonGame.getId());

      preparedStatement.setString(27, commonGame.getWhitePlayer());
      preparedStatement.setString(28, commonGame.getBlackPlayer());

      // Execute the query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error while inserting commonGame into commonGame table." + commonGame,
          e);
    }
  }
}
