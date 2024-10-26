package com.xoff.chessvger.common;

import java.io.Serializable;
import java.util.List;

public interface ICommonManager<K extends Serializable, T extends CommonModel> {

  public List<T> findAll();

  public void clear();

  public void deleteById(K key);

  public void finish();

  public T get(K key);

  public T create(T value);

  public void update(T value);

  public List<T> saveAll(List<T> list);
}
