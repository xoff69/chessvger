package com.xoff.chessvger.builder;

import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.Pageable;

public class PageableBuilder {

  public static Pageable buildPageable() {
    return PageRequest.of(1, 100);
  }
}
