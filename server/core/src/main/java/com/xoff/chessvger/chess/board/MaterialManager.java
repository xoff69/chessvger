package com.xoff.chessvger.chess.board;

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

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.position.MaterialMap;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaterialManager implements IMaterialManager {

  private static final int NBMAP = 1;
  private final MaterialMap[] materialMaps;

  public MaterialManager(String dbname) {

    materialMaps = new MaterialMap[NBMAP];
    for (int i = 0; i < NBMAP; i++) {
      materialMaps[i] = new MaterialMap(dbname, i);
    }
  }

  public List<Long> search(Filter filter) {
    long materialValue = 0L;
    // TODO cas des champs a -1 = on n'a pas choisi
    materialValue = encode(materialValue, filter.getNbpionblanc(), DEBUT_PION_BLANC);
    materialValue |= encode(materialValue, filter.getNbpionnoir(), DEBUT_PION_NOIR);
    materialValue |= encode(materialValue, filter.getNbcavalierblanc(), DEBUT_CAVALIER_BLANC);
    materialValue |= encode(materialValue, filter.getNbcavaliernoir(), DEBUT_CAVALIER_NOIR);
    materialValue |= encode(materialValue, filter.getNbfoublanc(), DEBUT_FOU_BLANC);
    materialValue |= encode(materialValue, filter.getNbfounoir(), DEBUT_FOU_NOIR);
    materialValue |= encode(materialValue, filter.getNbdameblanc(), DEBUT_DAME_BLANC);
    materialValue |= encode(materialValue, filter.getNbdamenoir(), DEBUT_DAME_NOIR);
    materialValue |= encode(materialValue, filter.getNbtourblanc(), DEBUT_TOUR_BLANC);
    materialValue |= encode(materialValue, filter.getNbtournoir(), DEBUT_TOUR_NOIR);
    List<Long> all = new ArrayList<>();

    for (int i = 0; i < NBMAP; i++) {

      all.addAll(materialMaps[i].search(filter, materialValue));
    }
    return all;
  }


  public void clear() {
    for (MaterialMap m : materialMaps) {
      m.clear();
    }
  }


  public void add(long key, long value) {

    int index = Math.abs((int) key);

    materialMaps[index % NBMAP].add(key, value);

  }


  public void finish() {
//        log.info("commit");
    for (MaterialMap m : materialMaps) {
      m.commit();
    }
  }


}
