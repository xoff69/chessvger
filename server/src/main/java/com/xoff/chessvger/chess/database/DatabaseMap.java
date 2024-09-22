package com.xoff.chessvger.chess.database;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseMap extends AdbCommonKeyLong<Database> {
  public DatabaseMap() {
    super(ParamConstants.DATA_FOLDER_DB + "bds" + Constants.MAP_SFX);

  }
}