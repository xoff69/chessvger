/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xoff.chessvger.chess.history;

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
public class HistoryMap  {

  private DB db;
  private Set<Long> list;

  public HistoryMap(String dbName) {
    super();

    db= DBMaker.fileDB(DatabaseManager.getFolder(dbName) + dbName + "HistoryMap" + Constants.MAP_SFX).checksumHeaderBypass().fileLockDisable().closeOnJvmShutdown()
        .fileMmapEnable().make();
    list = db.treeSet("HistoryMap", Serializer.LONG).createOrOpen();
  }
  public void clear() {
    list.clear();
    db.commit();

  }


  public void commit() {
    db.commit();
  }

  public int size() {
    return list.size();
  }

  public List<Long> list() {
    return new ArrayList(list.stream().toList());

  }

  public void add(Long value) {
    list.add (value);

  }

}
