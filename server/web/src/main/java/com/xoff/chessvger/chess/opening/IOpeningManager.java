package com.xoff.chessvger.chess.opening;

import java.util.List;

public interface IOpeningManager {
  public Opening findOpening(List<String> moves);

  public void finish();

  public List<Opening> list();

  public void add(Opening opening);

  public Opening findOpening(String eco);
}
