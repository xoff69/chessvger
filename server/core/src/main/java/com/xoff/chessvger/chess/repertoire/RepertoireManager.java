package com.xoff.chessvger.chess.repertoire;

import com.xoff.chessvger.common.ACommonManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepertoireManager extends ACommonManager<Long, Repertoire>
    implements IRepertoireManager {


  public RepertoireManager() {
    super(ParamConstants.DATA_FOLDER_COMMON + "RepertoireMap" + Constants.MAP_SFX);

  }

  public List<Repertoire> findRepertoire(long userId) {
    List<Repertoire> repertoires = findAll();
    log.info("user="+userId+ " findRepertoire repertoires nombre: "+repertoires.size());
    List<Repertoire> result = new ArrayList<>();
    for (Repertoire repertoire : repertoires) {
      log.info("findRepertoire repertoire "+repertoire);
      if (repertoire.getUserId() == userId) {
        result.add(repertoire);
      }
    }
    log.info("findRepertoire "+result.size());
    return result;

  }

}
