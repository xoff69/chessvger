package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.database.IDatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.GlobalGameManager;
import com.xoff.chessvger.chess.game.ItemGameTree;
import com.xoff.chessvger.chess.game.OneGameTree;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.exception.WebException;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.ui.web.mapper.GameMapper;
import com.xoff.chessvger.ui.web.navigation.DBOpened;
import com.xoff.chessvger.ui.web.navigation.GameOpened;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.ui.web.navigation.Tab;
import com.xoff.chessvger.view.CoupleFenMoveId;
import com.xoff.chessvger.view.CoupleLongView;
import com.xoff.chessvger.view.GameView;
import com.xoff.chessvger.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameServiceImpl implements IGameService {

  @Autowired
  Navigation navigation;
  @Autowired
  GameMapper gameMapper;
  public boolean gameFlip(long bdId, long gameId){
    IDatabaseManager dm = GlobalManager.getInstance().getDatabaseManager(bdId);
    String w = dm.getGameWhereMapManager().get(gameId);
    CommonGame cg = dm.getGlobalGameManager().get(w).get(gameId);
    cg.getMetaCommonGame().setFlipBoard(!cg.getMetaCommonGame().isFlipBoard());
    dm.upsert(cg, DBOperation.UPDATE);
    return true;
  }

  public PageView<CommonGame> managePage(Pageable paging, long bdId) {
    Database database = GlobalManager.getInstance().getDatabaseMap().get(bdId);
    // FIXME verifier que ce n est pas deja present
    log.info("service game manage page");
    // FIXME on devrait pas faire un new DM
    DatabaseManager dm = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(dm);
    PageView<CommonGame> pageGameView =
        GlobalGameManager.computePaging(dm.getGlobalGameManager().getAllGamesReadOnly(), paging);
    return pageGameView;
  }

  public void gameOpen(long bdId, long gameId) {

    // TODO utiliser un cache pour ca
    log.info(" du filter " + navigation.getBdFilters().size());


    IDatabaseManager dm = GlobalManager.getInstance().getDatabaseManager(bdId);
    String w = dm.getGameWhereMapManager().get(gameId);
    CommonGame cg = dm.getGlobalGameManager().get(w).get(gameId);
    cg.getMetaCommonGame().setLastSeen(System.currentTimeMillis());
    dm.upsert(cg, DBOperation.UPDATE);

    GameView g = gameMapper.commonGame2GameView(cg);
    navigation.getCacheGameView().put(bdId + "_" + gameId, g);

    dm.getHistoryManager().add(gameId);

    String title = g.getJoueurBlanc() + "/" + g.getJoueurNoir() + ":" + g.getResultat();
    Tab t = navigation.appendTabGame(bdId, gameId, title);

    navigation.setLevel1Tab(t.getId());
    navigation.setLevel2Tab(t.getFils().get(t.getFils().size() - 1).getId());

    DBOpened dbOpened = navigation.getDbOpeneds().get(bdId);
    GameOpened gameOpened = new GameOpened();
    gameOpened.setGameId(gameId);
    dbOpened.getGameOpeneds().put(gameId, gameOpened);

    log.info("navigation gameOpen =" + navigation);
    log.info("navigation gameOpen =" + navigation.getCacheGameView().size() + "   " + bdId + "_" +
        gameId);

  }

  public PageView<CommonGame> managePage(Pageable paging, long bdId, Filter filter) {

    Database database = GlobalManager.getInstance().getDatabaseMap().get(bdId);
    DatabaseManager dm = new DatabaseManager(database);
    // FIXME on devrait pas faire un new DM
    GlobalManager.getInstance().addDatabaseManager(dm);
    List<CommonGame> commonGames = dm.search(filter);
    return GlobalGameManager.computePaging(commonGames, paging);

  }

  public CoupleLongView gameClose(long bdId, long gameId) {

    // faire appel server pour remover le tab et le opened game + sauver le game
    DBOpened dbOpened = navigation.getDbOpeneds().get(bdId);
    log.info("game close " + dbOpened.toString());
    Tab bdTab = navigation.getByObjectKey(bdId);
    //TODO   GameOpened gameOpened = dbOpened.getGameOpeneds().remove(gameId);
    Tab tab = navigation.removeTabGame(bdId, gameId);
    CoupleLongView coupleView = new CoupleLongView();
    coupleView.setTabfirst(bdTab.getFils().get(0).getId());
    coupleView.setTabtoClose(tab.getId());
    // TODO faire une methode clean + la queue
    return coupleView;
  }

  public CoupleFenMoveId gameGetFenNavigation(Long bdId, Long gameId, int moveId, int where) {
// renoie le fen

    GlobalManager.getInstance().getCallStatManager().appendStat("FEN_GAME");
    log.info("gameGetFenNavigation " + bdId + "_" + gameId);
    // renvoie le moe id current ?
    GameView gameView = navigation.getCacheGameView().get(bdId + "_" + gameId);
    log.info("gameGetFenNavigation " + moveId);
    OneGameTree oneGameTree = gameView.getOneGameTree();
    CoupleFenMoveId coupleView = new CoupleFenMoveId();


    switch (where) {
      case 0:
        coupleView.setMoveId("0");
        coupleView.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        //TODO constante
        break;
      case -1:
        if (moveId == 0) {
          throw new WebException("Invalid MoveId =0");
        }
        ItemGameTree beforeItemGameTree = (oneGameTree.findById(moveId)).getPreviousMove();
        if (beforeItemGameTree == null) {
          throw new WebException("Invalid MoveId where before");
        }
        coupleView.setMoveId(String.valueOf(beforeItemGameTree.getId()));
        coupleView.setFen(beforeItemGameTree.getFen());
        break;
      case 1:
        ItemGameTree next =
            (moveId == 0) ? oneGameTree.getParent() : (oneGameTree.findById(moveId)).getNextMove();

        if (next == null) {
          throw new WebException("Invalid next MoveId");
        }
        coupleView.setMoveId(String.valueOf(next.getId()));
        coupleView.setFen(next.getFen());

        break;
      case 2:
        if (moveId == 0) {
          moveId = oneGameTree.getParent().getId();
          // le premier

        }
        ItemGameTree lastItemGameTree = oneGameTree.findById(moveId);
        if (lastItemGameTree == null) {
          throw new WebException("Invalid next MoveId - toEnd");
        }
        while (lastItemGameTree.getNextMove() != null) {
          lastItemGameTree = lastItemGameTree.getNextMove();
        }
        coupleView.setMoveId(String.valueOf(lastItemGameTree.getId()));
        coupleView.setFen(lastItemGameTree.getFen());
        break;
      default:
        throw new WebException("Invalid where");
    }

    log.info(coupleView.toString());
    return coupleView;
  }
}
