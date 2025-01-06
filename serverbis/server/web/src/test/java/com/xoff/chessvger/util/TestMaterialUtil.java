package com.xoff.chessvger.util;

import static com.xoff.chessvger.util.MaterialUtil.DEBUT_PION_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMaterialUtil {

  @Test
  @DisplayName("testMaterial")
  public void testMaterial() {
    long materialValue = 0L;
    materialValue = encode(materialValue, 8, DEBUT_PION_BLANC);

    assertEquals(255, materialValue);
  }
}
