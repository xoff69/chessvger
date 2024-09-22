package com.xoff.chessvger.chess.player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.web.view.StatJoueurView;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class PlayerStatManagerTest {


  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("PlayerStatManagerTest");
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
  public void testStatJoueur() {

    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    // ce qui est appele dans le parseData
    //4100026        Karpov, Anatoly
    CommonPlayer commonPlayer = pm.findOrAdd("Karpov,A", 1233456456);


    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();

    CommonGame game = games.get(0);
    game.setWhiteFideId(commonPlayer.getIdnumber());
    databaseManager.upsert(game, DBOperation.UPDATE);

    assertNotNull(commonPlayer);
    StatJoueurView statJoueurView =
        databaseManager.getPlayerStatManager().getStatJoueur(databaseManager, commonPlayer);
    assertNotNull(statJoueurView);
  }
}