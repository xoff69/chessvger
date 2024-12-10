package com.xoff.chessvger.parser.util;

import com.xoff.chessvger.parser.Main;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

public class CommonDao {
  private static BasicDataSource ds = new BasicDataSource();


  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("connexion DB :" + e);
    }
    ds.setUrl("jdbc:postgresql://" + Main.getDBHost() + "/chessvger");
    ds.setUsername("chessvger");
    ds.setPassword("chessvger");
    ds.setMinIdle(5);
    ds.setMaxIdle(10);
    ds.setMaxOpenPreparedStatements(100);
  }

  private CommonDao() {
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}
