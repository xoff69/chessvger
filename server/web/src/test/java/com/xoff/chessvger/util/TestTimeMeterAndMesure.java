package com.xoff.chessvger.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTimeMeterAndMesure {

    @Test
    @DisplayName("testTimeMeter")
    public void testTimeMeter() {
        TimeMeter.getInstance().startMesure("a");
        TimeMeter.getInstance().stopMesure("a");

        assertNotNull(TimeMeter.getInstance());

    }
}
