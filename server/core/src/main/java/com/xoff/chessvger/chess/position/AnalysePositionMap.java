package com.xoff.chessvger.chess.position;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnalysePositionMap extends AdbCommonKeyLong<AnalyzedPosition> {


  public AnalysePositionMap() {
    super(ParamConstants.DATA_FOLDER_COMMON + "analyzedPositionMap" + Constants.MAP_SFX);

  }


  /**
   * la clef c'est un ensemble de move separe par un constants.MAP_SEP
   */
  public void add(long key, AnalyzedPosition value) {

    AnalyzedPosition old = get(key);
    if (old != null) {
      if (old.getEvaluationTimeMillis() > value.getEvaluationTimeMillis()) {
        // on garde l'ancienne valeur
        return;
      }

    }
    super.add(key, value);
  }

}
