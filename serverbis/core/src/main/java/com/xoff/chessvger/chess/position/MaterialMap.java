package com.xoff.chessvger.chess.position;

import static com.xoff.chessvger.util.MaterialUtil.DEBUT_CAVALIER_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_CAVALIER_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_DAME_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_DAME_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_FOU_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_FOU_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_PION_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_PION_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_TOUR_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_TOUR_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.encode;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.common.AdbCommonKeyLongSet2;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaterialMap extends AdbCommonKeyLongSet2 {


  private long origine = 0L;

  public MaterialMap(String dbname, int i) {

    super(DatabaseManager.getFolder(dbname) + dbname + "MaterialMap" + i + "-" + Constants.MAP_SFX);


    origine = 0L;
    origine = encode(origine, 8, DEBUT_PION_BLANC);
    origine |= encode(origine, 8, DEBUT_PION_NOIR);
    origine |= encode(origine, 2, DEBUT_CAVALIER_BLANC);
    origine |= encode(origine, 2, DEBUT_CAVALIER_NOIR);
    origine |= encode(origine, 2, DEBUT_FOU_BLANC);
    origine |= encode(origine, 2, DEBUT_FOU_NOIR);
    origine |= encode(origine, 1, DEBUT_DAME_BLANC);
    origine |= encode(origine, 1, DEBUT_DAME_NOIR);
    origine |= encode(origine, 2, DEBUT_TOUR_BLANC);
    origine |= encode(origine, 2, DEBUT_TOUR_NOIR);
  }


  public List<Long> search(Filter filter, long materialValue) {
    Set<Long> resultat = new HashSet<>();

    log.info(Long.toBinaryString(materialValue));
    if (filter.isMaterialPrecis()) {
      resultat.addAll(getValuesForKey(materialValue));

    } else {
      List<Long> ki = keyset();
      System.out.println("SM keyset " + ki.size());
      for (long l : ki) {
        if ((l & materialValue) == materialValue) {
          resultat.addAll(getValuesForKey(l));
        }
      }

    }
    return new ArrayList<>(resultat);
  }
}
