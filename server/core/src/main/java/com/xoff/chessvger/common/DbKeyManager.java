package com.xoff.chessvger.common;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;


public class DbKeyManager {
  private static DbKeyManager _instance;
  @Getter
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private final DbKeyGenerator dbKeyGenerator;

  private DbKeyManager() {
    dbKeyGenerator = new DbKeyGenerator();
  }

  public static DbKeyManager getInstance() {
    if (_instance == null) {
      _instance = new DbKeyManager();
    }
    return _instance;
  }
}
