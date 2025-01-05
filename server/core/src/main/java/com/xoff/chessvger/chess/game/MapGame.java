package com.xoff.chessvger.chess.game;

import static com.xoff.chessvger.util.StringUtility.between;
import static com.xoff.chessvger.util.StringUtility.egaliteChaine;
import static com.xoff.chessvger.util.StringUtility.egaliteChainePlayer;
import static com.xoff.chessvger.util.StringUtility.egaliteNombre;

import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.DateUtils;
import com.xoff.chessvger.util.FaitsDeJeuUtil;
import com.xoff.chessvger.util.PgnUtil;
import com.xoff.chessvger.util.StringUtility;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class MapGame extends AdbCommonKeyLong<CommonGame> {

  private final IGameWhereMapManager gameWhereMapManager;

  private List<CommonGame> games;


  public MapGame(DatabaseManager databaseManager, String basename) {
    super(DatabaseManager.getFolder(databaseManager.createName()) + basename + "gameMap" +
        Constants.MAP_SFX);
    // this.pm = pm;
    this.gameWhereMapManager = databaseManager.getGameWhereMapManager();


    //  log.info("MapGame:" + filename + ",map=" + getMapName() + ".init" + "-" + size());
    games = new ArrayList<>();
    load();
  }

  private static boolean isLeftIntoRight(String[] left, String[] right) {
    int len = left.length;

    if (len > right.length) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      if (!left[i].equals(right[i])) {
        return false;
      }
    }
    return true;
  }

  public void add(long key, CommonGame value, DBOperation operation) {
    super.add(key, value);
    gameWhereMapManager.add(key, value.getFirstMove());
    switch (operation) {
      case UPDATE -> {
        games.remove(value);
        games.add(value);
      }
      case AJOUT -> {
        games.add(value);
      }
      case DUPLICATE -> {
      }// TODO

      case DELETE -> {
      }// TODO

      case UNDELETE -> {
      }// TODO

    }


  }

  public List<CommonGame> getGameByStart(String[] previousMoves) {
    List<CommonGame> result = new ArrayList();

    List<CommonGame> all = getGames();
    for (CommonGame g : all) {

      //  System.out.println("getGameByStart=="+g);
      String[] completeList = PgnUtil.extractMovesFromString(g.getMoves());

      if (isLeftIntoRight(previousMoves, completeList)) {
        result.add(g);
      }

    }

    return result;
  }

  public List<CommonGame> listGameOfAPlayerForEco(long idPlayer, String eco, boolean isWhite) {
    List<CommonGame> result = new ArrayList<>();

    try {

      getGames().forEach((game) -> {
        if (game.getBlackFideId() == (idPlayer) && !isWhite && eco.equals(game.getEco())) {
          result.add(game);
        } else if (game.getWhiteFideId() == (idPlayer) && isWhite && eco.equals(game.getEco())) {
          result.add(game);
        }
      });

    } catch (Exception e) {
      log.error(e.getMessage());

    }

    return result;
  }

  //  TODO splitter
  public List<CommonGame> search(Filter filter) {
    List<CommonGame> listResultat = new ArrayList<>();
    //System.out.println("search cas general");
    //   log.info(">>>>>>>>>>>>>>>>>>>>>game:" + filter);
    if (filter.isCasGeneral()) {

      try {
        //         log.info(">>>>>>>>>>>>>>>>>>>>>game:" + games.size() + "\nFILTER=" + filter);

        for (CommonGame game : getGames()) {
          //@TODO faire les faits de jeu en meme temps
          boolean isToAdd = true;

          //           log.info(">>>>>>>>>>>>>>>>>>>>>game:" + game);
          // on ne prend pas les parties mises a jour

          isToAdd = isToAdd &&
              (FaitsDeJeuUtil.isMatching(game.getInformationsFaitDeJeu(), filter.maskFaitsDeJeu()));


          if (!isToAdd) {
            continue;
          }

          // comment on a le premier vrai
          if (!StringUtility.egaliteChaine(filter.getEvent(), game.getEvent())) {
            continue;
          }
          if (!egaliteChaine(filter.getSite(), game.getSite())) {
            continue;
          }

          switch (filter.getSelectedModeCoups()) {
            case Constants.INF_SEL:// <
              isToAdd = isToAdd && (game.getNbcoups() <= filter.getNbcoup1());

              break;
            case Constants.EGAL_SEL:  // =
              if (filter.getNbcoup1() > 0) {

                isToAdd = isToAdd && (game.getNbcoups() == filter.getNbcoup1());
              }
              break;
            case Constants.SUP_SEL: //>
              isToAdd = isToAdd && (game.getNbcoups() >= filter.getNbcoup1());

              break;
            case Constants.BETWEEN_SEL: // entre

              isToAdd = isToAdd && (game.getNbcoups() >= filter.getNbcoup1() &&
                  game.getNbcoups() <= filter.getNbcoup2());

              break;
            default:
              log.info("map game switch 1");
          }

          if (!isToAdd) {
            continue;
          }

          if (!filter.isCampIndifferent()) {

            // plantage des foisinfo
            //log.info("whilte="+filter.getWhite()+"-//"+ game.getPlayerWhite());
            isToAdd = isToAdd && egaliteChainePlayer(filter.getWhite(), game.getNomBlanc(),
                filter.isModeApproximatif());

            isToAdd = isToAdd && egaliteChainePlayer(filter.getBlack(), game.getNomNoir(),
                filter.isModeApproximatif());


          } else {
            boolean p1 = false;

            p1 = egaliteChainePlayer(filter.getWhite(), game.getNomBlanc(),
                filter.isModeApproximatif()) &&
                egaliteChainePlayer(filter.getBlack(), game.getNomNoir(),
                    filter.isModeApproximatif());

            boolean p2 = false;

            p2 = egaliteChainePlayer(filter.getWhite(), game.getNomNoir(),
                filter.isModeApproximatif()) &&
                egaliteChainePlayer(filter.getBlack(), game.getNomBlanc(),
                    filter.isModeApproximatif());

            isToAdd = isToAdd && (p1 || p2);


          }
          if (!isToAdd) {
            continue;
          }
          isToAdd = isToAdd && egaliteChaine(filter.getRound(), game.getRound());
          if (game.getNomBlanc().equals("Vedder,R")) {  // DEBUG TODO
            log.info("vedder a  " + isToAdd + " " + game.getNomBlanc());
          }
          switch (filter.getSelectedModeEco()) {
            case Constants.INF_SEL:// <
              isToAdd = isToAdd && between("@00", filter.getEco1(), game.getEco());

              break;
            case Constants.EGAL_SEL:  // =
              isToAdd = isToAdd && egaliteChaine(filter.getEco1(), game.getEco());

              break;
            case Constants.SUP_SEL: //>

              isToAdd = isToAdd && between(filter.getEco1(), "E99", game.getEco());
              break;
            case Constants.BETWEEN_SEL: // entre
              isToAdd = isToAdd && between(filter.getEco1(), filter.getEco2(), game.getEco());

              break;
            default:
              log.error("map game sw");
          }
          // FIXME debug
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder b  " + isToAdd + " " + game.getNomBlanc());
          }
          if (!isToAdd) {
            continue;
          }
          if (filter.getResult() != 0) {
            //100, 110,101,111,10,11,1,0

            int critres = filter.getResult();
            boolean isScore = false;
            if (critres > 99) { // 01
              isScore = (Constants.RESULT_0_1.equals(game.getResult()));

              critres = critres - 100;
            }

            if (critres > 9) { // 12
              isScore = isScore || (game.getResult() == Constants.RESULT_1_2);

              critres = critres - 10;
            }
            if (critres > 0) { // 10
              isScore = isScore || (game.getResult() == Constants.RESULT_1_0);

            }
            isToAdd = isToAdd && isScore;
          }
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder c  " + isToAdd + " " + game.getNomBlanc());
          }
          if (!isToAdd) {
            continue;
          }
          String c1 = filter.getBlackTitle();
          if ("-".equals(c1)) {
            c1 = "";
          }
          isToAdd = isToAdd && egaliteChaine(c1, game.getBlackTitle());
          c1 = filter.getWhiteTitle();
          if ("-".equals(c1)) {
            c1 = "";
          }
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder d  " + isToAdd + " " + game.getNomBlanc());
          }
          isToAdd = isToAdd && egaliteChaine(c1, game.getWhiteTitle());
          isToAdd = isToAdd && egaliteNombre(filter.getWhiteElo(), game.getWhiteElo());
          isToAdd = isToAdd && egaliteNombre(filter.getBlackElo(), game.getBlackElo());
          isToAdd = isToAdd && egaliteNombre(filter.getWhiteFideId(), game.getWhiteFideId());
          isToAdd = isToAdd && egaliteNombre(filter.getBlackFideId(), game.getBlackFideId());
          if (!isToAdd) {
            continue;
          }
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder w  " + isToAdd + " " + game.getNomBlanc());
          }
          if (!StringUtils.isEmpty(filter.getDate1())) {

            if (StringUtils.isEmpty(game.getDate()) && filter.isIncludeDateNull()) {
              // rien a faire
            } else {
              //"label.avant","label.egal","label.apres","label.entre")};
              int anneeCrit = DateUtils.getYear(filter.getDate1());
              int annee1 = DateUtils.getYear(game.getDate());
              int annee1Event = DateUtils.getYear(game.getEventDate());
              switch (filter.getSelectedModeDate()) {
                case Constants.INF_SEL:

                  isToAdd = isToAdd && (anneeCrit > annee1 || anneeCrit > annee1Event);
                  break;
                case Constants.EGAL_SEL:
                  isToAdd = isToAdd && (anneeCrit == annee1 || anneeCrit == annee1Event);
                  break;
                case Constants.SUP_SEL:

                  isToAdd = isToAdd && (anneeCrit < annee1 || anneeCrit < annee1Event);
                  break;
                case Constants.BETWEEN_SEL://entre
                  int anneeCrit2 = DateUtils.getYear(filter.getDate2());
                  isToAdd = isToAdd && ((anneeCrit <= annee1 && anneeCrit2 >= annee1) ||
                      (anneeCrit <= annee1Event && anneeCrit2 >= annee1Event));

                  break;
                default:
              }
            }
          }
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder z  " + isToAdd + " " + game.getNomBlanc());
          }
        /* FIXME : a remettre
        // favori
        boolean critereFav = false;
        char fav = filter.getFavori();
        switch (fav) {
            case Constants.NA:
                critereFav = true;
                break;
            case Constants.YES:
                critereFav = game.isFavori();
                break;
            case Constants.NO:
                critereFav = !game.isFavori();
                break;
        }
        isToAdd = isToAdd && critereFav;
        if (game.getNomBlanc().equals("Vedder,R")){
            log.info("vedder zxxx  "+isToAdd+ " "+game.getNomBlanc());
        }
        // theorique
        boolean critereTheo = false;
        char theo = filter.getTheorique();
        switch (theo) {
            case Constants.NA:
                critereTheo = true;
                break;
            case Constants.YES:
                critereTheo = game.isTheorique();
                break;
            case Constants.NO:
                critereTheo = !game.isTheorique();
                break;
        }
        isToAdd = isToAdd && critereTheo;
        // interet
        if (game.getNomBlanc().equals("Vedder,R")){
            log.info("vedder u  "+isToAdd+ " "+game.getNomBlanc());
        }
        boolean critereInteret = false;
        char binteret = filter.getInteret();
        switch (binteret) {
            case '-':
                critereInteret = true;
                break;
            default:
                critereInteret = str2Int("" + binteret) == game.getInteret();
                break;
        }
        isToAdd = isToAdd && critereInteret;
        */
          boolean asDUS = false;
          // nombre de jour
// pour la condition last update

          long now = System.currentTimeMillis();
          long lastu = now;
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder y  " + isToAdd + " " + game.getNomBlanc());
          }
          if (filter.getNbJourSinceUpdate() != 0) {

            long nbmillisaday = 24 * 3600 * 1000;
            int nbjour = filter.getNbJourSinceUpdate();

            lastu = now - (nbjour * nbmillisaday);


          } else {
            lastu = 0;
          }
          if (game.getLastUpdate() > lastu) {
            asDUS = true;
          }
          if (game.getNomBlanc().equals("Vedder,R")) {
            log.info("vedder bdfd  " + isToAdd + " " + game.getNomBlanc());
          }
          if (isToAdd && asDUS) {
            listResultat.add(game);
          }

        }

      } catch (Exception e) {

        log.error(e.getMessage());
      }
      //    log.info(getMapName() +" final gameDB " + listResultat.size());
    }
    return listResultat;
  }


  public void update() {
    load();
  }

  private void load() {
    //log.info("debut load");
    games.clear();
    games = new ArrayList(list());

    // log.info("fin load:" + games.size());
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  public List<CommonGame> getGames() {
    if (games.isEmpty()) {
      load();
    }
    return games;
  }


}
