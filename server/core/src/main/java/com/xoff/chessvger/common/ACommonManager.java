package com.xoff.chessvger.common;


import java.io.Serializable;
import java.util.List;

public abstract class ACommonManager<K extends Serializable, T extends CommonModel>
    implements ICommonManager<K, T> {

  private final AdbCommonKeyLong map;

  public ACommonManager(String filename) {
    this.map = new AdbCommonKeyLong<T>(filename);
  }

  public List<T> findAll() {
    return map.list();
  }

  public void clear() {
    map.clear();
  }

  public void deleteById(K key) {
    map.remove(key);
  }

  public void finish() {
    map.commit();
  }

  public T get(K key) {
    return (T) map.get(key);
  }

  public T create(T value) {
    value.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    map.add(value.getId(), value);
    return value;
  }

  public void update(T value) {
    map.add(value.getId(), value);
  }

  public List<T> saveAll(List<T> list) {
    for (T t : list) {
      update(t);
    }
    return list;
  }
}
