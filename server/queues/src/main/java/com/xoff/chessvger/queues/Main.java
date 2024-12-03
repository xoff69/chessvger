package com.xoff.chessvger.queues;

import com.xoff.chessvger.queues.game.AppConsumerGame;
import com.xoff.chessvger.queues.game.AppProducerGame;
import com.xoff.chessvger.queues.gameofplayer.GameOfAPlayerConsumer;
import com.xoff.chessvger.queues.player.AppConsumerPlayer;
import com.xoff.chessvger.queues.player.AppProducerPlayer;
import com.xoff.chessvger.queues.position.PositionConsumer;
import com.xoff.chessvger.queues.stat.StatConsumer;

public class Main {

  // localhost:19092,
  public static final String getKafkaHost(){ return "broker:9092";}
  // localhost
  public static String getDBHost(){
    return "db_chessvger";
  }

  public static void main(String[] args) {

    System.out.println("Producers and server are starting  V1.0.1");

    System.out.println("AppProducerGame World!");
    Runnable[] runnables =
        new Runnable[] {new AppProducerGame(), new AppConsumerGame(), new AppProducerPlayer(),
            new AppConsumerPlayer(), new StatConsumer(), new PositionConsumer(),
            new GameOfAPlayerConsumer()};

    for (Runnable r : runnables) {
      Thread thread = new Thread(r);
      thread.start();
    }

  }

}
