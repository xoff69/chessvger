package com.xoff.chessvger.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class CVFileUtils {


  public static boolean deleteFilesForPathByPrefix(final String path, final String prefix) {
    boolean success = true;
    System.out.println("deleteFilesForPathByPrefix " + path + " " + prefix);
    // log.info("effacement du contenu de " + path + File.separator + prefix);
    try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(
        Paths.get(makeFolder(path + File.separator + prefix + File.separator)), "*.*")) {
      for (final Path newDirectoryStreamItem : newDirectoryStream) {
        Files.delete(newDirectoryStreamItem);
      }
      //      log.info("suppresson repertoire");
      File f = new File(makeFolder(path + File.separator + prefix));
      if (!f.delete()) {
        log.error("delete " + path + ":" + prefix + " error");
      }

    } catch (final Exception e) {
      success = false;
      log.error(e.getMessage());
    }

    return success;
  }

  public static String makeFolder(String folder) {
    return FilenameUtils.getFullPath(folder) + FilenameUtils.getName(folder);
  }

  public static boolean exists(String name, String folder) {
    try {
      File f = new File(makeFolder(folder + name));
      return f.exists();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }


  public static boolean deleteDirectory(File directoryToBeDeleted) {
    log.info("deleteEnvTest=" + directoryToBeDeleted.getName());
    File[] allContents = directoryToBeDeleted.listFiles();
    if (allContents != null) {
      for (File file : allContents) {

        boolean res = deleteDirectory(file);
        log.info("deleteEnvTest=" + file.getName() + " = " + res);
      }
    }
    try {
      Files.delete(Paths.get(makeFolder(directoryToBeDeleted.getAbsolutePath())));
      return true;
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    return false;
  }

  public static void createDir(String nom) {
    log.info(">>createDir " + nom);
    try {
      File f = new File(makeFolder(nom));
      if (!f.exists()) {
        if (!f.mkdir()) {
          log.info("createDir ko " + nom);
        } else {
          log.info("createDir ok " + nom);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.info("<<createDir " + nom);
  }


}
