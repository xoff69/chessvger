package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.game.CommonGame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {
  /**
   * tenant schema + db id
   */
  public static final String TABLE_GAME=" create table %s.db_%s_common_game (black_elo integer, date date, event_date date, favori boolean not null, interet integer not null,\n" +
  "is_deleted boolean, last_position integer, nb_coups integer, partie_analysee boolean, theorique boolean not null,\n" +
  "white_elo integer, black_fide_id bigint, id bigint not null, informations_fait_de_jeu bigint, last_update bigint,\n" +
  "white_fide_id bigint, moves varchar(3000), black_player varchar(255), black_title varchar(255), eco varchar(255),\n" +
  "event varchar(255), first_move varchar(255), opening varchar(255), result varchar(255), round varchar(255),\n" +
 " site varchar(255), white_player varchar(255), white_title varchar(255), primary key (id))\n" +
  ";";


  private static final String INSERT_SQL =
      "INSERT INTO %s.common_game (event, site, partie_analysee, date, event_date, round, result, " +
          "white_title, black_title, white_elo, black_elo, eco, opening, white_fide_id, black_fide_id, " +
          "nb_coups, last_position, informations_fait_de_jeu, last_update, is_deleted, first_move, moves, " +
          "interet, theorique, favori,id,white_player,black_player) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

  public int count(String schemaName) throws SQLException {
    try (Connection connection = CommonDao.getConnection()) {
      ;

      String sqlQuery = "SELECT COUNT(*) FROM "+schemaName+".common_game";
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

  public void insertCommonGame(CommonGame commonGame,String schemaName) throws SQLException, ClassNotFoundException {

    String sql = String.format(INSERT_SQL, schemaName);
    // TODO faire un upsert
    try (Connection connection = CommonDao.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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
