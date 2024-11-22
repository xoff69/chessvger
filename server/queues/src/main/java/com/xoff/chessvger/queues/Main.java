package com.xoff.chessvger.queues;

import java.util.Map;
import redis.clients.jedis.Jedis;

public class Main {
  public static void main(String[] args) {
    System.out.println("hello");

    Jedis jedis = new Jedis();
    jedis.set("events/city/rome", "32,15,223,828");
    String cachedResponse = jedis.get("events/city/rome");
    System.out.println("hello"+cachedResponse);

    jedis.hset("user#1", "name", "Peter");
    jedis.hset("user#1", "job", "politician");

    String name = jedis.hget("user#1", "name");

    Map<String, String> fields = jedis.hgetAll("user#1");
    String job = fields.get("job");
    System.out.println(name+":"+job);

  }

}
