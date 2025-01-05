package com.xoff.chessvger.chess.move;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResultInterpretationTest {

  @Test
  void set() {
    ResultInterpretation res = new ResultInterpretation();
    res.set(1L);
    assertEquals(res.getFaitsdejeu(), 1L);
  }
}