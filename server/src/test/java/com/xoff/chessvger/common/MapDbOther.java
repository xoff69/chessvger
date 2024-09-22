package com.xoff.chessvger.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

@Tag("IT")
public class MapDbOther {
  @Test
  @DisplayName("testAtomic")
  public void testAtomic() {
    DB db = DBMaker.memoryDB().make();
    Atomic.Integer atomicInteger = db.atomicInteger("atomicInteger").createOrOpen();
    int a=atomicInteger.incrementAndGet();
    a=atomicInteger.incrementAndGet();
    assertEquals(2,a);
  }
  @Test
  @DisplayName("testList")
  public void testList() {
    DB db = DBMaker.memoryDB().make();
    List<String> list = db.indexTreeList("some_list", Serializer.STRING).createOrOpen();
    list.add("a");
    list.add("b");
    db.commit();
    assertEquals(2,list.size());
  }
}
