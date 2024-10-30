package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.opening.Opening;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.view.StatJoueurView;
import com.xoff.chessvger.view.StatOpening;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@NoArgsConstructor
public class PlayerStatManager implements IPlayerStatManager {


  /**
   * determine ses stats avec les blancs, avec les noirs ses ouvertures
   * preferees sur l ensemble de ses parties
   */
  public StatJoueurView getStatJoueur(DatabaseManager databaseManager, CommonPlayer player) {

    StatJoueurView statJoeurView = new StatJoueurView();

    HashMap<String, StatOpening> openWhite = new HashMap();
    HashMap<String, StatOpening> openBlack = new HashMap();
    int nbblanc = 0;
    List<CommonGame> list = databaseManager.getGameOfAPlayerManager()
        .listGameOfAPlayer(databaseManager, player.getIdnumber());

    //  log.info("player=" + player);
    for (CommonGame g : list) {
      //    log.info("g=" + g);
      if (g.getWhiteFideId() == (player.getIdnumber())) {
        nbblanc++;

        if (!StringUtils.isEmpty(g.getEco())) {
          StatOpening so = openWhite.get(g.getEco());
          if (so == null) {
            so = new StatOpening();
            Opening eco = GlobalManager.getInstance().getOpeningManager().findOpening(g.getEco());
            so.setEco(eco);

            if (eco == null) {
              //     log.info(g);;
            } else {
              openWhite.put(eco.getEco(), so);
            }
          }
          so.setNbgames(so.getNbgames() + 1);
        }

        switch (g.getResult()) {
          case Constants.RESULT_1_0:
            statJoeurView.setStat10blanc(statJoeurView.getStat10blanc() + 1);
            break;
          case Constants.RESULT_0_1:
            statJoeurView.setStat12blanc(statJoeurView.getStat12blanc() + 1);
            break;
          default:
            statJoeurView.setStat01blanc(statJoeurView.getStat01blanc() + 1);
            break;
        }

      } else {

        if (!StringUtils.isEmpty(g.getEco())) {
          StatOpening so = openBlack.get(g.getEco());
          if (so == null) {
            so = new StatOpening();
            Opening eco = GlobalManager.getInstance().getOpeningManager().findOpening(g.getEco());
            so.setEco(eco);
            if (eco == null) {
              //   log.info(g);;
            } else {
              openBlack.put(eco.getEco(), so);
            }
          }
          so.setNbgames(so.getNbgames() + 1);
        }

        switch (g.getResult()) {
          case Constants.RESULT_1_0:
            statJoeurView.setStat10noir(statJoeurView.getStat10noir() + 1);
            break;
          case Constants.RESULT_0_1:
            statJoeurView.setStat12noir(statJoeurView.getStat12noir() + 1);
            break;
          default:
            statJoeurView.setStat01noir(statJoeurView.getStat01noir() + 1);
            break;
        }
      }

      statJoeurView.setStat10global(statJoeurView.getStat10blanc() + statJoeurView.getStat01noir());
      statJoeurView.setStat12global(statJoeurView.getStat12blanc() + statJoeurView.getStat12noir());
      statJoeurView.setStat01global(statJoeurView.getStat01blanc() + statJoeurView.getStat10noir());
    } // fin loop tous les games

    // on determine la tendance
    Collections.sort(list, new Comparator<CommonGame>() {
      @Override
      public int compare(CommonGame o1, CommonGame o2) {
        return o2.getDate().compareTo(o1.getDate());
      }

    });
    int borne = list.size();
    int tendance = 0;
    if (borne > Constants.OPENING_TENDANCE_NUMBER) {
      borne = Constants.OPENING_TENDANCE_NUMBER;
    }
    for (int i = 0; i < borne; i++) {
      CommonGame c = list.get(i);
      //    log.info(c);
      if (player.getIdnumber() == (c.getWhiteFideId())) {
        if (c.getResult() == Constants.RESULT_1_0) {
          tendance++;
        } else if (c.getResult() == Constants.RESULT_0_1) {
          tendance--;
        }
      }
      if (player.getIdnumber() == (c.getBlackFideId())) {
        if (c.getResult() == Constants.RESULT_1_0) {
          tendance--;
        } else if (c.getResult() == Constants.RESULT_0_1) {
          tendance++;
        }
      }
    }
    statJoeurView.setTendance(tendance);
    log.info("tendance=" + tendance);
    if (list.size() > 0) {
      int nbnoir = list.size() - nbblanc;
      // pourcentaege!!!!
      if (nbblanc > 0) {

        statJoeurView.setStat10blanc(statJoeurView.getStat10blanc() * 100 / nbblanc);
        statJoeurView.setStat12blanc(statJoeurView.getStat12blanc() * 100 / nbblanc);
        statJoeurView.setStat01blanc(statJoeurView.getStat01blanc() * 100 / nbblanc);
      }

      if (nbnoir > 0) {
        statJoeurView.setStat10noir(statJoeurView.getStat10noir() * 100 / nbnoir);
        statJoeurView.setStat12noir(statJoeurView.getStat12noir() * 100 / nbnoir);
        statJoeurView.setStat01noir(statJoeurView.getStat01noir() * 100 / nbnoir);
      }

      statJoeurView.setStat10global(statJoeurView.getStat10global() * 100 / list.size());
      statJoeurView.setStat12global(statJoeurView.getStat12global() * 100 / list.size());
      statJoeurView.setStat01global(statJoeurView.getStat01global() * 100 / list.size());
    }

    int maxitems = Constants.OPENING_FAVORITES_NUMBER;
    List<StatOpening> lstWs = new ArrayList(openWhite.values());
    Collections.sort(lstWs);
    if (lstWs.size() < maxitems) {
      maxitems = lstWs.size();
    }
    statJoeurView.setOpeningWhite(lstWs.subList(0, maxitems));
    //      log.info("maxitems w:" + maxitems);
    List<StatOpening> lstBs = new ArrayList(openBlack.values());
    Collections.sort(lstBs);
    maxitems = Constants.OPENING_FAVORITES_NUMBER;
    if (lstBs.size() < maxitems) {
      maxitems = lstBs.size();
    }
    statJoeurView.setOpeningBlack(lstBs.subList(0, maxitems));
    //    log.info("maxitems b:" + maxitems);

    return statJoeurView;

  }


}
