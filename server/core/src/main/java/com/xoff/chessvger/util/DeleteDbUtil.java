package com.xoff.chessvger.util;

import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;


@Slf4j
// FIXME
public class DeleteDbUtil {

  private final List<String> laeffacer;


  public DeleteDbUtil() {
    laeffacer = new ArrayList<>();

  }

  public void execute() {

    manageDelete();

    nettoyageBds();
  }

  /**
   * ajoute la bd a effacer à la liste, pour gerer tout ça a la fermeture
   * TODO
   */
  public void addToDelete(long bd) {
    //  laeffacer.addDatabaseManager(bd);
    //  printKNoowBD();
    //       List<String> l = StringUtility.stringToList(BDS);
    //   l.remove(bd);
    //    log.info("addToDelete :"+bd);
    //   printKNoowBD();
    // FIXME propertiesUtil.setProperty(Constants.LISTBD, StringUtility.listToString(l));

  }


  public void manageDelete() {
    //   log.info("manageDelete");
    try {
      File ftodel =
          new File(FilenameUtils.getName(ParamConstants.DATA_FOLDER_DB + Constants.TODEL));

      OutputStreamWriter writer =
          new OutputStreamWriter(new FileOutputStream(ftodel), StandardCharsets.UTF_8);
      String newline = System.getProperty("line.separator");
      for (String bd : laeffacer) {
        writer.write(bd);
        writer.write(newline);

      }
      writer.close();
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }

  }


  private void nettoyageBds() {
    log.info(">BeforeRun");
    try {
      File file = new File(FilenameUtils.getName(ParamConstants.DATA_FOLDER_DB + Constants.TODEL));
      Scanner sc = new Scanner(file, StandardCharsets.UTF_8);
      while (sc.hasNextLine()) {
        String to = sc.nextLine();
        log.info("to delete:" + to);
        CVFileUtils.deleteFilesForPathByPrefix(ParamConstants.DATA_FOLDER_DB, to);
      }
      sc.close();
      if (!file.delete()) {
        log.error("error delete " + file.getCanonicalPath());
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    //      LOGGER.info("<Before Run");
  }
}
