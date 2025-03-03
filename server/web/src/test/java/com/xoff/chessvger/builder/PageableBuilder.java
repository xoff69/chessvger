package com.xoff.chessvger.builder;

import com.xoff.chessvger.ui.web.controller.tools.PageRequest;
import com.xoff.chessvger.util.Pageable;

public class PageableBuilder {

  public static Pageable buildPageable() {
    return PageRequest.of(1, 100);
  }
}
