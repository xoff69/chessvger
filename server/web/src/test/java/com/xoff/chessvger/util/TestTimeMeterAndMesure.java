package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTimeMeterAndMesure {

  @Test
  @DisplayName("testTimeMeter")
  public void testTimeMeter() {
    TimeMeter.getInstance().startMesure("a");
    TimeMeter.getInstance().stopMesure("a");

    assertNotNull(TimeMeter.getInstance());

  }
}
