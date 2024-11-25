package com.xoff.chessvger.queues.reconciliation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Reconciliation {
  // une structure qui contient les differents points d entree a checker pour un game
  private long gameId;
  private boolean positionDone;
  private boolean materialDone;
  private boolean gameOfAPlayerDone;
  private boolean statGameDone;
}
