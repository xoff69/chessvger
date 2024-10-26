package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.database.IDatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.ui.web.view.PageView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class PlayerServiceImpl implements IPlayerService {


  public PageView managePage(Pageable paging, long bdId) {

    GlobalManager.getInstance().getCallStatManager().appendStat("PLAYER.managePage");
    IDatabaseManager databaseManager=GlobalManager.getInstance().getDatabaseManager(bdId);
    if (databaseManager==null) {
      log.error("PlayerService dm null"+bdId);
     throw new RuntimeException("bdId="+bdId);
    }
    return Page.compute(paging, databaseManager.getPlayersWithGames(StringUtils.EMPTY,paging));
  }
}
