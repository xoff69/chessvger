package com.xoff.chessvger.chess.filter;

import java.util.List;

public interface IFilterManager {

  void add(Filter f);

  void del(Filter f);

  List<Filter> values();


  void finish();
}
