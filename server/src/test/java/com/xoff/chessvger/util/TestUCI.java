package com.xoff.chessvger.util;

import static com.xoff.chessvger.util.UCIUtil.convertNotationToSuccint;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.chess.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TestUCI {
  @Test
  @DisplayName("testUCI")
  public void testUCI() {
    Position p = new Position();
    // String moves = "e2e4 d7d6 d2d4 c5d4 f3d4 g8f6 b1c3 = en d6 d4 cxd4";
    String moves = "e2e4 d7d6 b1c3 g8f6 g1f3 e7e5 ";
    String res = convertNotationToSuccint(moves, p);
    System.out.println("res=" + res);
    assertEquals(res.trim(), "e4 d6 Nc3 Nf6 Nf3 e5");
  }
}
