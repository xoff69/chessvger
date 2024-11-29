package com.xoff.chessvger.queues.reconciliation;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
public class ReconciliationManager {
  private static ReconciliationManager _instance = null;
  private  Jedis jedis;
  private ReconciliationManager(){
    jedis = new Jedis();
  }
  private Jedis getJedis(){return jedis;}
  public static ReconciliationManager getInstance() {
    if (_instance == null) {
      _instance = new ReconciliationManager();

    }
    return _instance;
  }
  public static void update(long gameId, ReconciliationType reconciliationType) {

    String reconciliation=getInstance().getJedis().get(String.valueOf(gameId));
    reconciliation="hello "+gameId+":"+reconciliation;
    getInstance().getJedis().set(String.valueOf(gameId),reconciliation);

  }

}
