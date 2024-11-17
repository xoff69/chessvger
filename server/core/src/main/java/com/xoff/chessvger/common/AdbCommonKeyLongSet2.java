package com.xoff.chessvger.common;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerArrayTuple;
@Slf4j
public class AdbCommonKeyLongSet2 {
  private final NavigableSet<Object[]> map;
  protected DB db;
  private String filename;

  public AdbCommonKeyLongSet2(String filename) {
    this.filename=filename;
    db = AdbCommon.makeIt(filename);
    map = makeMap();
  }


  public int sizeSet(Long key) {
    Set subset = map.subSet(new Long[] {key},         // lower interval bound
        new Long[] {key, null});  // upper interval bound, null is positive infinity

    return subset.size();
  }

  public void add(Long key, Long value) {
    map.add(new Long[] {key, value});
  }

  private NavigableSet<Object[]> makeMap() {
    NavigableSet<Object[]> multimap = db.treeSet("mapADBCommonKeyLongSet2")
        //set tuple serializer
        .serializer(new SerializerArrayTuple(Serializer.LONG, Serializer.LONG)).counterEnable()
        .counterEnable().counterEnable() // pourquoi 3 ?
        .createOrOpen();
    return multimap;
  }

  public List<Long> getValuesForKey(Long key) {
    Set subset = map.subSet(new Object[] {key},         // lower interval bound
        new Object[] {key, null});  // upper interval bound, null is positive infinity

    Iterator it = subset.iterator();
    List<Long> l = new ArrayList<>();
    while (it.hasNext()) {
      Object[] o = (Object[]) it.next();
      l.add((Long) o[1]);
    }

    return l;

  }

  public void clear() {
    db.commit();
  }


  public void commit() {

    if (size()>0)
      log.info(" AdbCommonKeyLongSet2 commit:"+filename+":"+size());
    db.commit();
  }

  public int size() {
    return map.size();
  }

  public List<Long> keyset() {
    //System.out.println(" keyset "+size());
    Iterator it = map.iterator();
    List<Long> l = new ArrayList();
    while (it.hasNext()) {
      Object[] o = (Object[]) it.next();
      l.add((Long) o[0]);
    }

    return l;
  }


  public void remove(Long[] key) {
    map.remove(key);
  }

}
