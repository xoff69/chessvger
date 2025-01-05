package com.xoff.chessvger.chess.stat;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.builder.SerializationUtil;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatBrowserTest {
  private StatBrowser statBrowser;

  @BeforeEach
  public void setUp() {
    statBrowser = new StatBrowser();
    statBrowser.setBlanc(1);
    statBrowser.setLevel(11);
    statBrowser.setLastGameDate("xxxxxxx");
  }


  @Test
  void testSerialization() throws IOException, ClassNotFoundException {

    byte[] serialized = SerializationUtil.serialize(statBrowser);
    Object deserialized = SerializationUtil.deserialize(serialized);

    assertTrue(deserialized instanceof StatBrowser);
    assertEquals(statBrowser.getLevel(), ((StatBrowser) deserialized).getLevel());
  }

}