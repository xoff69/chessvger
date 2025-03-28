package com.xoff.chessvger.chess.position;

import com.xoff.chessvger.model.AnalyzedPosition;

public interface IAnalysedPositionManager {

  void add(AnalyzedPosition f);

  void del(AnalyzedPosition f);

  AnalyzedPosition get(long id);


  void finish();


  void clear();
}
