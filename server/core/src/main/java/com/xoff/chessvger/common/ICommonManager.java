package com.xoff.chessvger.common;

import java.io.Serializable;
import java.util.List;

public interface ICommonManager<K extends Serializable, T extends CommonModel> {

  List<T> findAll();

  void clear();

  void deleteById(K key);

  void finish();

  T get(K key);

  T create(T value);

  void update(T value);

  List<T> saveAll(List<T> list);
}
