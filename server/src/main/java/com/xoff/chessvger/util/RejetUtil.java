package com.xoff.chessvger.util;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class RejetUtil {


  private OutputStreamWriter writer;


  public RejetUtil() {
    try {
      String p = FilenameUtils.getFullPath(ParamConstants.REP_REJET) +
          FilenameUtils.getName(ParamConstants.REP_REJET);
      //   log.info("RejetUtil init :" + folder);
      File directoryRejet = new File(p);

      long l = System.currentTimeMillis();
      String path = CVFileUtils.makeFolder(
          ParamConstants.REP_REJET + "/" + DateUtils.toDateFile(l) + "_" +
              DbKeyManager.getInstance().getDbKeyGenerator().getNext() + ".pgn");

      log.info("Reject Path:  " + path);

      File f = new File(path);
      writer = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);

    } catch (IOException ioe) {
      log.error(ioe.getMessage());
    }
  }

  public void finish() {
    // ferme le fichier
    try {
      writer.close();
    } catch (IOException ioe) {
      log.error(ioe.getMessage());
    }
    //  log.info("Fin rejet util");
  }

  public void ecritRejet(CommonGame game, String cause) {

    //    log.error("REJET "+game);
    try {
      writer.write(cause);
      writer.write("\n");
      writer.write(game.toPGN());
    } catch (IOException ioe) {
      log.error(ioe.getMessage());
    }

  }
}
