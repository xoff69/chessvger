package com.xoff.chessvger.backoffice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.backoffice.environnement.RunInitSystem;
import com.xoff.chessvger.backoffice.environnement.RunInitTenant;
import com.xoff.chessvger.backoffice.game.RunGameParser;
import com.xoff.chessvger.backoffice.player.RunPlayerParser;
import com.xoff.chessvger.common.UserTenant;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.topic.Topic;
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
      dbhost = "localhost";
      // for test
      UserTenant userTenant = new UserTenant();
      userTenant.setName("xoff");
      userTenant.setPassword("xoff");
      userTenant.setIsAdmin(false);
      userTenant.setId(5L);
      userTenant.setLogin("xoff");
      Thread thread = new Thread(new RunInitTenant(userTenant));
      thread.start();
    }
    System.out.println("Start main");
    ObjectMapper objectMapper = new ObjectMapper();
    try (Jedis jedis = new Jedis("redis", 6379)) {
      JedisPubSub pubSub = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
          try {
            System.out.println("Received message from channel " + channel + ": " + message);

            MessageToParser messageToParser =
                objectMapper.readValue(message, MessageToParser.class);

            if (messageToParser.getActionQueue() == ActionQueue.CREATE_TENANT_ENVIRONMENT) {
              // TODO
              System.out.println("Creating environment");
            } else if (messageToParser.getActionQueue() == ActionQueue.PARSEGAME) {
              Thread thread = new Thread(new RunGameParser(messageToParser.getFolderToParse()));
              thread.start();
            } else if (messageToParser.getActionQueue() == ActionQueue.PARSEPLAYER) {

              Thread thread = new Thread(new RunPlayerParser(messageToParser.getFolderToParse()));
              thread.start();
            }
           else if (messageToParser.getActionQueue() == ActionQueue.INIT_SYSTEM) {

            Thread thread = new Thread(new RunInitSystem());
            thread.start();
          }
            else if (messageToParser.getActionQueue() == ActionQueue.CREATE_TENANT_ENVIRONMENT) {
              UserTenant userTenant=new UserTenant();
              // todo
              Thread thread = new Thread(new RunInitTenant(userTenant));
              thread.start();
            }
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);

          }
        }
      };
      jedis.subscribe(pubSub, Topic.TOPIC_TO_QUEUE);
    }
  }
}
