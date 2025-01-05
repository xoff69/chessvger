package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDao {

  private static final String INSERT_USER="INSERT INTO "+ CommonDao.COMMON_SCHEMA+".users (login, description, password, profil)\n" +
  "VALUES     (?, ?, ?, ?);";

  public static void createUser(Connection connection,
                                String login, String description, String password, boolean profil) throws Exception {

    try (PreparedStatement stmt = connection.prepareStatement(INSERT_USER)) {
      stmt.setString(1, login);
      stmt.setString(2, description);
      stmt.setString(3, password);
      stmt.setBoolean(4, profil);
      stmt.executeUpdate();
      System.out.println("createUser: " + login);
    }
    catch (Exception e){
      System.out.println("Error createUser: " + login+" "+e.getMessage());
    }
  }
}
