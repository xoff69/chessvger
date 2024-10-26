package com.xoff.chessvger.chess.engine;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EngineMap extends AdbCommonKeyLong<Engine> {


  public EngineMap() {
    super(ParamConstants.DATA_FOLDER_COMMON + "engineMap" + Constants.MAP_SFX);


  }
}
