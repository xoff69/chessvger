package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTriplet {

  @Test
  @DisplayName("testTriplet")
  public void testTriplet() {
    TripletPgnAnalyse t = new TripletPgnAnalyse(5);
    t.incrDebut();
    assertEquals(6, t.getDebut());

    t.decrDebut();
    assertEquals(5, t.getDebut());

    t.incrFin();
    assertEquals(6, t.getFin());
  }
}
