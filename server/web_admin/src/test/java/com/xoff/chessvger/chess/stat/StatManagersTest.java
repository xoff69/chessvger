package com.xoff.chessvger.chess.stat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.builder.PlayerBuilder;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.QuickFilter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.view.StatBrowserView;
import com.xoff.chessvger.view.StatJoueurView;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IT")
public class StatManagersTest {

  private static final String DBNAME = "StatManagersTest";
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    Database database = DatabaseBuilder.buildDatabase(DBNAME);
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
  @DisplayName("testStatManager")
  public void testStatManager() {

    IBrowserStatManager bsm = databaseManager.getGlobalBrowserStatManager().get("e4");
    bsm.browseFirstMove(GameBuilder.generateGames(10));

    assertEquals(bsm.getGames(new Position()).size(), 25);
    List<String> ls = new ArrayList<>();
    ls.add("e4");
    ls.add("c5");
    ls.add("Nf3");
    ls.add("d6");
    List<StatBrowserView> lsb = bsm.getBrowseData(databaseManager, ls);
    assertNotNull(lsb);

  }

  @Test
  @DisplayName("testPlayerStatManager")
  public void testPlayerStatManager() {
    StatJoueurView sj = databaseManager.getPlayerStatManager()
        .getStatJoueur(databaseManager, PlayerBuilder.buildPlayer());
    assertNotNull(sj);
  }

  @Test
  @DisplayName("testGlobalStat")
  public void testGlobalStat() {

    List<CommonGame> l =
        databaseManager.getGlobalBrowserStatManager().gameOfASB(new QuickFilter(new Position()));
    assertEquals(l.size(), ConstantsTest.NB_GAMES);

    List<StatBrowserView> ls = databaseManager.getGlobalBrowserStatManager()
        .getListSBForFilter(new QuickFilter(new Position()));
    assertEquals(ls.size(), 1);

    List<StatBrowserView> ls2 = databaseManager.getGlobalBrowserStatManager()
        .buildTreeFromGame(GameBuilder.generateGames(10));
    assertEquals(ls2.size(), 1);
  }
}
