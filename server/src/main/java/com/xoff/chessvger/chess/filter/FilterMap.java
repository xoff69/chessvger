package com.xoff.chessvger.chess.filter;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FilterMap extends AdbCommonKeyLong<Filter> {

  public FilterMap() {
    super(ParamConstants.DATA_FOLDER_COMMON + "filterMap" + Constants.MAP_SFX);
  }


}