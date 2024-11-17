package com.xoff.chessvger.ui.web.navigation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


public class TabList {
  @Getter
  @Setter
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<Tab> list;


  public TabList() {
    list = new ArrayList();

  }


  public void add(Tab o) {
    getList().add(o);
  }

  public void remove(Tab o) {
    getList().remove(o);
  }

  public Tab get(int i) {
    return getList().get(i);
  }

  public int size() {
    return getList().size();
  }


}
