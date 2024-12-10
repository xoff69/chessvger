package com.xoff.chessvger.parser.reconciliation;

import redis.clients.jedis.Jedis;
// https://www.baeldung.com/spring-data-redis-pub-sub
// https://apurvsheth.medium.com/spring-boot-redis-connectivity-with-docker-kubernetes-9ab0965f32b0
public class ReconciliationManager {
  private static ReconciliationManager _instance = null;
  private final Jedis jedis;

  public static void main(final String[] args) {
    getInstance().getJedis().set("key", "value");
System.out.println(getInstance().getJedis().get("key"));
  }

  private ReconciliationManager() {
    jedis = new Jedis("localhost");
  }

  public static ReconciliationManager getInstance() {
    if (_instance == null) {
      _instance = new ReconciliationManager();

    }
    return _instance;
  }

  public static void update(long gameId, ReconciliationType reconciliationType) {

    String reconciliation = getInstance().getJedis().get(String.valueOf(gameId));
    reconciliation = "hello " + gameId + ":" + reconciliation;
    getInstance().getJedis().set(String.valueOf(gameId), reconciliation);

  }

  private Jedis getJedis() {
    return jedis;
  }

}
