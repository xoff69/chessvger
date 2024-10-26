package com.xoff.chessvger.chess.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
@Slf4j
public class TestSerializationPlayer {


  @Test
  @DisplayName("testCommonPlayerManager")
  public void testCommonPlayerManager() {
    try {

      CommonPlayerManager cm = new CommonPlayerManager();
      cm.clear();

      CommonPlayer p1 = cm.findOrAdd("toto", 1L);
      CommonPlayer p2 = cm.findOrAdd("titi", 2L);
      assertEquals(cm.listPlayer().size(), 2);
      assertNotNull(p1);
      assertNotNull(p2);
      cm.finish();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
