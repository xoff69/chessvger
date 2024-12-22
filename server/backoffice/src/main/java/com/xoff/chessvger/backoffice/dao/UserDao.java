package com.xoff.chessvger.backoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDao {
  public static final String TABLE_USER =
      "CREATE TABLE "+ CommonDao.ADMIN_SCHEMA+".users (\n" + "\n" +
          "                             id SERIAL PRIMARY KEY,\n" +
          "                             login VARCHAR(50) NOT NULL UNIQUE,\n" +
          "                             description TEXT,\n" +
          "                             password VARCHAR(255) NOT NULL,\n" + "\n" +
          "                             date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
          "                             date_update TIMESTAMP,\n" + "\n" +
          "                             profil BOOLEAN DEFAULT FALSE -- Profil: admin (TRUE) ou utilisateur normal (FALSE)\n" +
          "\n" + ");";
  private static final String INSERT_USER="INSERT INTO "+ CommonDao.ADMIN_SCHEMA+".users (login, description, password, profil)\n" +
  "VALUES     (?, ?, ?, ?);";

  public static void createUser(Connection connection,
                                String login, String description, String password, boolean profil) throws Exception {

    try (PreparedStatement stmt = connection.prepareStatement(INSERT_USER)) {
      stmt.setString(1, login);
      stmt.setString(2, description);
      stmt.setString(3, password);
      stmt.setBoolean(5, profil);
      stmt.executeUpdate();
      System.out.println("createUser: " + login);
    }
  }
}
