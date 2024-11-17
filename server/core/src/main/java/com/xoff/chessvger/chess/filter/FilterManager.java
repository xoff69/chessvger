package com.xoff.chessvger.chess.filter;

import com.xoff.chessvger.common.DbKeyManager;
import java.util.ArrayList;
import java.util.List;


public class FilterManager implements IFilterManager {
  private final FilterMap map;


  public FilterManager() {
    map = new FilterMap();
    if (map.size() == 0) {

      Filter f2 = new Filter("label.filtre.hebdo",
          DbKeyManager.getInstance().getDbKeyGenerator().getNext());
      f2.setSystem(true);
      map.add(f2.getId(), f2);
    }
  }

  public void add(Filter f) {
    map.add(f.getId(), f);
  }

  public void del(Filter f) {
    map.remove(f.getId());
  }


  public List<Filter> values() {
    List<Filter> lf = new ArrayList();

    lf.addAll(map.list());
    return lf;
  }


  public void finish() {
    map.commit();
  }
}
