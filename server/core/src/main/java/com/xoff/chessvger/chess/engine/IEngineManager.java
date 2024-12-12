package com.xoff.chessvger.chess.engine;

import java.util.List;

public interface IEngineManager {

  void finish();

  List<Engine> list();

  Engine get(long id);

  void add(Engine f);

  void del(Engine f);

  void clear();
}
