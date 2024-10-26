package com.xoff.chessvger.chess.engine;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class EngineManager implements IEngineManager {

  private final EngineMap map;


  public EngineManager() {
    map = new EngineMap();
    if (map.size() == 0) {
      Engine e = EngineFactory.createDefaultEngineForSystem();
      map.add(e.getId(), e);
    }

  }


  public void finish() {
    map.commit();
  }

  public List<Engine> list() {
    return map.list();
  }

  public Engine get(long id) {
    return map.findById(id);
  }

  public void add(Engine f) {
    map.add(f.getId(), f);
  }

  public void del(Engine f) {
    map.remove(f.getId());
  }

  public void clear() {

    map.clear();
  }
}
