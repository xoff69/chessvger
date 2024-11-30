package com.xoff.chessvger.queues.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class CommonDao {
  private static CommonDao _instance = null;
  private Connection connection;

  private CommonDao() {
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://db_chessvger/chessvger", "chessvger",
          "chessvger");
          System.out.prinln("connexion ok");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("connexion DB "+e.getProducer+":"+e);
    }
  }

  public static CommonDao getInstance() {
    if (_instance == null) {
      _instance = new CommonDao();
    }
    return _instance;
  }

  public Connection getConnection() {
    return connection;
  }
}
