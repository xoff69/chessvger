package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.stat.BrowserStatManager;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;


@Slf4j


public class GameManagerRun implements Runnable {


  private final String emplacement;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We want that")
  private final DatabaseManager databaseManager;
  private final String first;
  private final String dbName;


  public GameManagerRun(String dbName, String first, DatabaseManager databaseManager) {

    this.emplacement = databaseManager.getFolder(databaseManager.getDatabaseName());
    this.databaseManager = databaseManager;
    this.first = first;
    this.dbName = dbName;
  }

  @Override
  public void run() {
    try {
      //   log.info(">GameManagerRun.run CommonGameManager et BrowserStatManager//"+first);
      //    CommonGameManager gameM = new CommonGameManager(database, dbName + "_" + first);
      //  dbm.getglobalManager.ameManager().put(first, gameM);

      BrowserStatManager bsm = new BrowserStatManager(first, databaseManager, false);
      databaseManager.getGlobalBrowserStatManager().put(first, bsm);

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
