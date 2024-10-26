package com.xoff.chessvger.chess.board;

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.position.PositionMap;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PositionManager implements IPositionManager {


  private final PositionMap[] positionMaps;

  public PositionManager(String fileName) {
//        log.info(">PositionManager");// ça met 11 minutes ca // 1 min/fichier
    positionMaps = new PositionMap[ParamConstants.NB_POSITION_MAP];
    for (int i = 0; i < ParamConstants.NB_POSITION_MAP; i++) {

      positionMaps[i] = new PositionMap(fileName + "_" + i + "_" + Constants.POSITION_SFX);
    }
    //     log.info("<PositionManager");
  }

  public static boolean uselessMethod() {

    return ParamConstants.NB_POSITION_MAP > 5;
  }

  public List<Long> search(Filter filter) {
    int index = (int) filter.getZobrist() % ParamConstants.NB_POSITION_MAP;
    return positionMaps[index].search(filter);

  }


  public void clear() {
    for (PositionMap m : positionMaps) {
      m.clear();
    }

  }


  public void add(long key, long value) {

    int index = (int) key % ParamConstants.NB_POSITION_MAP;
    positionMaps[index].add(key, value);

  }


  public void finish() {
//        log.info("commit");
    for (PositionMap m : positionMaps) {
      m.commit();
    }
  }

}
