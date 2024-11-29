package com.xoff.chessvger.queues.reconciliation;

public enum ReconciliationType {
  GAME(0),POSITION(1), MATERIAL(2), STAT(3), GAMEOFPLAYER(4);

  private final int value;

  ReconciliationType(int value) {
    this.value = value;
  }
}
