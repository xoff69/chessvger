package com.xoff.chessvger.util;

public class SessionKeyGenerator {

  private static SessionKeyGenerator _instance = null;
  private int index;

  private SessionKeyGenerator() {
    index = 1;
  }

  public static SessionKeyGenerator getInstance() {
    if (_instance == null) {
      _instance = new SessionKeyGenerator();
    }
    return _instance;
  }

  public void reset() {
    index = 0;
  }

  public int next() {
    index++;
    return index;
  }
}
