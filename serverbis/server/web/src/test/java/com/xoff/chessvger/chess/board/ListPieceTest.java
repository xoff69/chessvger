package com.xoff.chessvger.chess.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ListPieceTest {

  @Test
  void duplicate() {
    ListPiece lp = new ListPiece();
    lp.addWhite(new Piece());
    lp.addWhite(new Piece());
    ListPiece copie = lp.duplicate();
    assertEquals(lp.getWhitePieces().size(), copie.getWhitePieces().size());
    lp.clear();
    assertEquals(0, lp.getWhitePieces().size());
  }

  @Test
  void addRemoveBlanc() {
    ListPiece lp = new ListPiece();
    lp.addWhite(new Piece());
    lp.addWhite(new Piece());

    assertEquals(2, lp.getWhitePieces().size());
    Piece p = new Piece();
    p.setActualPosition(new Case(2, 2, MetierConstants.CAVALIER_BLANC));

    lp.addWhite(p);
    lp.removeWhitePiece(p);
    assertEquals(2, lp.getWhitePieces().size());
  }

  @Test
  void addRemoveNoir() {
    ListPiece lp = new ListPiece();
    lp.addBlack(new Piece());
    lp.addBlack(new Piece());

    assertEquals(2, lp.getBlackPieces().size());

    Piece p = new Piece();
    p.setActualPosition(new Case(2, 2, MetierConstants.CAVALIER_BLANC));

    lp.addBlack(p);
    lp.removeBlackPiece(p);
    assertEquals(2, lp.getBlackPieces().size());
  }

}