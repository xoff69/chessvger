package com.xoff.chessvger.queues;


import com.xoff.chessvger.view.ChessLineView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedList;
import java.util.Queue;
import lombok.Getter;

@SuppressFBWarnings(value = {"UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"}, justification = "We want that")
public class DataQueue {
  private final Queue<ChessLineView> queue = new LinkedList<>();
  private final int maxSize;
  private final Object fullQueue = new Object();
  private final Object emptyQueue = new Object();
  @Getter
  private final String topic;
  public boolean runFlag = true;

  public DataQueue(int maxSize, String topic) {
    this.maxSize = maxSize;
    this.topic = topic;
  }

  public boolean isFull() {
    return queue.size() == maxSize;
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public void waitOnFull() throws InterruptedException {
    synchronized (fullQueue) {
      fullQueue.wait();
    }
  }

  public void waitOnEmpty() throws InterruptedException {
    synchronized (emptyQueue) {
      emptyQueue.wait();
    }
  }

  public void notifyAllForFull() {
    synchronized (fullQueue) {
      fullQueue.notifyAll();
    }
  }

  public void notifyAllForEmpty() {
    synchronized (emptyQueue) {
      emptyQueue.notifyAll();
    }
  }

  public void add(ChessLineView message) {
    synchronized (queue) {
      queue.add(message);
    }
  }

  public ChessLineView remove() {
    synchronized (queue) {
      return queue.poll();
    }
  }
}