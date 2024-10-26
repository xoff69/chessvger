package com.xoff.chessvger.queue;

import com.xoff.chessvger.ui.web.view.ChessLineView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Producer implements Runnable {
  @SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "We want that")
  private final DataQueue dataQueue;

  private final String data;

  public Producer(DataQueue dataQueue, String data) {
    this.dataQueue = dataQueue;
    this.data = data;
  }

  @Override
  public void run() {
    produce();
  }

  private void produce() {
    while (dataQueue.runFlag) {
      synchronized (this) {
        while (dataQueue.isFull() && dataQueue.runFlag) {
          try {
            dataQueue.waitOnFull();
          } catch (InterruptedException e) {
            log.error(e.getMessage());
            break;
          }
        }
        if (!dataQueue.runFlag) {
          break;
        }
        ChessLineView message = new ChessLineView(data);
        dataQueue.add(message);
        dataQueue.notifyAllForEmpty();
      }
    }
    log.info("Producer Stopped");
  }


  public void stop() {
    dataQueue.runFlag = false;
    dataQueue.notifyAllForFull();
  }
}