package com.xoff.chessvger.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.chess.board.Position;
import org.junit.jupiter.api.Test;


public class TestFen {

  private final static String FEN_EXEMPLE = "1k6/3K1B2/8/2N5/8/8/8/8 b - - 0 9";

  @Test
  public void testFen() {

    Position p = Position.fen2Position(FEN_EXEMPLE);
    int b = 33;

    assertEquals(p.tofen().substring(0, b), FEN_EXEMPLE.substring(0, b));

  }
}
