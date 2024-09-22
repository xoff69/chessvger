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
public class FilterGeneralTest {

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
  @DisplayName("testEco")
  public void testEco() {


    Filter f = new Filter("testEco", 1);
    f.setEco1("A00"); // inferieur
    f.setSelectedModeEco(Constants.EGAL_SEL);
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES, l1.size());
    f.setEco1("B00"); // <
    f.setEco2("C00");
    f.setSelectedModeEco(Constants.BETWEEN_SEL); // entre
    List<CommonGame> l2 = databaseManager.search(f);

    assertEquals(0, l2.size());
  }


  @Test
  @DisplayName("testResult")
  public void testResult() {

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
    Filter f = new Filter("testResult", 2);
    f.setResult(100);
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(l1.size(), ConstantsTest.NB_GAMES - DELTA + 1);
    // les resultats peuvent etre combines pour le filtre
    // avec 101 on cherche les 0-1 et les 1-0
    f.setResult(101);
    List<CommonGame> l2 = databaseManager.search(f);
    assertEquals(l2.size(), ConstantsTest.NB_GAMES);
  }


  @Test
  @DisplayName("testSite")
  public void testSite() {
    // prepa

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setSite("new york");
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testSite", 2);
    f.setSite("new york");
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(l1.size(), ConstantsTest.NB_GAMES - DELTA + 1);
  }

  @Test
  @DisplayName("testEvent")
  public void testEvent() {

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setEvent("mon anniv");
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testEvent", 1);
    f.setEvent("mon anniv");
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(l1.size(), ConstantsTest.NB_GAMES - DELTA + 1);
  }

  @Test
  @DisplayName("testRound")
  public void testRound() {

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setRound("rond");
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testRound", 3);
    f.setRound("rond");
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(l1.size(), ConstantsTest.NB_GAMES - DELTA + 1);
  }

  @Test
  @DisplayName("testUpdateSince")
  public void testUpdateSince() {

    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setLastUpdate(System.currentTimeMillis() - 24 * 3600 * 1000 * 4);

      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testUpdateSince", 3);
    f.setNbJourSinceUpdate(5);
    f.setSelectedModeDate(Constants.SUP_SEL);
    List<CommonGame> l1 = databaseManager.search(f);
    //FIXME   assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());
  }


  @Test
  @DisplayName("testNbCoup")
  public void testNbCoup() {

// 61 65 78 dans le jeu de test
    Filter f = new Filter("testNbCoup", 4);
    f.setNbcoup1(79);
    f.setNbcoup2(83);
    f.setSelectedModeCoups(Constants.BETWEEN_SEL);
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(0, l1.size());


    f.setNbcoup1(140);
    f.setNbcoup2(0);
    f.setSelectedModeCoups(Constants.INF_SEL);
    List<CommonGame> l2 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES, l2.size());

  }


}
