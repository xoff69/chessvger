package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.view.StatGame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class GameStatTest {
  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("GameStatTest");
    databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @Test
  public void testGameStat() {
    StatGame statGame =
        databaseManager.getGameStatManager().getStatGame(GameBuilder.generateGames(10));
    assertNotNull(statGame);
  }
}
