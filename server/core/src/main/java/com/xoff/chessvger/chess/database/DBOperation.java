package com.xoff.chessvger.chess.database;


public enum DBOperation {
  AJOUT(1), DELETE(2), DUPLICATE(3), UPDATE(4), UNDELETE(5);

  private final int value;

  DBOperation(int v) {
    this.value = v;
  }

}
