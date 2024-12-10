package com.xoff.chessvger.parser;


import com.xoff.chessvger.parser.game.RunGameParser;
import com.xoff.chessvger.parser.player.RunPlayerParser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Main {


  // localhost
  private static String dbhost = "localhost:19092"; //"db_chessvger"

  public static String getDBHost() {
    return dbhost;
  }

  public static void main(String[] args) {

    if (args.length > 0) {
      System.out.println("Main!" + args[0]);
      dbhost = "db_chessvger";
    }

    try (Jedis jedis = new Jedis("redis", 6379)) {
      // Define a new JedisPubSub instance to handle messages
      JedisPubSub pubSub = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
          System.out.println("Received message from channel " + channel + ": " + message);
        }
      };
      // Subscribe to the "my-channel" channel
      jedis.subscribe(pubSub, "pubsub:queue");
    }

    Runnable[] runnables = new Runnable[] {new RunGameParser(), new RunPlayerParser()};

    for (Runnable r : runnables) {
      Thread thread = new Thread(r);
      thread.start();
    }

  }

}
