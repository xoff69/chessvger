package com.xoff.chessvger.chess.board;


import com.xoff.chessvger.chess.filter.Filter;
import java.util.List;

public interface IMaterialManager {


  List<Long> search(Filter filter);


  void clear();


  void add(long key, long value);


  void finish();

}
