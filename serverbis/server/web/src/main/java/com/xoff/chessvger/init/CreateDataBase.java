package com.xoff.chessvger.init;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.util.DateUtils;
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


}
