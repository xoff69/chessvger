package com.xoff.chessvger.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NavigableSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerArrayTuple;

@Tag("IT")
public class MultimapTest {

  @Test
  @DisplayName("testMultimapStringInt")
  public void testMultimapStringInt() {
    DB db = DBMaker.memoryDB().make();
    // initialize multimap: Map<String,List<Integer>>
    NavigableSet<Object[]> multimap = db.treeSet("towns")
        //set tuple serializer
        .serializer(new SerializerArrayTuple(Serializer.STRING, Serializer.INTEGER)).counterEnable()
        .counterEnable().counterEnable().createOrOpen();

// populate, key is first component in tuple (array), value is second
    multimap.add(new Object[] {"John", 1});
    multimap.add(new Object[] {"John", 2});
    multimap.add(new Object[] {"Anna", 1});

// print all values associated with John:
    Set johnSubset = multimap.subSet(new Object[] {"John"},         // lower interval bound
        new Object[] {"John", null});  // upper interval bound, null is positive infinity

    assertEquals(johnSubset.size(), 2);

  }

  @Test
  @DisplayName("testMultimapLongLongs")
  public void testMultimapLongLongs() {
    DB db = DBMaker.memoryDB().make();
    // initialize multimap: Map<String,List<Integer>>
    NavigableSet<Object[]> multimap = db.treeSet("ll")
        //set tuple serializer
        .serializer(new SerializerArrayTuple(Serializer.LONG, Serializer.LONG)).counterEnable()
        .counterEnable().counterEnable() // pourquoi 3 ?
        .createOrOpen();

// populate, key is first component in tuple (array), value is second
    multimap.add(new Object[] {1L, 1L});
    multimap.add(new Object[] {1L, 2L});
    multimap.add(new Object[] {10L, 1L});

// print all values associated with John:
    Set johnSubset = multimap.subSet(new Object[] {1L},         // lower interval bound
        new Object[] {1L, null});  // upper interval bound, null is positive infinity

    assertEquals(johnSubset.size(), 2);

  }

  @Test
  @DisplayName("testMultimapLongLongsPerf")
  public void testMultimapLongLongsPerf() {
    DB db = DBMaker.memoryDB().make();
    // initialize multimap: Map<String,List<Integer>>
    NavigableSet<Object[]> multimap = db.treeSet("ll")
        //set tuple serializer
        .serializer(new SerializerArrayTuple(Serializer.LONG, Serializer.LONG)).counterEnable()
        .counterEnable().counterEnable() // pourquoi 3 ?
        .createOrOpen();
    long nb = 1_000L;
// populate, key is first component in tuple (array), value is second
    for (long l = 0L; l < nb; l++) {
      multimap.add(new Object[] {l % 5, l});
    }
// print all values associated with John:
    Set johnSubset = multimap.subSet(new Object[] {1L},         // lower interval bound
        new Object[] {1L, null});  // upper interval bound, null is positive infinity
    System.out.println("taille :" + johnSubset.size());
    org.junit.jupiter.api.Assertions.assertNotNull(johnSubset);
    assertEquals(johnSubset.size(), nb / 5);
  }
}
