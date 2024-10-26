package com.xoff.chessvger.chess.engine;

import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.ParamConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j

public class EngineFactory {


  private static final String SF_PATH = ParamConstants.SF_PATH;


  public static Engine createDefaultEngineForSystem() {

    Engine e = new Engine(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    e.setPv(3);
    e.setDescription("StockFish 11");
    e.setProf(20);
    e.setName("StockFish 11");
    e.setPath(StringUtils.EMPTY);

    e.setPath(SF_PATH);

    log.info("create default engine " + e);
    return e;

  }


}
