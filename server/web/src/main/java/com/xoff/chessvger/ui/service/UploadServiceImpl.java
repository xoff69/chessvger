package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.IDatabaseManager;
import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UploadServiceImpl implements IUploadService {


  private static void finishUpload() {
    log.info(" finishUpload ");
    try {
      FileUtils.cleanDirectory(new File(FilenameUtils.getName(ParamConstants.PATH_IMPORT)));
    } catch (IOException e) {
      log.error(e.getMessage());
    }

  }

  private static final File writeMultipartToDisk(MultipartFile multipartFile) {
    File fileo = new File(
        FilenameUtils.getName(ParamConstants.PATH_IMPORT + multipartFile.getOriginalFilename()));

    try (OutputStream os = new FileOutputStream(fileo)) {
      os.write(multipartFile.getBytes());
    } catch (FileNotFoundException e) {
      log.error(e.getMessage());
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return fileo;
  }


  public int uploadPlayer(MultipartFile file) {
    GlobalManager.getInstance().getCallStatManager().appendStat("PLAYER_IMPORT");
    try {

      log.info("upload uploadFidePlayer");
      File fileo = writeMultipartToDisk(file);
      ICommonPlayerManager commonPlayerManager =
          GlobalManager.getInstance().getCommonPlayerManager();
      FileUtils.cleanDirectory(new File(FilenameUtils.getName(ParamConstants.PLAYERS_FOLDER)));
      FileUtils.moveFile(fileo, new File(FilenameUtils.getName(ParamConstants.PLAYERS_PATH)));
      commonPlayerManager.clear();
      int l = commonPlayerManager.importeFidePlayer();
      FileUtils.cleanDirectory(new File(FilenameUtils.getName(ParamConstants.PLAYERS_FOLDER)));
      finishUpload();
      log.info("nombre de player ajoutes " + l);
      return l;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return 0;
  }

  public int uploadPgn(MultipartFile file, long bdId) {

    GlobalManager.getInstance().getCallStatManager().appendStat("PGN_IMPORT");
    //  TODO  File fileo = writeMultipartToDisk(multipartFile);
    Database database = new Database();
    IDatabaseManager dm = GlobalManager.getInstance().getDatabaseManager(bdId);

    log.info("import PGN avant:" + database.getNbgames());
    int l = dm.importePgn(ParamConstants.PATH_IMPORT);
    log.info("import PGN apres:" + l);

    dm.finish();
    finishUpload();
    return l;
  }

}
