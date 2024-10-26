package com.xoff.chessvger.chess.position;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.builder.SerializationUtil;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzedPositionTest {
  private AnalyzedPosition analyzedPosition;

  @BeforeEach
  public void setUp() {
    analyzedPosition = new AnalyzedPosition(10L, 1.0f, 10, "xxx");

  }


  @Test
  void testSerialization() throws IOException, ClassNotFoundException {

    byte[] serialized = SerializationUtil.serialize(analyzedPosition);
    Object deserialized = SerializationUtil.deserialize(serialized);

    assertTrue(deserialized instanceof AnalyzedPosition);
    assertEquals(analyzedPosition.toString(), deserialized.toString());
  }
}