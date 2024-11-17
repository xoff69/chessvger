package com.xoff.chessvger.chess.filter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.board.Position;
import org.junit.jupiter.api.Test;

class QuickFilterTest {

  @Test
  void accept() {
    QuickFilter qc = new QuickFilter(new Position());
    assertTrue(qc.acceptGame(GameBuilder.buildGame()));

  }
}