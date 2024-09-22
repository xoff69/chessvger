package com.xoff.chessvger.chess.userpack;

import com.xoff.chessvger.common.ACommonManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPackManager extends ACommonManager<Long, UserPack> implements IUserPackManager {


  public UserPackManager() {
    super(ParamConstants.DATA_FOLDER_COMMON + "UserPackMap" + Constants.MAP_SFX);

  }


}
