package com.xoff.chessvger.chess.opening;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("IT")
class OpeningFactoryTest {


    @Test
    void loadAll() {
        List<Opening> lop = OpeningFactory.loadAll();
        assertEquals(lop.size(), 2010);
    }
}