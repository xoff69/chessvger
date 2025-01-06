/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

@Slf4j
public class PlayerOfDbMap {
  // TODO mutualiser avec HistoryMap
  private final DB db;
  private final Set<Long> setToSave;
  private List<Long> workList;

  public PlayerOfDbMap(String dbName) {
    super();

    db = DBMaker.fileDB(
            DatabaseManager.getFolder(dbName) + dbName + "PlayerOfDbMap" + Constants.MAP_SFX)
        .checksumHeaderBypass().fileLockDisable().closeOnJvmShutdown().fileMmapEnable().make();
    setToSave = db.treeSet("PlayerOfDbMap", Serializer.LONG).createOrOpen();
    workList = new ArrayList<>(setToSave);
  }

  public void clear() {
    workList.clear();
    setToSave.clear();
    db.commit();

  }

  public void commit() {
    setToSave.clear();
    log.info("PlayerOfDbMap.commit:" + workList.size());
    setToSave.addAll(workList);
    log.info("PlayerOfDbMap.commit after:" + setToSave.size());
    db.commit();
  }

  public int size() {
    return setToSave.size();
  }

  public List<Long> list() {
    workList = setToSave.stream().toList();
    return workList;

  }

  public void add(Long value) {
    workList.add(value);

  }

}
