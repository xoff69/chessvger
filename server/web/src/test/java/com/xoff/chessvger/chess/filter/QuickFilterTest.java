package com.xoff.chessvger.chess.filter;

import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickFilterTest {

    @Test
    void accept() {
        QuickFilter qc = new QuickFilter(new Position());
        assertTrue(qc.acceptGame(GameBuilder.buildGame()));

    }
}