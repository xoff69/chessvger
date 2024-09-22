package com.xoff.chessvger.common.queue;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Slf4j
public class QueueManagerForGame {
  // https://github.com/eugenp/tutorials/blob/master/static-analysis/pom.xml
  private final DataQueue dataQueue;


  private final List<Thread> threads;


  public QueueManagerForGame(String topic) {
    dataQueue = new DataQueue(10, topic);

    threads = new ArrayList<>();
  }

  public void addConsumer(SimpMessageSendingOperations consumerable) {
    Consumer consumer = new Consumer(dataQueue, consumerable);
    Thread consumerThread = new Thread(consumer);
    consumerThread.start();
    threads.add(consumerThread);
  }

  public void addProduction(String message) {

    Producer producer = new Producer(dataQueue, message);

    Thread producerThread = new Thread(producer);
    log.info("addProduction " + message);
    producerThread.start();
    threads.add(producerThread);
  }

  public void finish() {

    ThreadUtil.waitForAllThreadsToComplete(threads);
  }


}
