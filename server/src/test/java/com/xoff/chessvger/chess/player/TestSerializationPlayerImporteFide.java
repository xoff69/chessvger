package com.xoff.chessvger.chess.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.common.GlobalManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestSerializationPlayerImporteFide {

  @Test
  @DisplayName("testImportFidePlayer")
  public void testImportFidePlayer() {

    ICommonPlayerManager cm = GlobalManager.getInstance().getCommonPlayerManager();
    cm.clear();
    cm.forceUpdate();
    assertEquals(cm.listPlayer().size(), 3);
    cm.finish();

  }
}
