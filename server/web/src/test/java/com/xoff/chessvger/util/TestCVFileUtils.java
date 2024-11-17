package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCVFileUtils {


  @Test
  @DisplayName("testCreateDeleteExistsDir")
  public void testCreateDeleteExistsDir() {
    String REP = "TestCVFileUtils" + File.separator;
    String F = "text.txt";
    Path path = Paths.get(REP + F);
    try {
      Files.createDirectories(path.getParent());
      Files.createFile(path);
    } catch (Exception e) {
      System.err.println("already exists: " + e.getMessage());
    }
    assertTrue(CVFileUtils.exists(F, REP));
    CVFileUtils.deleteDirectory(new File(REP));
    assertFalse(CVFileUtils.exists(F, REP));
  }


  @Test
  @DisplayName("testDeleteFilesForPathByPrefix")
  public void testDeleteFilesForPathByPrefix() {

    String REP = "TestCVFileUtils";
    String PREF = "prefixe";
    String NOPREF = "noprefixe";


    String F1 = File.separator + PREF + File.separator + "xxx_1.txt";
    String F2 = File.separator + PREF + File.separator + "xxx_2.txt";
    String F3 = File.separator + NOPREF + File.separator + "xxx_3.txt";

    try {
      Path path = Paths.get(REP + File.separator);
      Files.createDirectories(path);

      path = Paths.get(REP + File.separator + PREF);
      Files.createDirectories(path);

      path = Paths.get(REP + File.separator + NOPREF);
      Files.createDirectories(path);

      Path path1 = Paths.get(REP + F1);
      Files.createFile(path1);

      Path path2 = Paths.get(REP + F2);
      Files.createFile(path2);

      Path path3 = Paths.get(REP + F3);
      Files.createFile(path3);

    } catch (Exception e) {
      System.err.println("already exists: " + e.getMessage());
      e.printStackTrace();
    }

    CVFileUtils.deleteFilesForPathByPrefix(REP, PREF);
    assertTrue(CVFileUtils.exists(F3, REP));
    assertFalse(CVFileUtils.exists(F1, REP));
    CVFileUtils.deleteDirectory(new File(REP));
  }

}
