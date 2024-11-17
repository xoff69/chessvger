package com.xoff.chessvger.chess.board;

/**
 * @description les pieces
 */
public enum EnumPiece {

  CASE_VIDE(0), PION(100), CAVALIER(200), FOU(200), TOUR(500), REINE(900), ROI(10000);

  private final int value;

  EnumPiece(int v) {
    value = v;
  }

  public int getValue() {
    return value;
  }

}
