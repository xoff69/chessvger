package com.xoff.chessvger.chess.position;

public interface IAnalysedPositionManager {

  public void add(AnalyzedPosition f);

  public void del(AnalyzedPosition f);

  public AnalyzedPosition get(long id);


  public void finish();


  public void clear();
}
