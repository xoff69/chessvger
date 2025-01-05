package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("IT")
@ExtendWith(MockitoExtension.class)
class MapGameTest {
  private static Database database;
  private static DatabaseManager databaseManager;

  @Mock
  MapGame mapGame;

  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("MapGameTest");
    databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @Test
  void add() {

    CommonGame commonGame = GameBuilder.buildGame("1. e4 c5 2. Nf3");
    ICommonGameManager commonGameManager = databaseManager.getGlobalGameManager().get("e4");
    long nbgamesMapGame = commonGameManager.nbgames();
    long nbgames = commonGameManager.getGames().size();

    commonGameManager.upsert(commonGame, DBOperation.AJOUT);
    assertEquals(nbgames + 1, commonGameManager.nbgames());
    assertEquals(nbgamesMapGame + 1, commonGameManager.nbgames());
    assertNotNull(databaseManager.getGameWhereMapManager().get(commonGame.getId()));

  }

  @Test
  void getGameByStart() {
  }

  @Test
  void listGameOfAPlayerForEco() {
  }

  @Test
  void search() {
  }

  @Test
  void update() {
  }
}