package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.PgnUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonPlayerManager implements ICommonPlayerManager {


  private final CommonPlayerDb commonPlayerDB;

  private final SynonymPlayerManager synonymPlayerManager;
  // TODO
  private final FamousPlayerManager famousPlayerManager;

  public CommonPlayerManager() {
    commonPlayerDB = new CommonPlayerDb();
    // FIXME bizarre cette implenm ci dessous
    synonymPlayerManager = new SynonymPlayerManager();
    famousPlayerManager = new FamousPlayerManager();
    if (commonPlayerDB.size() == 0) {
      log.info("importe fide");
      importeFidePlayer();
    }
  }

  public void forceUpdate() {
    importeFidePlayer();
  }

  public void clear() {
    commonPlayerDB.clear();
  }


  public List<CommonPlayer> findByNameOrID(String param) {
    return commonPlayerDB.findByNameOrID(param);
  }


  public List<CommonPlayer> findByNameOrIDSearch(String param) {
    return commonPlayerDB.findByNameOrIDSearch(param);
  }


  public CommonPlayer findOrAdd(String pname, long fideID) {

    //String testName="Aagaard,J";
    CommonPlayer player = null;
    if (fideID != 0L) {
      player = findById(fideID);

    }
    //4100026        Karpov, Anatoly
    if (player == null) {
      /// le find by name fait aussi la recherche dans les synonymes
      player = findByName(pname);

      if (player == null) {

        player = new CommonPlayer();
        player.setName(pname);
        player.setNakedName(PgnUtil.playerDenudeName(pname));

        player.setIdnumber(fideID);
        if (player.getIdnumber() == 0L) {
          player.setIdnumber(Constants.ID_MIN_NON_FIDE_PLAYER +
              DbKeyManager.getInstance().getDbKeyGenerator().getNext());
        }
        commonPlayerDB.add(player.getIdnumber(), player);
      }
    }


    return player;
  }


  public List<String> listPlayer() {
    return commonPlayerDB.listPlayer();
  }

  /*
  recherche par identifiant fide
   */
  public CommonPlayer findById(long idFide) {
    return commonPlayerDB.findById(idFide);
  }

  public boolean isWellKnowPlayer(String name) {

    return famousPlayerManager.isWellKnown(name);
  }


  public CommonPlayer findByName(String name) {

    //  String testName="Aagaard,J";

    CommonPlayer player = commonPlayerDB.findByName(name);
    if (player != null) {

      return player;
    }
    // dans les synonyms

    String bestName = getSynonymPlayerManager().getBestName(PgnUtil.playerDenudeName(name));

    player = commonPlayerDB.findByName(bestName);
//System.out.println(bestName+"###player apres synonyme"+player);
    if (player != null) {

      return player;
    }
//System.out.println("variantes");
    // dans les variantes
    List<String> var = variantes(bestName);
    for (String v : var) {
      player = commonPlayerDB.findByName(v);
      if (player != null) {

        return player;
      }
    }

    //    log.info("pas trouve");
    return player;
  }


  private List<String> variantes(String name) {
    List<String> resultat = new ArrayList();
    //    if (name.contains("rj")) {
    resultat.add(name.replace("rj", "ri"));
    resultat.add(name.replace("ov", "of"));
    resultat.add(name.replace("ge", "gue"));
    resultat.add(name.replace("ck", "ch"));
    resultat.add(name.replace("ikt", "ict"));
    resultat.add(name.replace("ik", "ick"));
    resultat.add(name.replace("gu", "gou"));
    // }

    return resultat;
  }


  public int importeFidePlayer() {
    return importeFidePlayer(ParamConstants.PLAYERS_PATH);
  }

  public int importeFidePlayer(String emplacement) {
    commonPlayerDB.importeFidePlayer(emplacement, getSynonymPlayerManager(),
        getFamousPlayerManager());
    return commonPlayerDB.size();
  }

  public void finish() {
    commonPlayerDB.finish();
    getSynonymPlayerManager().finish();
    famousPlayerManager.finish();
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  public SynonymPlayerManager getSynonymPlayerManager() {
    return synonymPlayerManager;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  public FamousPlayerManager getFamousPlayerManager() {
    return famousPlayerManager;
  }
}
