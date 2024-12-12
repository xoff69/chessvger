package com.xoff.chessvger.common;


import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

@Slf4j
public class DbKeyGenerator extends AdbCommon<Long, Long> {

  private static final Long KEY = 1L;

  public DbKeyGenerator() {
    // ca reste pas ouf de mettre une map pour une seule valeur
    super(ParamConstants.DATA_FOLDER_COMMON + "KEYGEN" + Constants.KEY_SFX);

    if (get(KEY) == null) {
      add(KEY, 0L);
    }
  }

  public HTreeMap<Long, Long> makeMap() {
    HTreeMap<Long, Long> map = db.hashMap(getFilename() + "_map").keySerializer(Serializer.LONG)
        .valueSerializer(Serializer.LONG).createOrOpen();
    return map;
  }

  public long getNext() {
    long val = get(KEY);
    val++;
    add(KEY, val);
    return val;
  }


}
