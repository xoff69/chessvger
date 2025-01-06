package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestZobrist {
  @Test
  @DisplayName("testZobrist")
  public void testZobrist() {
    assertEquals(512549802, Zobrist.getTAlea()[0][0][9]);

  }
}
