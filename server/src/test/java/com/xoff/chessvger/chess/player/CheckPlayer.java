package com.xoff.chessvger.chess.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.common.GlobalManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class CheckPlayer {


  @Test
  @DisplayName("testCheckPlayer")
  public void testCheckPlayer() {


    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    // ce qui est appele dans le parseData
    //4100026        Karpov, Anatoly
    CommonPlayer commonPlayer = pm.findOrAdd("Karpov,A", 1233456456);


    assertNotNull(commonPlayer);
    assertTrue(commonPlayer.toString().contains("Kar"));

  }

  @Test
  @DisplayName("testInsertSamePlayer")
  public void testInsertSamePlayer() {


    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    // ce qui est appele dans le parseData
    //4100026        Karpov, Anatoly
    CommonPlayer j = pm.findOrAdd("Krapov,A", 0);

    CommonPlayer j2 = pm.findOrAdd("Krapov,A", 0);
    assertNotNull(j);
    assertNotNull(j2);
    assertEquals(j.getIdnumber(), j2.getIdnumber());

  }

  @Test
  @DisplayName("testInsertSamePlayer2")
  public void testInsertSamePlayer2() {


    ICommonPlayerManager pm = GlobalManager.getInstance().getCommonPlayerManager();
    // ce qui est appele dans le parseData
    //4100026        Karpov, Anatoly
    CommonPlayer j = pm.findOrAdd("Krapov,A", 500);

    CommonPlayer j2 = pm.findOrAdd("Krapov,A", 500);
    assertNotNull(j);
    assertNotNull(j2);
    assertEquals(j.getIdnumber(), j2.getIdnumber());

  }
}
