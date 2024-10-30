package com.xoff.chessvger.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PageView<T> {

  private long totalElements;
  private int totalPages;
  private int currentPage;
  @SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "We want that")
  private List<T> items;
  public PageView() {
    items = new ArrayList<T>();
  }
}
