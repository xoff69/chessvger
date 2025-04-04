package com.xoff.chessvger.chess.move;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultInterpretationTest {

    @Test
    void set() {
        ResultInterpretation res = new ResultInterpretation();
        res.set(1L);
        assertEquals(res.getFaitsdejeu(), 1L);
    }
}