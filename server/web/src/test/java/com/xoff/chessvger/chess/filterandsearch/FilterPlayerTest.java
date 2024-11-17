package com.xoff.chessvger.chess.filterandsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.PlayerBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.Constants;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class FilterPlayerTest {


  private static final int WHITE_ELO = 2271;
  private static final int DELTA = 5;
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
  @DisplayName("testElo")
  public void testElo() {

    CommonPlayer player = PlayerBuilder.buildPlayer();
    player.setName("karpov");

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setBlackFideId(player.getIdnumber());
      g.setWhiteElo(WHITE_ELO);
      g.setResult(Constants.RESULT_0_1);
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }


    }
    Filter f = new Filter("testElo", 1);
    f.setWhiteElo(WHITE_ELO);

    List<CommonGame> l1 = databaseManager.search(f);


    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());
  }

  @Test
  @DisplayName("testNameBlack")
  public void testNameBlack() {
    // prepa
    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    CommonPlayer j = pm.findOrAdd("Karpov,A", 4100026);

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setBlackFideId(j.getIdnumber());
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testNameBlack", 1);
    f.setBlack("Karpov,A");
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());

  }

  @Test
  @DisplayName("testNameWhite")
  public void testNameWhite() {
    // prepa
    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    CommonPlayer j = pm.findOrAdd("Karpov,A", 4100026);

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setWhiteFideId(j.getIdnumber());
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testNameWhite", 1);
    f.setWhite("Karpov,A");
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(l1.size(), ConstantsTest.NB_GAMES - DELTA + 1);
  }

  @Test
  @DisplayName("testFideTitle")
  public void testFideTitle() {
    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setWhiteTitle("IM");
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testFideTitle", 5);
    f.setWhiteTitle("IM");
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());

  }

  @Test
  @DisplayName("testPlayerIndif")
  public void testPlayerIndif() {
    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    CommonPlayer j = pm.findOrAdd("Karpov,A", 4100026);

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setWhiteFideId(j.getIdnumber());
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testPlayerIndif", 1);
    f.setBlack("Karpov,A");
    f.setCampIndifferent(true);
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(l1.size(), ConstantsTest.NB_GAMES - DELTA + 1);
  }
}
