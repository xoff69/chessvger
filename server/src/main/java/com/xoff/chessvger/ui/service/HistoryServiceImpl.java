package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
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
public class HistoryServiceImpl implements IHistoryService {

  @Autowired
  Navigation navigation;
  @Autowired
  GameMapper gameMapper;

  public PageView<CommonGame> managePage(Pageable paging, long bdId) {

    GlobalManager.getInstance().getCallStatManager().appendStat("HISTORY.MANAGEPAGE");
    Database database = GlobalManager.getInstance().getDatabaseMap().get(bdId);

    // FIXME faudrait appeler tout ca dans un service
    DatabaseManager dm = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(dm);
    PageView pageGameView = Page.compute(paging,
        GameMapper.INSTANCE.mapListEntity2Dto(dm.getHistoryManager().listHistory()));
    log.info(" gamesHistory taille =" + pageGameView.getItems().size());
    return pageGameView;
  }
}
