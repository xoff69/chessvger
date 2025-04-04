package com.xoff.chessvger.chess.player;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("IT")
public class TestSynonym {

    @Test
    @DisplayName("testSynonym")
    public void testSynonym() {
        System.out.println("testSynonym");
        SynonymPlayerManager spm = new SynonymPlayerManager();

        assertEquals(spm.getBestName("toto"), "toto");
        spm.finish();
    }
}
