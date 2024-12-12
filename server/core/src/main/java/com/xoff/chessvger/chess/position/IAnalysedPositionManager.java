package com.xoff.chessvger.chess.position;

public interface IAnalysedPositionManager {

  void add(AnalyzedPosition f);

  void del(AnalyzedPosition f);

  AnalyzedPosition get(long id);


  void finish();


  void clear();
}
