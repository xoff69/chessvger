package com.xoff.chessvger.init;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.util.DateUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateDataBase {
  public static void main(String[] args) {
    // no link with the admin account, pack...
    String testCase="initial";
    PerfManager.start(DateUtils.toDateFile(System.currentTimeMillis())+" CreateDataBase  DB from "+args[0]+"  :"+testCase);
    DatabaseManager databaseManager = CreateDBTool.createDbCore(args[0], args[1], args[2], args[3]);
    log.info("CreateDataBase done : " + databaseManager.getDatabaseId());
    PerfManager.end("end ",databaseManager.count());
  }

  public static void main2(String[] args) {
    try {
      System.out.println("Hello world!");
      Class.forName("org.duckdb.DuckDBDriver");
      Connection conn = DriverManager.getConnection("jdbc:duckdb:./test_duckdb");
      Statement stmt = conn.createStatement();
      String schema="s1";
      String table="table21";
      String sql = "CREATE SCHEMA IF NOT EXISTS "+schema;
      stmt.executeUpdate(sql);

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+schema+"."+table+" (id INTEGER PRIMARY KEY, other_id INTEGER,at VARCHAR)");
      Random r=new Random();
      for (int i=0;i<17;i++) {
        String vvv="dddd"+i*r.nextInt();
        stmt.executeUpdate(" INSERT INTO " + schema  +"."+table+"   VALUES ("+i+","+i+",'"+vvv+"');");
      }
      try (ResultSet rs = stmt.executeQuery("SELECT * FROM "+schema+"."+table)) {
        while (rs.next()) {
          System.out.println(rs.getInt(1)+ "  "+ rs.getInt(2)+" "+rs.getString(3));
        }
      }
      stmt.close();


    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
