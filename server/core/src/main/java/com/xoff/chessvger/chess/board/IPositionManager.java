package com.xoff.chessvger.chess.board;

import com.xoff.chessvger.chess.filter.Filter;
import java.util.List;

public interface IPositionManager {


  public List<Long> search(Filter filter);


  public void clear();

  public void add(long key, long value);


  public void finish();
}
