package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStringUtility {
  @Test
  @DisplayName("testEgaliteChaine")
  public void testEgaliteChaine() {

    assertTrue(StringUtility.egaliteChaine("toTo", "TOTO"));
    assertTrue(StringUtility.egaliteChaine("", "TOTO"));
    assertFalse(StringUtility.egaliteChaine("titi", "TOTO"));
  }

  @Test
  @DisplayName("testEgaliteChainePlayer")
  public void testEgaliteChainePlayer() {
    assertTrue(StringUtility.egaliteChainePlayer("toTo", "TOTO", true));
    assertTrue(StringUtility.egaliteChainePlayer("", "TOTO", true));
    assertFalse(StringUtility.egaliteChainePlayer("titi", "TOTO", true));
  }

  @Test
  @DisplayName("testisNumberOrEmpty")
  public void testIsNumberOrEmpty() {
    assertTrue(StringUtility.isNumberOrEmpty("25"));
    assertTrue(StringUtility.isNumberOrEmpty(""));
    assertFalse(StringUtility.isNumberOrEmpty("aa"));

  }

  @Test
  @DisplayName("testisDateOrEmpty")
  public void testIsDateOrEmpty() {
    assertTrue(StringUtility.isNumberOrEmpty("25"));
    assertTrue(StringUtility.isNumberOrEmpty(""));
    assertFalse(StringUtility.isNumberOrEmpty("aa"));

  }

  @Test
  @DisplayName("testletterToNumberEco")
  public void testLetterToNumberEco() {
    assertEquals(StringUtility.letterToNumberEco('E'), 5000);
  }

  @Test
  @DisplayName("testBetween")
  public void testBetween() {
    assertTrue(StringUtility.between("B00", "C00", "B12"));
    assertFalse(StringUtility.between("B00", "C00", "C12"));
  }

  @Test
  @DisplayName("testNotZero")
  public void testNotZero() {
    assertEquals(StringUtility.notZeronotEmpty(5), "5");
  }

  @Test
  @DisplayName("testListToString")
  public void testListToString() {

    List<String> l = new ArrayList<>();
    l.add("A");
    l.add("B");
    assertEquals(StringUtility.listToString(l), "A" + Constants.MAP_SEP + "B" + Constants.MAP_SEP);
  }

  @Test
  @DisplayName("testSetToString")
  public void testSetToString() {

    Set<String> l = new HashSet<>();
    l.add("A");
    l.add("B");
    assertEquals(StringUtility.setToString(l), "A" + Constants.MAP_SEP + "B" + Constants.MAP_SEP);
  }

  @Test
  @DisplayName("testString2List")
  public void testString2List() {

    String s = "A" + Constants.MAP_SEP + "B";

    assertEquals(StringUtility.stringToList(s).size(), 2);
  }

  @Test
  @DisplayName("testString2Set")
  public void testString2Set() {

    String s = "A" + Constants.MAP_SEP + "B";

    assertEquals(StringUtility.stringToSet(s).size(), 2);
  }
}

