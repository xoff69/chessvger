package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class GameOfAPlayerManagerTest {

  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    Database database = DatabaseBuilder.buildDatabase("GameOfAPlayerManagerTest");
    databaseManager = new DatabaseManager(database);

    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @BeforeEach
  public void beforeEach() {
    databaseManager.clear();
    DatabaseBuilder.feedDatabase(databaseManager);
  }


  @Test
  void listGameOfAPlayer() {
    IGameOfAPlayerManager gameOfAPlayerManager = databaseManager.getGameOfAPlayerManager();
    assertNotNull(gameOfAPlayerManager);
    List<CommonGame> games =
        gameOfAPlayerManager.listGameOfAPlayer(databaseManager, GameBuilder.WHITE_FIDE_ID);
    assertNotNull(games);
    assertNotEquals(0, games.size());
  }


}