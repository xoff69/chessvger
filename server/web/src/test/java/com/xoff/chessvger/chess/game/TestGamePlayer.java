package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.PlayerBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestGamePlayer {

  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("TestGamePlayer");
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
  public void testGameOfPlayer() {


    CommonPlayer player = PlayerBuilder.buildPlayer();
    player.setName("karpov");
    int index = 0;

    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame g : games) {
      g.setBlackFideId(player.getIdnumber());
      databaseManager.upsert(g, DBOperation.UPDATE);
      index++;
      if (index > ConstantsTest.NB_GAMES - 5) {
        break;
      }
    }
    int compte = databaseManager.getGameOfAPlayerManager().countGameOfAPlayer(player.getIdnumber());
    assertEquals(compte, ConstantsTest.NB_GAMES - 4);


  }
}
