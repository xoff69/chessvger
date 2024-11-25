package com.xoff.chessvger.queues.reconciliation;

public enum ReconciliationType {
  POSITION(1),
  MATERIAL(2),
  STAT(3),
  GAMEOFPLAYER(4);

  private int value;
  private ReconciliationType(int value){
    this.value = value;
  }
}
