package com.xoff.chessvger.ui;

import com.xoff.chessvger.util.Pageable;

// FIXME pas ouf
public class PageRequest {
  public static Pageable of(int page, int size) {
    return new Pageable(page, size);
  }
}
