package com.xoff.chessvger.chess.database;

import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.chess.board.IMaterialManager;
import com.xoff.chessvger.chess.board.IPositionManager;
import com.xoff.chessvger.chess.board.MaterialManager;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.board.PositionManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.CommonGameManager;
import com.xoff.chessvger.chess.game.GameManagerRun;
import com.xoff.chessvger.chess.game.GameOfAPlayerManager;
import com.xoff.chessvger.chess.game.GameStatManager;
import com.xoff.chessvger.chess.game.GameWhereMapManager;
import com.xoff.chessvger.chess.game.GlobalGameManager;
import com.xoff.chessvger.chess.game.ICommonGameManager;
import com.xoff.chessvger.chess.game.IGameOfAPlayerManager;
import com.xoff.chessvger.chess.game.IGameStatManager;
import com.xoff.chessvger.chess.game.IGameWhereMapManager;
import com.xoff.chessvger.chess.game.IGlobalGameManager;
import com.xoff.chessvger.chess.game.ItemGameTree;
import com.xoff.chessvger.chess.game.OneGameTree;
import com.xoff.chessvger.chess.game.SearchRun;
import com.xoff.chessvger.chess.history.HistoryManager;
import com.xoff.chessvger.chess.history.IHistoryManager;
import com.xoff.chessvger.chess.move.ResultInterpretation;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.chess.player.IPlayerOfDbManager;
import com.xoff.chessvger.chess.player.IPlayerStatManager;
import com.xoff.chessvger.chess.player.PlayerOfDbManager;
import com.xoff.chessvger.chess.player.PlayerStatManager;
import com.xoff.chessvger.chess.stat.GlobalBrowserStatManager;
import com.xoff.chessvger.chess.stat.IBrowserStatManager;
import com.xoff.chessvger.chess.stat.IGlobalBrowserStatManager;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.CVFileUtils;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.util.Parser;
import com.xoff.chessvger.util.RejetUtil;
import com.xoff.chessvger.view.JoueurView;
import com.xoff.chessvger.view.StatBrowserView;
import com.xoff.chessvger.view.StatGame;
import com.xoff.chessvger.view.StatJoueurView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
public class DatabaseManager implements IDatabaseManager {


  private final Database database;
  private IGlobalGameManager globalGameManager;
  private IPositionManager positionManager;
  private IMaterialManager materialManager;
  private IGlobalBrowserStatManager globalBrowserStatManager;
  private IPlayerStatManager playerStatManager;
  private IGameStatManager gameStatManager;
  private IGameWhereMapManager gameWhereMapManager;
  private IGameOfAPlayerManager gameOfAPlayerManager;
  private IHistoryManager historyManager;
  private Parser parser;
  private IPlayerOfDbManager playerOfDbManager;

  private RejetUtil rejetUtil;

  public DatabaseManager(Database database) {
    this.database = database;
    init(createName());
  }

  public static boolean exists(String name, String folder) {
    return CVFileUtils.exists(name + "_" + Constants.CVDB_EXT, folder);

  }

  public static String getFolder(String dbname) {
    return ParamConstants.DATA_FOLDER_DB + dbname + File.separator;
  }

  public long count() {
    return database.getNbgames();
  }

  public long duplicate(long userId) {
    // TODO
    log.info("databasemanager.duplicate FIXME " + userId);

    // creer une database dans le repertoire target,
    // FIXME le probleme c est que ca s appuie sur le create name

    Database newDatabase = new Database();
    newDatabase.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    newDatabase.setName(this.database.getName());
    newDatabase.setDescription(this.database.getDescription() + "  copie");
    newDatabase.setLastUpdate(System.currentTimeMillis());
    GlobalManager.getInstance().getDatabaseMap().add(newDatabase.getId(), newDatabase);
    GlobalManager.getInstance().getFiliationMap().add(this.getDatabaseId(), newDatabase.getId());

    // creation du repertoire
    DatabaseManager databaseManager = new DatabaseManager(newDatabase);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);
    List<CommonGame> games = this.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame commonGame : games) {
      databaseManager.upsert(commonGame, DBOperation.AJOUT);
    }
    newDatabase.setNbgames(games.size());
    databaseManager.postUpdateGameAndStat();
    log.info("duplicate postUpdateGameAndStat PGN" + games.size());
    // duplicate gameOfBbPlayer
    List<Long> playersIds = getPlayerOfDbManager().listIdsOfPlayer();
    for (Long l : playersIds) {
      databaseManager.getPlayerOfDbManager().add(l);
    }

    databaseManager.finish();

    GlobalManager.getInstance().getDatabaseMap().add(newDatabase.getId(), newDatabase);
    return newDatabase.getId();
  }

  public long getDatabaseId() {
    return database.getId();
  }

  public String getDatabaseName() {
    return database.getName();
  }

  private void init(String dbName) {

    CVFileUtils.createDir(getFolder(createName()));

    parser = new Parser();
    rejetUtil = new RejetUtil();
    // il faut un routeur pour les ajouts et donc des maps
    // on boucle sur les premiers coups possibles
    // dailleurs on doit pouvoir modifier aussi le statBrowserDB
    positionManager = new PositionManager(getFolder(dbName) + dbName);
    materialManager = new MaterialManager(dbName);

    globalBrowserStatManager = new GlobalBrowserStatManager(this);
    gameOfAPlayerManager = new GameOfAPlayerManager(this);
    playerStatManager = new PlayerStatManager();
    gameStatManager = new GameStatManager();
    historyManager = new HistoryManager(this);
    gameWhereMapManager = new GameWhereMapManager(dbName);
    globalGameManager = new GlobalGameManager(this);
    playerOfDbManager = new PlayerOfDbManager(this);
    ExecutorService taskExecutor = Executors.newFixedThreadPool(Constants.ALL_FIRST_MOVE.size());
    for (String move : Constants.ALL_FIRST_MOVE) {

      GameManagerRun runEntity = new GameManagerRun(dbName, move, this);
      taskExecutor.execute(runEntity);
    }

    taskExecutor.shutdown();
    try {
      taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException ex) {
      log.error(ex.getMessage());
    }
  }

  public void finish() {
    log.info("DATABASE MANAGER FINSUH >----------" + database.getName());

    globalGameManager.finish();
    globalBrowserStatManager.finish();
    positionManager.finish();
    materialManager.finish();
    gameOfAPlayerManager.finish();
    gameWhereMapManager.finish();
    historyManager.finish();
    playerOfDbManager.finish();
    log.info("DATABASE MANAGER FINSUH <----------");

  }


  public void clear() {
    log.info("Databasemanager.clear");
    globalGameManager.clear();
    globalBrowserStatManager.clear();
    positionManager.clear();
    materialManager.clear();
    gameOfAPlayerManager.clear();
    gameWhereMapManager.clear();
    historyManager.clear();
    playerOfDbManager.clear();
  }

  public String createName() {
    return "DB_" + database.getId();
  }

  public boolean parseMoves2(CommonGame game) {

    OneGameTree ogt = new OneGameTree(game.getMoves());

    //TraceUtils.traceInFile("ogt="+ogt.toString());
    Position position = new Position();
    ItemGameTree courant = ogt.getParent();
    int compteur = 0;
    while (courant != null) {

      ResultInterpretation rer = BoardManager.play(position, courant.getCurrentMove());
      //  TraceUtils.traceInFile("\n "+(compteur+1)+ " je joue : "+courant.getCurrentMove()+" "+position.toString());

      if (rer.isInvalide()) {
        log.error(game.toString());
        return false;
      }
      // tres important ce OU, en effet, ce n est qu a la fin du parsin des coups que l information est complete
      game.setInformationsFaitDeJeu(game.getInformationsFaitDeJeu() | rer.getFaitsdejeu());
      CoupleZobristMaterial czm = position.evaluateZobristAndMaterial();
      game.setLastPosition(czm.getZobrist());

      GlobalManager.getInstance().getDatabaseManager(database.getId()).getPositionManager()
          .add(czm.getZobrist(), game.getId());


      GlobalManager.getInstance().getDatabaseManager(database.getId()).getMaterialManager()
          .add(czm.getMaterial(), game.getId());

      courant = courant.getNextMove();
      compteur++;
    }
    game.setNbcoups(compteur);

    return true;
  }

  public String exportePgn() {

    StringBuilder result = new StringBuilder();
    for (String s : Constants.ALL_FIRST_MOVE) {

      ICommonGameManager g =
          GlobalManager.getInstance().getDatabaseManager(database.getId()).getGlobalGameManager()
              .get(s);
      List<CommonGame> l = g.getGames();
      for (CommonGame c : l) {
        result.append(c.toPGN()).append(System.lineSeparator());
      }
    }
    return result.toString();
  }


  public int importePgn(String emplacement) {

    //    String name = new File(emplacement).getName();
    //   int tot = parser.parseData(parser.readPgnFile(emplacement), this, name);
    int tot = parser.parseDir(new File(emplacement), this);
    if (tot <= 0) {
      log.error("erreur importePgn ");
    }
    log.info("before postUpdateGameAndStat");
    int nbtotal = postUpdateGameAndStat();
    log.info("after postUpdateGameAndStat");
    database.setNbgames(nbtotal);
    database.setLastUpdate(System.currentTimeMillis());
    GlobalManager.getInstance().getDatabaseMap().add(database.getId(), database);
    return tot;
  }

  public List<CommonGame> search(Filter filter) {

    List<CommonGame> listRes = new ArrayList();
    if (filter.isCasGeneral()) {
      ExecutorService taskExecutor = Executors.newFixedThreadPool(Constants.ALL_FIRST_MOVE.size());
      SearchRun[] tab = new SearchRun[Constants.ALL_FIRST_MOVE.size()];
      int i = 0;
      //    log.info(">>>>>>>>>>>recherche +"+filter);
      for (String move : Constants.ALL_FIRST_MOVE) {
        SearchRun runEntity = new SearchRun(this, filter, move);
        tab[i++] = runEntity;
        taskExecutor.execute(runEntity);
      }
      taskExecutor.shutdown();
      try {
        taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      } catch (InterruptedException ex) {
        log.error(ex.getMessage());
      }

      for (i = 0; i < tab.length; i++) {
        //     log.info("avant addDatabaseManager all:" + i + "-" + tab[i]);
        if (tab[i].getResultat() != null) {
          listRes.addAll(tab[i].getResultat());
        }
      }
    }
    // on ajoute ici la recherche par position et par material
    if (filter.isCasOtherCriteria()) {
      //   log.info(">>>>>>>>>>>recherche  isCasOtherCriteria +");
      //     log.info("material is like " + materialValue);
      List<CommonGame> listResultatMaterial = searchMaterial(filter);

      listRes.addAll(listResultatMaterial);
    }
    // @TODO faire des ET ou Ou selon le filtre
    if (filter.isCasPosition()) {
      // si ce qu'il y a dans lpost n'est pas dans liste resultat, on supprime de list resultat
      // si ce qu'il y a dans lpost

      //   log.info(">>>>>>>>>>>recherche  position +" + listeResultat.size() + "---" + lPost.size());

      listRes.addAll(searchPosition(filter));
      //    log.info("<<<<recherche  position +" + listeResultat.size() + "---" + lPost.size());
    }


    listRes = CommonGameManager.removeDuplicate(listRes);
    listRes = CommonGameManager.restrictSize(listRes, ParamConstants.MAX_RESULT);
    //    log.info("<<<<recherche +" + listRes.size());
    return listRes;
  }


  private List<CommonGame> searchMaterial(Filter filter) {
    List<CommonGame> result = new ArrayList<>();
    List<Long> lo =
        GlobalManager.getInstance().getDatabaseManager(database.getId()).getMaterialManager()
            .search(filter);
    for (long l : lo) {
      result.add(getGameById(l));
    }
    System.out.println("search searchMaterial " + lo.size());
    return result;
  }

  private List<CommonGame> searchPosition(Filter filter) {

    List<CommonGame> result = new ArrayList<>();
    List<Long> lo =
        GlobalManager.getInstance().getDatabaseManager(database.getId()).getPositionManager()
            .search(filter);
    for (long l : lo) {
      result.add(getGameById(l));
    }
    System.out.println("search position" + lo.size());
    return result;
  }


  public CommonGame getGameById(long id) {
    String p = getGameWhereMapManager().get(id);
    CommonGame g = getGlobalGameManager().get(p).get(id);
    return g;
  }


  public List<JoueurView> getPlayersWithGames(String param, Pageable paging) {
    log.info(">getPlayersWithGames ");
    List<JoueurView> playersView = new ArrayList<>();
    List<Long> playersId = getPlayerOfDbManager().listIdsOfPlayer();
    log.info(">getPlayersWithGames  playersId " + playersId.size());
    int cpt = 0;
    // FIXME on ne ramene que les n premiers
    // FIXNE avec le param
    for (int i = 0; i < playersId.size() && i < paging.pageSize; i++) {
      CommonPlayer p =
          GlobalManager.getInstance().getCommonPlayerManager().findById(playersId.get(i));
      JoueurView jv = new JoueurView();
      jv.setJoueur(p);

      int nb = getGameOfAPlayerManager().countGameOfAPlayer(p.getIdnumber());

      jv.setNbgames(nb);
      playersView.add(jv);
    }
    log.info(">getPlayersWithGames  playersView " + playersView.size());
    Collections.sort(playersView, new Comparator<JoueurView>() {
      @Override
      public int compare(JoueurView o1, JoueurView o2) {
        return o2.getNbgames() - o1.getNbgames();
      }
    });
    log.info("<getPlayersWithGames " + playersView.size());
    return playersView;

  }


  public void postUpdateGameAndStat(CommonGame game) {

    log.info(">>>postUpdateGameAndStat");

    String s = game.getFirstMove();
    IBrowserStatManager bs = GlobalManager.getInstance().getDatabaseManager(database.getId())
        .getGlobalBrowserStatManager().get(s);    // on ne met à jour qu'un seul game
    // et encore c'est un max, s'il existait deja sans doute qu'on ne doit rien faire
    // FIXME
    List<CommonGame> li = new ArrayList();
    li.add(game);
    bs.browseFirstMove(li);

    database.setLastUpdate(System.currentTimeMillis());
  }


  public int postUpdateGameAndStat() {

    int total = 0;
    long bdId = database.getId();

    for (String firstmove : Constants.ALL_FIRST_MOVE) {


      ICommonGameManager commonGameManager =
          GlobalManager.getInstance().getDatabaseManager(bdId).getGlobalGameManager()
              .get(firstmove);
      commonGameManager.update();
      total += commonGameManager.nbgames();
      // log.info("post update " + g.nbgames());
      IBrowserStatManager bs =
          GlobalManager.getInstance().getDatabaseManager(bdId).getGlobalBrowserStatManager()
              .get(firstmove);
      if (bs != null) {
        bs.clear();
        List<CommonGame> liste = commonGameManager.getGames();
        //  log.info(firstmove+" nouvelles parties to browse:-" + liste.size()+ "/"+commonGameManager.getGames().size());
        bs.browseFirstMove(liste);
      }


    }
    database.setLastUpdate(System.currentTimeMillis());
    return total;
  }


  public long getLastUpdate() {
    return database.getLastUpdate();
  }


  public CommonGame upsert(CommonGame item, DBOperation operation) {

    //  log.info("a-0 ajout de :" + item);
    //       log.info("findOrAdd ou met à jour : " + operation + "-" + item);
    ICommonGameManager gameManager =
        GlobalManager.getInstance().getDatabaseManager(database.getId()).getGlobalGameManager()
            .get(item.getFirstMove());
    if (gameManager == null) {
      // FIXME exception
      log.error(" error " + item);
      return item;
    }
    //   log.info("a-1) ajout de :" + item);
    switch (operation) {
      case AJOUT:
      case UPDATE:

        if (item.getId() == 0) {
          item.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
        }
        item.setLastUpdate(System.currentTimeMillis());
        if (parseMoves2(item)) {
          gameManager.upsert(item, operation);
          GlobalManager.getInstance().getDatabaseManager(database.getId()).getGameOfAPlayerManager()
              .ajoute(item.getBlackFideId(), item.getId());
          GlobalManager.getInstance().getDatabaseManager(database.getId()).getGameOfAPlayerManager()
              .ajoute(item.getWhiteFideId(), item.getId());
          //    log.info("ajout ok"+item);
        } else {
          log.error(item.toString());
          rejetUtil.ecritRejet(item, "INT");
        }

        break;

      case DUPLICATE:
        CommonGame cop = item.duplicate();
        cop.setLastUpdate(System.currentTimeMillis());
        cop.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
        if (parseMoves2(cop)) {
          gameManager.upsert(cop, operation);
        }
        break;
      case DELETE:
        item.setDeleted(true);
        item.setLastUpdate(System.currentTimeMillis());
        gameManager.upsert(item, operation);
        log.info("efface ok");
        break;
      case UNDELETE:
        item.setDeleted(false);
        item.setLastUpdate(System.currentTimeMillis());
        gameManager.upsert(item, operation);
        break;

      default:
    }

    return item;
  }


  public boolean optimiser(boolean withDeleted, boolean deleteDoublon) {
    //sauvegarde les fichiers

    log.info(">>>optimiser:" + withDeleted + "-" + deleteDoublon);

    IDatabaseManager dm = GlobalManager.getInstance().getDatabaseManager(database.getId());
    List<CommonGame> listAGarder = new ArrayList();
    for (String s : Constants.ALL_FIRST_MOVE) {
      ICommonGameManager gameManager = dm.getGlobalGameManager().get(s);

      List<CommonGame> allGames = gameManager.getGames();
      int compteur = 0;
      for (CommonGame game : allGames) {
        // log.info("game a optimiser:" + game);
        boolean toRemove = false;
        if (withDeleted) {
          if (game.isDeleted()) {
            toRemove = true;
          }
        }
        if (deleteDoublon && compteur > 0) {
          List<CommonGame> sousList = allGames.subList(0, compteur);
          for (CommonGame gameBefore : sousList) {
            if (game.isDoublon(gameBefore)) {
              toRemove = true;
              break;
            }
          }
        }

        if (!toRemove) {

          listAGarder.add(game);

        }
        compteur++;
        //   log.info("on a vu:" + compteur);
      }
    } // fin for sur s
    clear();
    //    log.info("optimiser apres init:");

    //save all
    for (CommonGame g : listAGarder) {
      //    log.info("on update:" + g);
      upsert(g, DBOperation.AJOUT);
    }
    postUpdateGameAndStat();

    log.info("optimisation terminee");
    return true;
  }


  public void delete() {
    log.info("delete:" + database.getId());
    // FIXME GlobalManager.getInstance().addToDelete(database.getId());
  }


  public List<StatBrowserView> getBrowseData(List<String> movesAlreadyPlayed) {
    List<StatBrowserView> all = new ArrayList();
    // si pas de coups joues : on boucle
    if (movesAlreadyPlayed.isEmpty()) {
      for (String s : Constants.ALL_FIRST_MOVE) {
        //log.info("getBrowseData:" + s+"  "+database().getId());
        IBrowserStatManager bsm = GlobalManager.getInstance().getDatabaseManager(database.getId())
            .getGlobalBrowserStatManager().get(s);       // log.info("s="+s+" "+bsm);
        //    log.info("getBrowseData:"+bsm.toString());
        if (bsm == null) {
          log.error(database.getId() + "bsm null" + s);
          continue;
        }
        List<StatBrowserView> l = bsm.getBrowseData(this, movesAlreadyPlayed);
        //   log.info(s+":getBrowseData:" + l.size());
        if (l != null) {
          all.addAll(l);
        }
      }
    } else {
      all.addAll(GlobalManager.getInstance().getDatabaseManager(database.getId())
          .getGlobalBrowserStatManager().get(movesAlreadyPlayed.get(0))
          .getBrowseData(this, movesAlreadyPlayed));
    }
    //   log.info("getBRowseData : " + all.size());
    return all;
  }


  public StatJoueurView getStatJoueur(CommonPlayer player) {
    IPlayerStatManager psm =
        GlobalManager.getInstance().getDatabaseManager(database.getId()).getPlayerStatManager();
    return psm.getStatJoueur(this, player);
  }


  public StatGame getStatGame(List<CommonGame> list) {
    IGameStatManager psm =
        GlobalManager.getInstance().getDatabaseManager(database.getId()).getGameStatManager();
    return psm.getStatGame(list);
  }

  @Override
  public IGlobalGameManager getGlobalGameManager() {
    return globalGameManager;
  }

  @Override
  public IPositionManager getPositionManager() {
    return positionManager;
  }

  @Override
  public IMaterialManager getMaterialManager() {
    return materialManager;
  }

  @Override
  public IPlayerOfDbManager getPlayerOfDbManager() {
    return playerOfDbManager;
  }

  @Override
  public IGlobalBrowserStatManager getGlobalBrowserStatManager() {
    return globalBrowserStatManager;
  }

  @Override
  public IPlayerStatManager getPlayerStatManager() {
    return playerStatManager;
  }

  @Override
  public IGameStatManager getGameStatManager() {
    return gameStatManager;
  }

  @Override
  public IGameWhereMapManager getGameWhereMapManager() {
    return gameWhereMapManager;
  }

  @Override
  public IGameOfAPlayerManager getGameOfAPlayerManager() {
    return gameOfAPlayerManager;
  }

  @Override
  public IHistoryManager getHistoryManager() {
    return historyManager;
  }

}
