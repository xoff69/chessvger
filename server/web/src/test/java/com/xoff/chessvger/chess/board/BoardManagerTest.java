package com.xoff.chessvger.chess.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.chess.move.ResultInterpretation;
import com.xoff.chessvger.exception.FindPieceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardManagerTest {


  @Test
  @DisplayName("findPiece")
  void testFindPiece() throws FindPieceException {

    Position position = new Position();
    for (int i = 0; i < 5; i++) {
      position.setAt(i, 1, MetierConstants.CASE_VIDE);
    }

    CoupleResultat cr = BoardManager.findPiece(position, 'R', new Case(0, 0), new Case(0, 2));
    assertTrue(cr.isResultatCoherent());

    cr = BoardManager.findPiece(position, 'N', new Case(1, 0), new Case(2, 2));
    assertTrue(cr.isResultatCoherent());

    cr = BoardManager.findPiece(position, 'B', new Case(-1, -1), new Case(3, 1));
    assertTrue(cr.isResultatCoherent());

    cr = BoardManager.findPiece(position, 'Q', new Case(3, 0), new Case(3, 1));
    assertTrue(cr.isResultatCoherent());

    cr = BoardManager.findPiece(position, 'K', new Case(4, 0), new Case(4, 1));
    assertTrue(cr.isResultatCoherent());

    cr = BoardManager.findPiece(position, ' ', new Case(-1, -1), new Case(6, 2));
    assertTrue(cr.isResultatCoherent());

    assertThrows(FindPieceException.class, () -> {

      BoardManager.findPiece(position, 'R', new Case(-1, -1), new Case(2, 2));
    });
  }

  @Test
  @DisplayName("playBasic 1. e4")
  void play1() {
    ResultInterpretation resultInterpretation1 = BoardManager.play(new Position(), "e4");
    assertEquals(resultInterpretation1.getPieceSource(), EnumPiece.PION);
  }

  @Test
  @DisplayName("play invalide mov")
  void playInvalideMove() {
    ResultInterpretation resultInterpretation2 = BoardManager.play(new Position(), "Cc3");
    assertTrue(resultInterpretation2.isInvalide());
  }

  @Test
  @DisplayName("play long castle ")
  void playLongCastle() {
    Position position = new Position();
    for (int i = 1; i < 3; i++) {
      position.setAt(i, 0, MetierConstants.CASE_VIDE);
    }
    ResultInterpretation resultInterpretation2 = BoardManager.play(new Position(), "0-0-0");
    assertFalse(resultInterpretation2.isInvalide());
  }

  @Test
  @DisplayName("play short castle ")
  void playShortCastle() {
    Position position = new Position();
    for (int i = 5; i < 7; i++) {
      position.setAt(i, 0, MetierConstants.CASE_VIDE);
    }
    ResultInterpretation resultInterpretation2 = BoardManager.play(new Position(), "0-0");
    assertFalse(resultInterpretation2.isInvalide());
  }

  @Test
  @DisplayName("play pawn move ")
  void playPawn() {
    // prise pion

    // prise piece

    // prise ep
  }


}