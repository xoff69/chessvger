package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.common.AdbCommonKeyString;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class FamousPlayerManager {

  private static final String MAP_LOADED = "famousMap";
  private static final String[] a =
      {"Fischer, Robert", "Botvinnik, Mikhail", "Capablanca Jose Raul"};
  private static final List<String> KNOWN = Arrays.asList(a);
  private static final String FILENAME2 =
      ParamConstants.DATA_FOLDER_COMMON + MAP_LOADED + Constants.MAP_SFX;
  private final AdbCommonKeyString<String> mapFromFile;

  public FamousPlayerManager() {
    mapFromFile = new AdbCommonKeyString<String>(FILENAME2);

  }

  public void keyset() {

    for (String s : mapFromFile.keyset()) {
      System.out.println(s);
    }
  }

  /**
   * ajoute le joueur a la liste s'il respecte la regle
   */
  public boolean addIfRelevant(CommonPlayer player) {
    if ("GM".equals(player.getTit())) {
      try {
        int rating = Integer.parseInt(player.getSsrtng());
        if (rating >= Constants.RATINGFORFAMOUS) {
          mapFromFile.add(player.getName(), Constants.PRESENT);

          return true;
        }
      } catch (NumberFormatException e) {
        log.error("addIfRelevant error " + player);
      }

    }
    return false;
  }

  /**
   * on cherche le meilleur nom pour le joueur dans les synonyms
   */
  public boolean isWellKnown(String name) {

    if (KNOWN.contains(name)) {
      return true;
    }
    // pour tous les synonymes de ce jour
    String syn = mapFromFile.get(name);

    return !StringUtils.isEmpty(syn);
  }


  public void finish() {
    mapFromFile.commit();
  }

}
