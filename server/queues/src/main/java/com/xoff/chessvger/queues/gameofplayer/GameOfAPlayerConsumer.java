package com.xoff.chessvger.queues.gameofplayer;

import com.xoff.chessvger.queues.util.Runner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameOfAPlayerConsumer implements Runner {

  @Override
  public void run() {
  log.info("GameOfAPlayerConsumer started");
  }
}
