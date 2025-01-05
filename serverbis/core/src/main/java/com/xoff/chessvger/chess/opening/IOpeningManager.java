package com.xoff.chessvger.chess.opening;

import java.util.List;

public interface IOpeningManager {
  Opening findOpening(List<String> moves);

  void finish();

  List<Opening> list();

  void add(Opening opening);

  Opening findOpening(String eco);
}
