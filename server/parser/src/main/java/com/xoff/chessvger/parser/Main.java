package com.xoff.chessvger.queues;

import com.xoff.chessvger.queues.game.AppConsumerGame;
import com.xoff.chessvger.queues.game.AppProducerGame;
import com.xoff.chessvger.queues.gameofplayer.GameOfAPlayerConsumer;
import com.xoff.chessvger.queues.player.AppConsumerPlayer;
import com.xoff.chessvger.queues.player.AppProducerPlayer;
import com.xoff.chessvger.queues.position.PositionConsumer;
import com.xoff.chessvger.queues.stat.StatConsumer;

public class Main {


  // localhost
  private static String dbhost="localhost:19092"; //"db_chessvger"
  public static String getDBHost(){
    return dbhost ;
  }

  public static void main(String[] args) {

    if (args.length >0){
      System.out.println("Main!" + args[0]);
      dbhost="db_chessvger";
    }
    System.out.println("Main!" +kafkahost+"-"+getKafkaHost());
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
