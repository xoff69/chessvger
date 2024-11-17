package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameOfAStatMap extends AdbCommonKeyLong<String> {


  private final HashMap<Long, List<Long>> workmap;


  public GameOfAStatMap(String premier, String dbname, boolean inMemory) {

    super(DatabaseManager.getFolder(dbname) + dbname + "GameOfAStatMap" + premier + "GOS" +
        Constants.MAP_SFX, inMemory);
    workmap = new HashMap();
    //  log.info("--->load GameOfAStatMap");
    for (Map.Entry<Long, String> entry : entrySet()) {
      String str = entry.getValue();
      String[] stra = str.split(Constants.MAP_SEP);
      List<Long> l = new ArrayList();
      for (String sx : stra) {
        l.add(Long.parseLong(sx));
      }
      workmap.put(entry.getKey(), l);
    }
  }


  public void commit() {
    // log.info("GameOfAStatMap.commit:");
    for (Map.Entry<Long, List<Long>> entry : workmap.entrySet()) {
      StringBuilder sb = new StringBuilder();
      List<Long> l = entry.getValue();
      for (Long s : l) {
        sb.append(s).append(Constants.MAP_SEP);
      }
      String values = sb.toString();
      add(entry.getKey(), values);
    }
    workmap.clear();
    db.close();
  }

  public List<Long> getValuesForKey(long pid) {
    return workmap.get(pid);

  }

  /**
   * ajoute a la map @FIXME
   */
  public void add(long key, long value) {
    //1582 s,nb appel=1605885,ratio=98 %
    // Methode=gasm,temps total=29533 soit 29 s,nb appel=1605885,ratio=63 %
    //  TimeMeter.startMesure("gasm");
    List<Long> l = workmap.get(key);
    if (l == null) {
      l = new ArrayList();
      l.add(value);
      workmap.put(key, l);
    } else {
      if (!l.contains(value)) {
        l.add(value);
      }
    }

  }

  public List<Long> search(Filter filter) {
    return getValuesForKey(filter.getZobrist());
  }
}
