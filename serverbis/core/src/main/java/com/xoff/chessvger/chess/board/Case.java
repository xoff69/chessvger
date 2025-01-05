package com.xoff.chessvger.chess.board;

import lombok.ToString;

@ToString
public class Case {

  public int raw;
  public int column;
  public char pieceOnTheBoard;

  public Case(int column, int raw, char pieceOnTheBoard) {
    this.raw = raw;
    this.column = column;
    this.pieceOnTheBoard = pieceOnTheBoard;
  }

  public Case(int column, int raw) {
    this.raw = raw;
    this.column = column;
    this.pieceOnTheBoard = MetierConstants.CASE_VIDE;
  }

  public Case(Case other) {
    raw = other.raw;
    column = other.column;
    pieceOnTheBoard = other.pieceOnTheBoard;
  }


  public Case() {
    raw = -1;
    column = -1;
    pieceOnTheBoard = MetierConstants.CASE_VIDE;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Case other)) {
      return false;
    }
    return other.raw == raw && other.column == column;
  }

  @Override
  public int hashCode() {
    return raw * 100 + column;
  }

  public Case duplicate() {
    return new Case(this);
  }


}
