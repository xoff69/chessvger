package com.xoff.chessvger.ui.web.navigation;

import lombok.Data;

@Data
public class GameOpened {
  private boolean isModified;
  private long gameId;
}
