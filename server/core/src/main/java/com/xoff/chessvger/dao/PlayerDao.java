package com.xoff.chessvger.dao;

import com.xoff.chessvger.model.CommonPlayer;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class PlayerDao {
    public static final String TABLE_PLAYER = "create table " + CommonDao.COMMON_SCHEMA + ".common_player (id bigint not null, birthday varchar(255),\n" +
            "                                   blitz_games varchar(255), blitz_rating varchar(255), blitzk varchar(255),\n" +
            "                                   country varchar(255), fide_id varchar(255), flag varchar(255),\n" +
            "                                   foa_title varchar(255), games varchar(255), k varchar(255), name varchar(255),\n" +
            "                                   o_title varchar(255), rapid_games varchar(255), rapid_rating varchar(255),\n" +
            "                                   rapidk varchar(255), rating varchar(255), sex varchar(255), title varchar(255),\n" +
            "                                   w_title varchar(255), primary key (id))\n" + ";";
    private static final String INSERT_SQL =
            "INSERT INTO " + CommonDao.COMMON_SCHEMA + ".common_player (id, fide_id, name, country, sex, title, w_title, o_title, " +
                    "foa_title, rating, games, k, rapid_rating, rapid_games, rapidk, blitz_rating, " +
                    "blitz_games, blitzk, birthday, flag) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static long findOrCreate(String name) throws SQLException {
        String sql = "SELECT id FROM " + CommonDao.COMMON_SCHEMA + ".common_player WHERE name ILIKE ?";

        try (Connection connection = CommonDao.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    log.info("Found common player with name {} id {}", name, id);
                    return id;
                }
            }
        }

        // If not found, create a new one
        long id = countCommonPlayers() + 1;
        CommonPlayer commonPlayer = new CommonPlayer();
        commonPlayer.setName(name);
        commonPlayer.setId(id);
        log.info("Insert player with name {} id {}", name, id);
        insertCommonPlayer(commonPlayer);
        return id;
    }


    public static long countCommonPlayers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + CommonDao.COMMON_SCHEMA + ".common_player";

        try (Connection connection = CommonDao.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("Failed to retrieve player count.");
            }
        }
    }

    public static boolean isWellKnowPlayer(String name) {
        log.warn("NOT IMPLEMENTED YET: isWellKnowPlayer");
        return true; // TODO
    }

    public static void insertCommonPlayer(CommonPlayer player) throws SQLException {

        // TODO faire un upsert

//System.out.println("Inserting common player"+player);
// TODO a faire partout
        try (Connection connection = CommonDao.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PlayerDao.INSERT_SQL)) {
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
            log.error(e.getMessage(), e);
            throw new SQLException("Error while inserting player into chessvger.common_player table.", e);
        }

    }
}
