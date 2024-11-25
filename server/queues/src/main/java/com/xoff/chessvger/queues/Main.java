package com.xoff.chessvger.queues;

import com.xoff.chessvger.queues.game.AppConsumerGame;
import com.xoff.chessvger.queues.game.AppProducerGame;
import com.xoff.chessvger.queues.gameofplayer.GameOfAPlayerConsumer;
import com.xoff.chessvger.queues.player.AppConsumerPlayer;
import com.xoff.chessvger.queues.player.AppProducerPlayer;
import com.xoff.chessvger.queues.position.PositionConsumer;
import com.xoff.chessvger.queues.stat.StatConsumer;
import java.util.Map;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
@Slf4j
public class Main {
  public static void main(String[] args) {

   log.info("Producers and server are starting");
    Thread.startVirtualThread(() -> {
      new AppProducerGame().run();
    });
    Thread.startVirtualThread(() -> {
      new AppConsumerGame().run();
    });
    Thread.startVirtualThread(() -> {
      new AppProducerPlayer().run();
    });
    Thread.startVirtualThread(() -> {
      new AppConsumerPlayer().run();
    });
    Thread.startVirtualThread(() -> {
      new StatConsumer().run();
    });
    Thread.startVirtualThread(() -> {
      new PositionConsumer().run();
    });
    Thread.startVirtualThread(() -> {
      new GameOfAPlayerConsumer().run();
    });


  }

}
