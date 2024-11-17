package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDateUtils {

  @Test
  @DisplayName("testGetYear")
  public void testGetYear() {

    assertEquals(2000, DateUtils.getYear("2000"));
    assertEquals(2000, DateUtils.getYear("2000.23.01"));
    assertEquals(0, DateUtils.getYear(""));
  }

  @Test
  @DisplayName("testtoDateFile")
  public void testToDateFile() {

    assertEquals("1970_04_26", DateUtils.toDateFile(10000000000L));
    assertEquals("1970.04.26", DateUtils.toDate(10000000000L));
  }

}
