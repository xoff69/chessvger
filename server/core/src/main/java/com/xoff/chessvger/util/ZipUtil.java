package com.xoff.chessvger.util;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class ZipUtil {


  private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    File destFile = new File(destinationDir, FilenameUtils.getName(zipEntry.getName()));

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
      throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
  }


  public static long traiteZipFile(String fileZip, DatabaseManager databaseManager, Parser parser)
      throws IOException {
    long tot = 0;
    File destDir = new File(ParamConstants.PATH_PGN);
    ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
    ZipEntry zipEntry = zis.getNextEntry();
    byte[] buffer = new byte[1024];


    while (zipEntry != null) {
      File newFile = newFile(destDir, zipEntry);
      // log.info("unzip:" + newFile.getName());
      try (FileOutputStream fos = new FileOutputStream(newFile)) {
        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
      }
      tot += parser.parseDir(destDir, databaseManager);
        /*
        if (!newFile.delete()) {
          log.info("deletion impossible " + destDir + ":" + zipEntry);
        }*/
      zipEntry = zis.getNextEntry();
    }


    zis.closeEntry();
    zis.close();

    return tot;
  }

}
