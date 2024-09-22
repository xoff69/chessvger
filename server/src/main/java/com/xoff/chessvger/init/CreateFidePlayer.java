package com.xoff.chessvger.init;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateFidePlayer {
  public static void main(String[] args) {
    String testCase="initial";
    // 2 params l endroit du fichier en 0 et le run folder en 1
    PerfManager.start(
        DateUtils.toDateFile(System.currentTimeMillis())+" Create Fide player  DB from "+args[0]+"  :"+testCase);
    long c=GlobalManager.getInstance().getCommonPlayerManager().importeFidePlayer(args[0]);
    log.info("Create Fide player done : " +c);
    PerfManager.end("end ",c);
  }
}
