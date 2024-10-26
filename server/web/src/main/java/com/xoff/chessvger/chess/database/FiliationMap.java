package com.xoff.chessvger.chess.database;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.AdbCommonKeyLongSet2;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;

public class FiliationMap extends AdbCommonKeyLongSet2 {

  public FiliationMap()
  {
    super(ParamConstants.DATA_FOLDER_DB + "filiation" + Constants.MAP_SFX);

  }
}
