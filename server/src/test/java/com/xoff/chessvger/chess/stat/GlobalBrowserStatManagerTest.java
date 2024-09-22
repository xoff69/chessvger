package com.xoff.chessvger.chess.stat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.QuickFilter;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.web.view.StatBrowserView;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class GlobalBrowserStatManagerTest {
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    Database database = DatabaseBuilder.buildDatabase("GlobalBrowserStatManagerTest");
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
  @DisplayName("testGlobalBrowserStatManagerTest")
  public void testGlobalBrowserStatManagerTest() {

    QuickFilter qf = new QuickFilter(new Position());

    List<StatBrowserView> l1 = databaseManager.getGlobalBrowserStatManager().getListSBForFilter(qf);
    assertEquals(1, l1.size());

    qf.setEloMin(1000);
    qf.setMemory(true);
    l1 = databaseManager.getGlobalBrowserStatManager().getListSBForFilter(qf);
    assertEquals(l1.size(), 1);
    qf.setMemory(false);
    l1 = databaseManager.getGlobalBrowserStatManager().getListSBForFilter(qf);
    assertEquals(l1.size(), 1);
    List<StatBrowserView> l2 = databaseManager.getGlobalBrowserStatManager()
        .buildTreeFromGame(GameBuilder.generateGames(5));
    assertEquals(l2.size(), 1);


  }

}