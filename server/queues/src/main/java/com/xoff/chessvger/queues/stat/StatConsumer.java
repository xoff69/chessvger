package com.xoff.chessvger.queues.stat;

import com.xoff.chessvger.queues.util.Runner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatConsumer implements Runner {

  @Override
  public void run() {
    log.info("Stat consumer started");
  }
}
