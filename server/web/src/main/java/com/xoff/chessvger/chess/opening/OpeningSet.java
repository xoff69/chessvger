package com.xoff.chessvger.chess.opening;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpeningSet extends AdbCommonKeyLong {

  public OpeningSet() {
    super(ParamConstants.DATA_FOLDER_COMMON + "OpeningMap" + Constants.MAP_SFX);
  }

  public Opening findOpening(List<String> moves) {
    List<Opening> l = list();

    Opening dernierConnu = null;
    for (Opening c : l) {

      if (c.isOpeningForGame(moves)) {
        dernierConnu = c;

      }
    }
    return dernierConnu;
  }

  public Opening findOpening(String eco) {
    List<Opening> l = list();

    for (Opening c : l) {

      if (c.getEco().equals(eco)) {
        return c;
      }
    }
    return null;

  }

}
