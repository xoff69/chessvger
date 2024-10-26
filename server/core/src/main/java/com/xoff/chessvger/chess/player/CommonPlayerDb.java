package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.util.PgnUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CommonPlayerDb {


  private static final int IDNUMBER = 15;
  private static final int NAME = 61;
  private static final int FED = 4;
  private static final int SEX = 4;
  private static final int TIT = 5;
  private static final int WTIT = 5;
  private static final int OTIT = 15;
  private static final int FOA = 4;
  private static final int SRTNG = 6;
  private static final int SGM = 4;
  private static final int SK = 3;
  private static final int RRTNG = 6;
  private static final int RGM = 4;
  private static final int RK = 3;
  private static final int BRTNG = 6;
  private static final int BGM = 4;
  private static final int BK = 3;
  private static final int BDAY = 6;
  private static final int FLAG = 4;


  private final CommonPlayerMap map;


  public CommonPlayerDb() {
    map = new CommonPlayerMap();
    listPlayer();
  }

  public void clear() {
    map.clear();
  }

  public int size() {
    return map.size();
  }


  public void add(long key, CommonPlayer va) {

    map.add(key, va);
  }

  public List<CommonPlayer> findByNameOrID(String param) {
    // si param est vide, on renvoie tout
    //  log.info("findByNameOrID:" + param);
    List<CommonPlayer> l = new ArrayList();
    if (!StringUtils.isEmpty(param)) {

      long lid = Long.parseLong(param);
      CommonPlayer commonPlayer = findById(lid);
      if (commonPlayer != null) {
        l.add(commonPlayer);
      }


      commonPlayer = findByName(param);
      if (commonPlayer != null) {
        l.add(commonPlayer);
      }

    } else {
      l = map.list();
    }
    return l;
  }

  public List<String> listPlayer() {
    return map.listNames();
  }


  public List<Long> listFideId() {
    return map.listFideId();
  }


  public CommonPlayer findById(long idFide) {
    return map.findById(idFide);

  }


  public CommonPlayer findByName(String name) {
    return map.findByName(name);

  }

  public List<CommonPlayer> findByNameOrIDSearch(String pparam) {

    List<CommonPlayer> resultat = new ArrayList();
    List<String> ls = listPlayer();
    String param = pparam.toLowerCase();
    for (String s : ls) {
      if (s.toLowerCase().contains(param)) {
        CommonPlayer p = findByName(s);
        log.info("on trouve " + s);
        if (p != null) {
          resultat.add(p);
        }
      }
    }
    List<Long> ll = listFideId();

    for (Long s : ll) {

      CommonPlayer p = findById(s);
      if (p != null) {
        resultat.add(p);

      }
    }

    return resultat;

  }

  public void finish() {

    map.commit();
  }


  public int importeFidePlayer(String emplacement, SynonymPlayerManager synonymPlayerManager,
                               FamousPlayerManager famous) {

    try (Stream<String> stream = Files.lines(Paths.get(emplacement))) {
      final int[] compteurLigne = {0};
      stream.forEach((line) -> {

        CommonPlayer player = new CommonPlayer();

        int debut = 0;
        int fin = IDNUMBER;
        try {
          player.setIdnumber(Long.parseLong(line.substring(debut, fin).trim()));
        } catch (Exception e) {
          log.error(" importeFidePlayer " + line);
        }
        debut = fin;
        fin = fin + NAME;
        player.setName(line.substring(debut, fin).trim());
        player.setNakedName(PgnUtil.playerDenudeName(player.getName()));
        debut = fin;
        fin = fin + FED;

        player.setFex(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SEX;

        player.setSex(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + TIT;

        player.setTit(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + WTIT;

        player.setWtit(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + OTIT;

        player.setOtit(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + FOA;

        player.setFoa(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SRTNG;

        player.setSsrtng(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SGM;

        player.setSgm(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SK;
        player.setSk(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + RRTNG;
        player.setRrtng(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + RGM;
        player.setRgm(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + RK;
        player.setRk(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BRTNG;
        player.setBrtng(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BGM;
        player.setBgm(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BK;
        player.setBk(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BDAY;
        int max = Math.min(fin, line.length());
        player.setBday(line.substring(debut, max - 1).trim());
        debut = fin;
        fin = fin + FLAG;
        max = Math.min(fin, line.length());
        if (max > debut) {
          player.setFlag(line.substring(debut, max - 1).trim());
        }
        //     log.info(player);
        if (compteurLigne[0] > 0) {

          add(player.getIdnumber(), player);
          famous.addIfRelevant(player);
        }
        compteurLigne[0]++;
      });

    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
    return map.size();
  }

}
