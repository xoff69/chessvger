package com.xoff.chessvger.chess.position;

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.common.AdbCommonKeyLongSet2;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PositionMap extends AdbCommonKeyLongSet2 {

  public PositionMap(String completeFileName) {
    super(completeFileName);
  }


  public List<Long> search(Filter filter) {

    List<Long> li = getValuesForKey(filter.getZobrist());
    log.info(" je cherche " + filter.getZobrist() + " je trouve  " + li.size());
    log.info("nombre de positions en stock " + size());
    return li;
  }

}