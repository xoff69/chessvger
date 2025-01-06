package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.chess.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PgnUtilTest {
  @Test
  void removeComment() {

    String chaineATester = "!#+tototo ##?";
    String chaineRes = "tototo ";
    assertEquals(chaineRes, PgnUtil.removeComment(chaineATester));
  }

  @Test
  @DisplayName("testPgn")
  void playerDenudeName() {
    String origine = "toto_AB_ o, ";

    assertEquals("toto_ab_o", PgnUtil.playerDenudeName(origine));
  }

  @Test
  void convert1MoveSan2Pgn() {
    Position p = new Position();

    String san = "e2e4";
    assertEquals("e4", PgnUtil.convert1MoveSan2Pgn(p, san));

  }

  @Test
  void convertSan2Pgn() {
        /*
        Position p=new Position();

        String san="1. e2e4 e7e5 ";
        assertEquals("e4", PgnUtil.convertSan2Pgn(p,san));
        @FIXME
         */
  }

  @Test
  void determineMove() {
    Position p = new Position();

    assertEquals("e4", PgnUtil.determineMove(p, 4, 1, 4, 3));
  }

  @Test
  void isInBorne() {

    assertTrue(PgnUtil.isInBorne(5, 5));
    assertTrue(PgnUtil.isInBorne(0, 7));
    assertFalse(PgnUtil.isInBorne(8, 5));
    assertFalse(PgnUtil.isInBorne(5, 8));
  }

  @Test
  void letter2int() {
    assertEquals(3, PgnUtil.letter2int('4'));
    assertEquals(2, PgnUtil.letter2int('c'));
  }

  @Test
  void int2letter() {

    assertEquals('a', PgnUtil.int2letter(1));
  }

  @Test
  void interpreteValue() {
    assertEquals(13, PgnUtil.interpreteValue("13"));
  }



  @Test
  void isScore() {
    assertTrue(PgnUtil.isScore("1-0"));
    assertFalse(PgnUtil.isScore("1-1"));
  }


  @Test
  void extraitListeMove() {

    String[] str = PgnUtil.extractMovesFromString("1. e4 e5 2. d4 d5");
    // FIXME seulement les 3 premiers ?
    assertEquals(3, str.length);
  }

  @Test
  void decodeURLDemiCoup() {

    assertEquals(4, PgnUtil.decodeURLDemiCoup("url=4"));
  }
}