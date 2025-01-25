package com.xoff.chessvger.backoffice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.backoffice.environnement.RunInitSystem;
import com.xoff.chessvger.backoffice.environnement.RunInitTenant;
import com.xoff.chessvger.backoffice.game.RunGameParser;
import com.xoff.chessvger.backoffice.player.RunPlayerParser;
import com.xoff.chessvger.backoffice.util.Metrics;
import com.xoff.chessvger.common.UserTenant;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.topic.Topic;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Main {

  // TODO check l existence des folders dans data
  // localhost
  private static String dbhost = "db_chessvger";

  public static String getDBHost() {
    return dbhost;
  }

  private static void checkEnvironment() {
    System.out.println("Checking environment...");
    // TODO
    Thread thread = new Thread(new RunInitSystem());
    thread.start();
  }

  public static void main(String[] args) throws Exception {

    System.out.println("start backoffice");

    if (args.length > 0&&"local".equals(args[0])) {

      System.out.println("Local Main!" + args[0]);
      dbhost = "localhost";
    }
    checkEnvironment();
    try {
      Prometheus.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
      // for test
      /*
      Thread thread = new Thread(new RunInitSystem());
      thread.start();
*/
    /*
      UserTenant userTenant = new UserTenant();
      // TODO demo
      userTenant.setTenantName("demo2");
      userTenant.setPassword("demo");
      userTenant.setIsAdmin(false);
      userTenant.setLogin("demo2");
      Thread thread = new Thread(new RunInitTenant(userTenant));
      thread.start();
 */
    ObjectMapper objectMapper = new ObjectMapper();
    try (Jedis jedis = new Jedis("redis", 6379)) {
      JedisPubSub pubSub = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
          try {
            System.out.println("Received message from channel " + channel + ": " + message);

            MessageToParser messageToParser =
                objectMapper.readValue(message, MessageToParser.class);

            if (messageToParser.getActionQueue() == ActionQueue.PARSEGAME) {
              Runnable runnable = new RunGameParser(messageToParser);

              Metrics.mesure("backoffice", "game-parse", runnable);

            } else if (messageToParser.getActionQueue() == ActionQueue.PARSEPLAYER) {

              Runnable runnable = new RunPlayerParser(messageToParser.getFolderToParse());

              Metrics.mesure("backoffice", "player-parse", runnable);

            } else if (messageToParser.getActionQueue() == ActionQueue.INIT_SYSTEM) {

              Thread thread = new Thread(new RunInitSystem());
              thread.start();
            } else if (messageToParser.getActionQueue() == ActionQueue.CREATE_TENANT_ENVIRONMENT) {
              UserTenant userTenant = new UserTenant();
              // TODO demo
              userTenant.setTenantName("demo");
              userTenant.setPassword("demo");
              userTenant.setIsAdmin(false);
              userTenant.setLogin("demo");
              Thread thread = new Thread(new RunInitTenant(userTenant));
              thread.start();
            }
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);

          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      };
      jedis.subscribe(pubSub, Topic.TOPIC_TO_QUEUE);
    }

}}
