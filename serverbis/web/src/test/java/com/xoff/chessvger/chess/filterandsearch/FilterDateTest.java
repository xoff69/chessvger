package com.xoff.chessvger.chess.filterandsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
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
public class FilterDateTest {

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
  @DisplayName("testDate")
  public void testDate() {
// les dates sont en 1998 dans le  jeu de test
    int index = 0;
    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setDate("2000.12.01");

      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testDate", 2);
    f.setDate1("1999.01.01");
    f.setSelectedModeDate(Constants.SUP_SEL);
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());


    f.setDate1("1999");
    f.setDate2("2020");

    f.setSelectedModeDate(Constants.BETWEEN_SEL);
    List<CommonGame> l2 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l2.size());


    f.setDate1("2020");
    f.setSelectedModeDate(Constants.INF_SEL);
    f.setIncludeDateNull(true);
    List<CommonGame> l3 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES, l3.size());
  }

  @Test
  @DisplayName("testEventDate")
  public void testEventDate() {
// les dates sont en 1998 dans le  jeu de test
    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setEventDate("2000.12.01");

      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - DELTA) {
        break;
      }
    }

    Filter f = new Filter("testEventDate", 1);
    f.setDate1("1999.01.01");
    f.setSelectedModeDate(Constants.SUP_SEL);
    List<CommonGame> l1 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());


    f.setDate1("1999");
    f.setDate2("2020");

    f.setSelectedModeDate(Constants.BETWEEN_SEL);
    List<CommonGame> l2 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l2.size());


    f.setDate1("2020");
    f.setSelectedModeDate(Constants.INF_SEL);
    f.setIncludeDateNull(true);
    List<CommonGame> l3 = databaseManager.search(f);
    assertEquals(ConstantsTest.NB_GAMES, l3.size());
  }
}