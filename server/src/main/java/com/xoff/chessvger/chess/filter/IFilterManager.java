package com.xoff.chessvger.chess.filter;

import java.util.List;

public interface IFilterManager {

  public void add(Filter f);

  public void del(Filter f);

  public List<Filter> values();


  public void finish();
}
