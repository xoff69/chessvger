package com.xoff.chessvger.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

@Slf4j
public abstract class AdbCommon<K extends Serializable, T extends Serializable> {

  private static final int COMMONADB_INCREMENT = 1048576;

  private static final int COMMONADB_INITIAL_SIZE_MAP = 1048576;

  @Getter
  private final String filename;


  private final HTreeMap<K, T> treeMap;

  protected DB db;

  public AdbCommon(String filename, boolean inMemory) {
    // taille initiale  10 mo aussi4
    this.filename = filename;
    if (!inMemory) {
      // non !!!db = DBMaker.fileDB(filename).transactionEnable().fileLockDisable().
      // fileMmapEnable().checksumHeaderBypass().closeOnJvmShutdown().
      // allocateStartSize(INITIAL_SIZE_MAP).allocateIncrement(INCREMENT).make();
      db = makeIt(filename);
    } else {
      db = DBMaker.memoryDB().allocateStartSize(COMMONADB_INITIAL_SIZE_MAP)
          .allocateIncrement(COMMONADB_INCREMENT).make();
    }
    treeMap = makeMap();
  }

  public AdbCommon(String filename) {
    this.filename = filename;
    db = makeIt(filename);
    treeMap = makeMap();
  }

  public static DB makeIt(String filename) {
    //log.info("ADBCommon ->" + filename);
    return DBMaker.fileDB(filename).checksumHeaderBypass().fileLockDisable().closeOnJvmShutdown()
        .fileMmapEnable().allocateStartSize(COMMONADB_INITIAL_SIZE_MAP)
        .allocateIncrement(COMMONADB_INCREMENT).make();

  }

  public abstract HTreeMap<K, T> makeMap();

  public void clear() {
    treeMap.clear();
    db.commit();

  }


  public void commit() {
    if (size()>0)
    log.info(" AdbCommon commit:"+filename+":"+size());
    db.commit();
  }

  public int size() {
    return treeMap.size();
  }

  public List<T> list() {
    return new ArrayList(treeMap.values());

  }

  public void add(K key, T value) {
    treeMap.put(key, value);

  }


  public T get(K pid) {
    return treeMap.get(pid);
  }

  public Set<Map.Entry<K, T>> entrySet() {
    return treeMap.entrySet();
  }

  public List<K> keyset() {
    return new ArrayList(treeMap.keySet());
  }

  public void remove(K key) {
    treeMap.remove(key);
  }


  @Deprecated
  public T findById(K pid) {
    return get(pid);

  }
}
