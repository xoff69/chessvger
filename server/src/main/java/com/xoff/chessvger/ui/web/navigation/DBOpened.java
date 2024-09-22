package com.xoff.chessvger.ui.web.navigation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import lombok.Data;

@Data
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We want that")

public class DBOpened {
  private boolean isModified;
  private long bdId;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private HashMap<Long, GameOpened> gameOpeneds;

  public DBOpened() {
    gameOpeneds = new HashMap();
  }
}
