package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("ut")
public class MaterialUtilTest {

  @Test
  @DisplayName("encode")
  void testpEncore() {


    assertNotEquals(10L, MaterialUtil.encode(1L, 5, 5));
  }
}
