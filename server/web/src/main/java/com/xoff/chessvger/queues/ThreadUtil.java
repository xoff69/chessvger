package com.xoff.chessvger.queues;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class ThreadUtil {
  public static void waitForAllThreadsToComplete(List<Thread> threads) {
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void sleep(long interval) {
    try {
      // Wait for some time to demonstrate threads
      Thread.sleep(interval);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}