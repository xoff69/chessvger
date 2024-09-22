package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.favorite.Favorite;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.mapper.GameMapper;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.ui.web.view.PageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FavoriteServiceImpl implements IFavoriteService {

  @Autowired
  Navigation navigation;
  @Autowired
  GameMapper gameMapper;

  public boolean updateFavorite(long bdId, long gameId, long userId) {

    GlobalManager.getInstance().getCallStatManager().appendStat("FAVORITES");
    log.info("updateFavorite");
    Favorite favorite = new Favorite();
    favorite.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    favorite.setBdId(bdId);
    favorite.setIdUser(userId);

    favorite.setIdGame(gameId);
    // TODO gerer les addDatabaseManager/delete
    GlobalManager.getInstance().getFavoriteManager().add(favorite.getId(), favorite);
    return true;
  }

  public PageView<CommonGame> managePage(Pageable paging, long userId) {

    GlobalManager.getInstance().getCallStatManager().appendStat("FAVORITES");
    PageView pageGameView = Page.compute(paging, GameMapper.INSTANCE.mapListEntity2Dto(
        GlobalManager.getInstance().getFavoriteManager().listFavorite(userId)));
    log.info(" games favorite taille =" + pageGameView.getItems().size());
    return pageGameView;
  }
}
