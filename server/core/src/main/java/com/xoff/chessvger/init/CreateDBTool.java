package com.xoff.chessvger.init;

import com.xoff.chessvger.EnvManager;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateDBTool {
  public static DatabaseManager createDbCore(String pgnDir, String name, String description,String runFolder) {
    // creer bd test
    log.info("createDbCore data PGM " + pgnDir);

    EnvManager.getInstance().addValue(EnvManager.RUN_FOLDER_PARAM, runFolder);
    Database database = new Database();
    database.setNbgames(0);
    database.setName(name);
    database.setDescription(description);
    database.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    database.setLastUpdate(System.currentTimeMillis());

    GlobalManager.getInstance().getDatabaseMap().add(database.getId(), database);

    DatabaseManager databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);

    log.info("createDbCore-a import PGN " + pgnDir);
    int l = databaseManager.importePgn(pgnDir);
    log.info("createDbCore-b  import PGN" + l);
    database.setNbgames(l);
    databaseManager.postUpdateGameAndStat();
    log.info("postUpdateGameAndStat PGN" + l);
    databaseManager.finish();
    log.info(" createDbCore-a  fini ");
    return databaseManager;
  }


}
