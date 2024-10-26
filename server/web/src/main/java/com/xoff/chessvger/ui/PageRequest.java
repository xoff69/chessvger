package com.xoff.chessvger.ui;

// FIXME pas ouf
public class PageRequest {
  public static Pageable of(int page, int size) {
    return new Pageable(page, size);
  }
}
