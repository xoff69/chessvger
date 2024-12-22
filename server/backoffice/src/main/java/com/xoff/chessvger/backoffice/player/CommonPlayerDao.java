package com.xoff.chessvger.backoffice.player;


import com.xoff.chessvger.backoffice.dao.CommonDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommonPlayerDao {
  private static String SCHEMA = "common";
  private static final String INSERT_SQL =
      "INSERT INTO "+SCHEMA+".common_player (id, fide_id, name, country, sex, title, w_title, o_title, " +
          "foa_title, rating, games, k, rapid_rating, rapid_games, rapidk, blitz_rating, " +
          "blitz_games, blitzk, birthday, flag) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  public void insertCommonPlayer(CommonPlayer player) throws SQLException, ClassNotFoundException {

    // TODO faire un upsert

//System.out.println("Inserting common player"+player);
// TODO a faire partout
    try (Connection connection = CommonDao.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
      preparedStatement.setLong(1, player.getId());
      preparedStatement.setString(2, player.getFideId());
      preparedStatement.setString(3, player.getName());
      preparedStatement.setString(4, player.getCountry());
      preparedStatement.setString(5, player.getSex());
      preparedStatement.setString(6, player.getTitle());
      preparedStatement.setString(7, player.getWTitle());
      preparedStatement.setString(8, player.getOTitle());
      preparedStatement.setString(9, player.getFoaTitle());
      preparedStatement.setString(10, player.getRating());
      preparedStatement.setString(11, player.getGames());
      preparedStatement.setString(12, player.getK());
      preparedStatement.setString(13, player.getRapidRating());
      preparedStatement.setString(14, player.getRapidGames());
      preparedStatement.setString(15, player.getRapidK());
      preparedStatement.setString(16, player.getBlitzRating());
      preparedStatement.setString(17, player.getBlitzGames());
      preparedStatement.setString(18, player.getBlitzK());
      preparedStatement.setString(19, player.getBirthday());
      preparedStatement.setString(20, player.getFlag());

      // Execute the query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error while inserting player into chessvger.common_player table.", e);
    }

  }
}

