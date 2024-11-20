package com.xoff.chessvger.queues;


import com.xoff.chessvger.view.ChessLineView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Slf4j
@AllArgsConstructor
public class Consumer implements Runnable {

  @SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "We want that")
  private final DataQueue dataQueue;
  @SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "We want that")
  private final SimpMessageSendingOperations consumerable;

  public Consumer(SimpMessageSendingOperations consumerable, DataQueue dataQueue) {
    this.consumerable = consumerable;
    this.dataQueue = dataQueue;
  }

  @Override
  public void run() {
    consume();
  }

  public void consume() {
    while (dataQueue.runFlag) {
      synchronized (this) {
        while (dataQueue.isEmpty() && dataQueue.runFlag) {
          try {
            dataQueue.waitOnEmpty();
          } catch (InterruptedException e) {
            log.error(e.getMessage());
            break;
          }
        }
        if (!dataQueue.runFlag) {
          break;
        }
        ChessLineView message = dataQueue.remove();
        dataQueue.notifyAllForFull();
        consumerable.convertAndSend(dataQueue.getTopic(), message);
      }
    }
    log.info("Consumer Stopped");
  }


  public void stop() {
    dataQueue.runFlag = false;
    dataQueue.notifyAllForEmpty();
  }
}
