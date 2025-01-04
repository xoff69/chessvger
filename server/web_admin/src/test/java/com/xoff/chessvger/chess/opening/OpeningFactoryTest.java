package com.xoff.chessvger.chess.opening;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class OpeningFactoryTest {


  @Test
  void loadAll() {
    List<Opening> lop = OpeningFactory.loadAll();
    assertEquals(lop.size(), 2010);
  }
}