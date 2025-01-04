package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSessionKey {

  @Test
  @DisplayName("testSessionKey")
  public void testSessionKey() {
    SessionKeyGenerator.getInstance().reset();
    assertEquals(1, SessionKeyGenerator.getInstance().next());
    assertEquals(2, SessionKeyGenerator.getInstance().next());
  }
}
