package com.xoff.chessvger.chess.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

  private static final String FEN = "5rk1/p4ppp/1p6/8/4N3/P2R1QP1/1b2PP1P/1q4K1 w - - 4 28";

  @Test
  @DisplayName("fen2PositionAndToFen")
  void fen2PositionAndToFen() {
    Position p = Position.fen2Position(FEN);
    assertEquals(p.tofen(), FEN);
  }

  @Test
  @DisplayName("testDuplicate")
  void testDuplicate() {
    Position p = Position.fen2Position(FEN);
    assertEquals(p.duplicate().tofen(), FEN);
  }

  @Test
  @DisplayName("testClear")
  void testClear() {
    Position p = Position.fen2Position(FEN);
    p.clear();
    assertEquals(2,
        p.getListePiece().getBlackPieces().size() + p.getListePiece().getWhitePieces().size());
  }

  @Test
  @DisplayName("getSpecificitesPosition")
  void getSpecificitesPosition() {
    Position p = Position.fen2Position(FEN);
    assertTrue(p.getSpecificitesPosition().toString().startsWith("ffff"));
  }

  @Test
  @DisplayName("evaluateMaterial")
  void evaluateMaterial() {
    Position p = Position.fen2Position(FEN);
    CoupleZobristMaterial coupleZobristMaterial = p.evaluateMaterial();
    assertEquals(35752356610335L, coupleZobristMaterial.getMaterial());
  }

  @Test
  @DisplayName("evaluateZobristAndMaterial")
  void evaluateZobristAndMaterial() {
    Position p = Position.fen2Position(FEN);
    CoupleZobristMaterial coupleZobristMaterial = p.evaluateZobristAndMaterial();
    assertEquals(1377188976L, coupleZobristMaterial.getZobrist());
    assertEquals(35752356610335L, coupleZobristMaterial.getMaterial());
  }
}