package com.xoff.chessvger.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFaitsDeJeuUtil {
    @Test
    @DisplayName("testFaitsDeJeuUtil")
    public void testFaitsDeJeuUtil() {
        long value = 0L;

        value = value | FaitsDeJeuUtil.WHITE_CASTLE_LONG;
        assertTrue(FaitsDeJeuUtil.isMatching(value, FaitsDeJeuUtil.WHITE_CASTLE_LONG));
    }
}
