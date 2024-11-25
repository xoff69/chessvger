package com.xoff.chessvger.queues;

import com.xoff.chessvger.queues.game.AppConsumerGame;
import com.xoff.chessvger.queues.game.AppProducerGame;
import com.xoff.chessvger.queues.player.AppConsumerPlayer;
import com.xoff.chessvger.queues.player.AppProducerPlayer;
import java.util.Map;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
@Slf4j
public class Main {
  public static void main(String[] args) {

   log.info("Producers and server are starting");
    Thread.startVirtualThread(() -> {
      AppProducerGame.runAppProducerGame();
    });
    Thread.startVirtualThread(() -> {
      AppConsumerGame.runAppConsumerGame();
    });
    Thread.startVirtualThread(() -> {
      AppProducerPlayer.runAppProducerPlayer();
    });
    Thread.startVirtualThread(() -> {
      AppConsumerPlayer.runAppConsumerPlayer();
    });

  }

}
