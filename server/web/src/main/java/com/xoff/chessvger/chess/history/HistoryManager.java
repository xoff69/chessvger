package com.xoff.chessvger.chess.history;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.ICommonGameManager;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.regex.History;

@Slf4j
public class HistoryManager implements IHistoryManager {

  private final HistoryMap map;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We want that")
  private final DatabaseManager databaseManager;


  public HistoryManager(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
    map = new HistoryMap(databaseManager.createName());

  }

  public List<CommonGame> listHistory() {
    List<CommonGame> lc = new ArrayList();
    List<Long> lh = map.list();
    log.info("list history ");
    lh.forEach((h) -> {
      // on cherche dans quel game manager se trouve la partie
      String premier = databaseManager.getGameWhereMapManager().get(h);
      ICommonGameManager gameDB = databaseManager.getGlobalGameManager().get(premier);

      lc.add(gameDB.get(h));

    });
    log.info("list history " + lc.size());
    return lc;

  }

  public void clear() {
    map.clear();
  }

  public void add( Long value) {
    map.add(value);
  }


  public void finish() {
    map.commit();
  }

}
