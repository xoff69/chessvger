package com.xoff.chessvger.chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.common.GlobalManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class EngineManagerTest {

  @Test
  public void testEngineManager() {
    GlobalManager.getInstance().getEngineManager().clear();
    Engine e = EngineFactory.createDefaultEngineForSystem();
    GlobalManager.getInstance().getEngineManager().add(e);
    assertNotNull(GlobalManager.getInstance().getEngineManager().get(e.getId()));
    assertEquals(1, GlobalManager.getInstance().getEngineManager().list().size());
    GlobalManager.getInstance().getEngineManager().clear();
    assertEquals(0, GlobalManager.getInstance().getEngineManager().list().size());


  }

}