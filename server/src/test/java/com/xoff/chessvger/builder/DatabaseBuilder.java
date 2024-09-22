package com.xoff.chessvger.builder;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import java.util.Random;

public class DatabaseBuilder {
  private static final Random rand = new Random();

  public static Database buildDatabase(String nomTest) {

    Database database = new Database();
    database.setNbgames(0);
    database.setName(nomTest);
    database.setDescription(nomTest);
    database.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    database.setLastUpdate(System.currentTimeMillis());
    // FIXME on le fait deux fois ca ?
    GlobalManager.getInstance().getDatabaseMap().add(database.getId(), database);
    DatabaseManager databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);


    return database;

  }

  public static void feedDatabase(DatabaseManager databaseManager) {
    int nbg = ConstantsTest.NB_GAMES;
    List<CommonGame> lg = GameBuilder.generateGames(nbg);
    for (int i = 0; i < nbg; i++) {
      databaseManager.upsert(lg.get(i), DBOperation.AJOUT);
    }
    databaseManager.postUpdateGameAndStat();
  }

  public static Database buildEmptyDatabase() {
    Database database = new Database();
    database.setName("DB_test" + rand.nextInt());
    return database;

  }

}
