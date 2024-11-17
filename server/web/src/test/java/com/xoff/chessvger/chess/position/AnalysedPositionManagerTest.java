package com.xoff.chessvger.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class AnalysedPositionManagerTest {

  private static AnalyzedPosition analyzedPosition;


  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("TestFaitDeJeu");
    databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);
    analyzedPosition = new AnalyzedPosition(10L, 1.0f, 10, "xxx");

  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @Test
  public void testAnalysedPositionManager() {

    GlobalManager.getInstance().getApPositionManager().add(analyzedPosition);

    AnalyzedPosition a =
        GlobalManager.getInstance().getApPositionManager().get(analyzedPosition.getZobrist());
    assertEquals(a.getZobrist(), analyzedPosition.getZobrist());
  }
}