package com.xoff.chessvger.chess.board;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.PositionBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class PositionManagerTest {


  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("PositionManagerTest");
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
  @DisplayName("PositionManager.search")
  void search() {
    Filter f = new Filter("testSearchPosition", 2);
    CoupleZobristMaterial coupleZobristMaterial =
        PositionBuilder.buildPosition().evaluateZobristAndMaterial();
    f.setZobrist(coupleZobristMaterial.getZobrist());
    IPositionManager positionManager = databaseManager.getPositionManager();
    List<Long> gamesId = positionManager.search(f);
    Assertions.assertTrue(gamesId.size() > 0);
  }

}