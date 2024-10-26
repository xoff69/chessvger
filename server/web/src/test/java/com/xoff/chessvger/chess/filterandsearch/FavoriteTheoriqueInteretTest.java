package com.xoff.chessvger.chess.filterandsearch;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("IT")
public class FavoriteTheoriqueInteretTest {
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
/*

@TODO
    @Test
    @DisplayName("testFavorite")
    public void testFavorite() {
        int index = 0;
        for (CommonGame g : dm.getGlobalGameManager().getAllGamesReadOnly()) {
            g.setDate("2000.12.01");
            //  g.setEventDate("2000.12.01");
            db.upsert(g, DBOperation.UPDATE);
            index++;
            if (index > ConstantsTest.NB_GAMES - DELTA) break;
        }

        Filter f = new Filter();
        f.setDate1("1999.01.01");
        f.setSelectedModeDate(Constants.SUP_SEL);
        List<CommonGame> l1 = db.search(f);
        assertEquals(ConstantsTest.NB_GAMES - DELTA + 1, l1.size());
    }



    private static void testInteretAndFavorite(DatabaseManager dm) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Filter f = new Filter();
        f.setInteret('3');
        f.setFavori(Constants.YES);
        runTest(f, dm, methodName);
    }


    private static void testInteret(DatabaseManager dm) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Filter f = new Filter();
        f.setInteret('3');
        runTest(f, dm, methodName);
    }


    private static void testFavorite(DatabaseManager dm) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Filter f = new Filter();
        f.setFavori(Constants.YES);
        runTest(f, dm, methodName);
    }

    private static void testTheorique(DatabaseManager dm) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Filter f = new Filter();
        f.setTheorique(Constants.YES);
        runTest(f, dm, methodName);
    }


    private static void testTheoriqueAndFavorite(DatabaseManager dm) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Filter f = new Filter();
        f.setTheorique(Constants.YES);
        f.setFavori(Constants.YES);
        runTest(f, dm, methodName);
    }*/
}
