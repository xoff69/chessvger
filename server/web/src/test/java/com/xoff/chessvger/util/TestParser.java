package com.xoff.chessvger.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.PgnBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestParser {


  private static Database database;
  private static DatabaseManager databaseManager;

  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("TestFaitDeJeu");
    databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @Test
  @DisplayName("testParse")
  public void testParse() {

    PgnBuilder.writeToFile();
    File DIR = new File(ParamConstants.PATH_IMPORT);
    long tot = new Parser().parseDir(DIR, databaseManager);

    assertEquals(tot, 18);
  }


}
