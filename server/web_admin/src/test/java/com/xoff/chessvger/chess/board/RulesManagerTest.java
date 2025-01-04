package com.xoff.chessvger.chess.board;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RulesManagerTest {

  @Test
  void isCheck() {

    Position p = Position.fen2Position("5rk1/p4ppp/1p6/8/4N3/P2R1QP1/1b2PP1P/1q4K1 w - - 4 28");

    assertTrue(RulesManager.isCheck(p, MetierConstants.BLANC));
    assertFalse(RulesManager.isCheck(p, MetierConstants.NOIR));
  }
}