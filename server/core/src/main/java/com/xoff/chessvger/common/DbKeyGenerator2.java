package com.xoff.chessvger.common;


import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;

@Slf4j
// semble moins performant car on fait une ecrtiure disque a chaque fois
public class DbKeyGenerator2 {
  private final DB db;
  private final Atomic.Integer atomicInteger;

  public DbKeyGenerator2() {

    db = DBMaker.fileDB(ParamConstants.DATA_FOLDER_COMMON + "KEYGEN" + Constants.KEY_SFX)
        .checksumHeaderBypass().fileLockDisable().closeOnJvmShutdown().fileMmapEnable().make();
    atomicInteger = db.atomicInteger("KEYGEN").createOrOpen();
  }

  public long getNext() {
    return atomicInteger.incrementAndGet();
  }

}
