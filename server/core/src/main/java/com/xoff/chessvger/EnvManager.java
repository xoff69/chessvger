package com.xoff.chessvger;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvManager {
  public static final String RUN_FOLDER_PARAM = "RUN_FOLDER";
  private static final String RUN_FOLDER_DEFAULT = "../run";

  @SuppressFBWarnings(value = {"MS_EXPOSE_REP"}, justification = "We want that")
  private static EnvManager _instance;
  private final HashMap<String, String> map;

  private EnvManager() {
    map = new HashMap();
    addValue(RUN_FOLDER_PARAM, RUN_FOLDER_DEFAULT);

  }

  public static EnvManager getInstance() {
    if (_instance == null) {
      _instance = new EnvManager();
    }
    return _instance;
  }

  public void addValue(String key, String value) {
    log.info("addDatabaseManager value " + key + "-" + value);
    map.put(key, value);
  }

  public String getValue(String key) {
    return map.get(key);

  }
}
