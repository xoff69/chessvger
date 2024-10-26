package com.xoff.chessvger.common;


import java.io.Serializable;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;


public class AdbCommonKeyLong<T extends Serializable> extends AdbCommon<Long, T> {


  public AdbCommonKeyLong(String filename) {
    super(filename);

  }

  public AdbCommonKeyLong(String filename, boolean inMemory) {
    super(filename, inMemory);

  }

  public HTreeMap<Long, T> makeMap() {
    HTreeMap<Long, T> map = db.hashMap(getFilename() + "_map").keySerializer(Serializer.LONG)
        .valueSerializer(Serializer.JAVA).createOrOpen();
    return map;
  }

}
