package com.xoff.chessvger.queues.reconciliation;

import redis.clients.jedis.Jedis;

public class ReconciliationManager {
  private static ReconciliationManager _instance = null;
  private final Jedis jedis;

  private ReconciliationManager() {
    jedis = new Jedis();
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
