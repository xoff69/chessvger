package com.xoff.chessvger.chess.engine;

import java.util.List;

public interface IEngineManager {

  public void finish();

  public List<Engine> list();

  public Engine get(long id);

  public void add(Engine f);

  public void del(Engine f);

  public void clear();
}
