package com.xoff.chessvger.ui.service;
import org.springframework.data.domain.Limit;
import com.xoff.chessvger.chess.database.IDatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.repository.CommonPlayerEntity;
import com.xoff.chessvger.repository.PlayerRepository;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class PlayerServiceImpl implements IPlayerService {

  @Autowired
  private PlayerRepository playerRepository;

  public List<CommonPlayerEntity> findAll(){
    // @TODO limiter les resultats
    return (List<CommonPlayerEntity>) playerRepository.findAll();
  }
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
