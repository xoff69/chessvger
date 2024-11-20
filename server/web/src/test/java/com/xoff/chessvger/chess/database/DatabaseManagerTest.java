package com.xoff.chessvger.chess.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.JoueurView;
import com.xoff.chessvger.view.StatBrowserView;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class DatabaseManagerTest {


  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("DataBaseManagerTest");
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
  @DisplayName("testAddDelete")
  public void testAddDelete() {

    assertEquals(ConstantsTest.NB_GAMES, databaseManager.getGlobalGameManager().size());
    CommonGame c = GameBuilder.buildGame();
    int avant = databaseManager.getGlobalGameManager().size();
    System.out.println("a " + databaseManager.getGlobalGameManager().size());
    databaseManager.upsert(c, DBOperation.AJOUT);
    System.out.println("ba " + databaseManager.getGlobalGameManager().size());
    databaseManager.upsert(c, DBOperation.DELETE);
    assertEquals(ConstantsTest.NB_GAMES, databaseManager.getGlobalGameManager().size());

  }

  @Test
  @DisplayName("testAddUpdate")
  public void testAddUpdate() {

    assertEquals(ConstantsTest.NB_GAMES, databaseManager.getGlobalGameManager().size());
    CommonGame c = GameBuilder.buildGame();
    int avant = databaseManager.getGlobalGameManager().size();
    c = databaseManager.upsert(c, DBOperation.AJOUT);
    System.out.println("vany " + c);
    c = databaseManager.upsert(c, DBOperation.UPDATE);
    assertEquals(ConstantsTest.NB_GAMES + 1, databaseManager.getGlobalGameManager().size());
  }

  @Test
  @DisplayName("testOptimizer")
  public void testOptimizer() {

    assertEquals(databaseManager.getGlobalGameManager().size(), ConstantsTest.NB_GAMES);

    databaseManager.optimiser(true, true);
    assertEquals(3, databaseManager.getGlobalGameManager().size());

  }

  @Test
  public void testDB2() {
    List<JoueurView> lj = databaseManager.getPlayersWithGames("",new Pageable(2,2));
    assertEquals(lj.size(), 0);
  }

  @Test
  @DisplayName("DatabaseManager.testBrowseData")
  public void testBrowseData() {
    List<String> ldeja = new ArrayList<>();
    ldeja.add("e4");

    List<StatBrowserView> lj = databaseManager.getBrowseData(ldeja);
    assertEquals(2, lj.size());
  }

  @Test
  public void testexportePGN() {
    assertNotNull(databaseManager.exportePgn());
  }


  @Test
  public void testPostUpdateGlobal() {
    long now = System.currentTimeMillis() - 1000;
    databaseManager.postUpdateGameAndStat();
    assertTrue(database.getLastUpdate() > now);

  }

  @Test
  public void testPostUpdate2() {
    long now = System.currentTimeMillis() - 1000;
    databaseManager.postUpdateGameAndStat(GameBuilder.buildGame());
    assertTrue(database.getLastUpdate() > now);
  }


  @Test
  @DisplayName("testImport")
  public void testImport() {
        /*
        databaseManager.getGlobalGameManager().clear();
        PgnBuilder.writeToFile();
        System.out.println("ficier pgn " + ConstantsTest.DATA_PGN);
        long l = db.importePgn(ConstantsTest.DATA_PGN_FILE);


        assertEquals(dm.getGlobalGameManager().size(), 18);
*/
    // TODO

  }
}