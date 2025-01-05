package com.xoff.chessvger.chess.opening;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpeningManager implements IOpeningManager {


  private final OpeningSet openingSet;


  public OpeningManager() {
    openingSet = new OpeningSet();
    if (openingSet.size() == 0) {
      log.info(">>load openings");

      List<Opening> list = OpeningFactory.loadAll();
      for (Opening op : list) {
        if (op.getEco() != null) {
          this.add(op);
        }
      }
    }
    log.info("<<load openings");

  }

  public List<Opening> list() {
    return new ArrayList<>(openingSet.list());
  }

  public Opening findOpening(List<String> moves) {
    return openingSet.findOpening(moves);
  }

  public void finish() {
    openingSet.commit();
  }

  public void add(Opening opening) {
    Long key = (long) (openingSet.size() + 1);
    openingSet.add(key, opening);
  }

  public Opening findOpening(String eco) {
    return openingSet.findOpening(eco);
  }


}
