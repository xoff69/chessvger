package com.xoff.chessvger.backoffice.util;

import com.xoff.chessvger.backoffice.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CommonDao {
  private static final HikariConfig config = new HikariConfig();
  private static final HikariDataSource ds;


  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("connexion DB :" + e);
    }
    // System.out.println("connexion DB :" + "jdbc:postgresql://" + Main.getDBHost() + "/chessvger");

    config.setJdbcUrl("jdbc:postgresql://" + Main.getDBHost() + "/chessvger");
    config.setUsername("chessvger");
    config.setPassword("chessvger");
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.setAutoCommit(true);
    ds = new HikariDataSource(config);
  }

  private CommonDao() {
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}
