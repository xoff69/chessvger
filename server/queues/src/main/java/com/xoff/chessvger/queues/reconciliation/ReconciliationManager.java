package com.xoff.chessvger.queues.reconciliation;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class ReconciliationManager {
  public static void update(long gameId, ReconciliationType reconciliationType) {
    Jedis jedis = new Jedis();
    jedis.set("events/city/rome", "32,15,223,828");
    String cachedResponse = jedis.get("events/city/rome");
    System.out.println("hello" + cachedResponse);

    jedis.hset("user#1", "name", "Peter");
    jedis.hset("user#1", "job", "politician");

    String name = jedis.hget("user#1", "name");

    Map<String, String> fields = jedis.hgetAll("user#1");
    String job = fields.get("job");
    System.out.println(name + ":" + job);
  }

}
