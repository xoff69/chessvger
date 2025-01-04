package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.PgnBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
@Slf4j
public class TestZip {
  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("TestZip");
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
  }

  @Test
  @DisplayName("testZip")
  public void testZip() {
    PgnBuilder.writeToFile();
    String filePath = PgnBuilder.FILEPGN;
    String zipPath = ParamConstants.WORK_DIR + "testPgn.zip";


    try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipPath))) {
      File fileToZip = new File(filePath);
      zipOut.putNextEntry(new ZipEntry(fileToZip.getName()));
      Files.copy(fileToZip.toPath(), zipOut);


    } catch (Exception fne) {
      log.error(fne.getMessage(), fne);
    }

    long nb = 0;
    try {
      nb = ZipUtil.traiteZipFile(zipPath, databaseManager, new Parser());

    } catch (Exception fne) {
      log.error(fne.getMessage(), fne);
    }
    assertEquals(18, databaseManager.getGlobalGameManager().size());
    assertEquals(18, nb);
  }
}
