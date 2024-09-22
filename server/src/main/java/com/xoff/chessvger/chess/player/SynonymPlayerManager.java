package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.common.AdbCommonKeyString;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.PgnUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SynonymPlayerManager {
  private static final String MAP_LOADED = "synonymloadedMap";
  private static final String FILENAME2 =
      ParamConstants.DATA_FOLDER_COMMON + MAP_LOADED + Constants.MAP_SFX;
  /**
   * MAP_LOADED contient une liste de synonymes pour un joueur a partir du
   * fichier des synonymes: anand viswanathan=ind anand viswanathan charge
   * dans loadSynonymFile
   */
  private final AdbCommonKeyString<String> mapFromFile;

  public SynonymPlayerManager() {
    mapFromFile = new AdbCommonKeyString(FILENAME2 + MAP_LOADED);
    if (mapFromFile.size() == 0) {
      load();
    }
  }

  public List<String> list() {
    return mapFromFile.list();
  }

  public void forceUpdate() {
    load();
  }

  private void load() {
    log.info("load synonyms");
    try {
      List<String> lines =
          Files.readAllLines(Paths.get(FilenameUtils.getName(ParamConstants.SYNONYM_PATH)));
      final int[] compteur = new int[1];
      compteur[0] = 0;
      lines.forEach((ligne) -> {
        if (compteur[0] != 0) {
          String[] items = ligne.split("=");
          if (items != null && items.length > 1) {
            // synonyme => clef
            mapFromFile.add(PgnUtil.playerDenudeName(items[1]), items[0]);

          }
        }
        compteur[0]++;
      });
      log.info("chargemnet des synonymes " + compteur[0]);
    } catch (IOException e) {
      log.error(e.getMessage());
    }

  }

  /**
   * on cherche le meilleur nom pour le joueur dans les synonyms
   */
  public String getBestName(String name) {

    // pour tous les synonymes de ce jour
    String syn = mapFromFile.get(name);
    //System.out.println("gest best name "+name+"-"+syn);
    if (StringUtils.isEmpty(syn)) {
      syn = name;
    }
    return syn;
  }


  public void finish() {
    mapFromFile.commit();
    //      log.info("SynonymPlayer.commit");
  }

}
