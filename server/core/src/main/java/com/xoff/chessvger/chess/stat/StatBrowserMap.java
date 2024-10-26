package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.AdbCommonKeyString;
import com.xoff.chessvger.util.Constants;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatBrowserMap extends AdbCommonKeyString<StatBrowser> {

  public StatBrowserMap(String premier, String dbname, boolean inMemory) {
    super(
        DatabaseManager.getFolder(dbname) + dbname + "statBrowserMap" + premier + Constants.MAP_SFX,
        inMemory);
  }


  public Collection<StatBrowser> values() {
    return list();
  }

  public List<String> keys() {
    return super.keyset();
  }
}
