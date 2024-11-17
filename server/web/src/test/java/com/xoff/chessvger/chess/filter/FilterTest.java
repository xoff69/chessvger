package com.xoff.chessvger.chess.filter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.builder.SerializationUtil;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterTest {

  private Filter filter;

  @BeforeEach
  public void setUp() {

    filter = new Filter("test", 1);
    filter.setMaterialPrecis(true);
    filter.setBlack("noir");
    filter.setWhite("blanc");
  }


  @Test
  void testSerialization() throws IOException, ClassNotFoundException {

    byte[] serialized = SerializationUtil.serialize(filter);
    Object deserialized = SerializationUtil.deserialize(serialized);

    assertTrue(deserialized instanceof Filter);
    assertEquals(filter.toString(), deserialized.toString());
  }
}