package com.xoff.chessvger.backoffice;

import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;

public class PrometheusSummaryExample {
  private static final Summary executionSummary = Summary.build()
      .name("method_execution_time_seconds")
      .help("Time taken to execute a method in seconds")
      .register();

  public static void start() throws IOException {
    new HTTPServer(8087); // Serveur HTTP pour exposer les métriques

    for (int i = 0; i < 10; i++) {
      Summary.Timer timer = executionSummary.startTimer();
      try {
        simulateWork();
      } finally {
        timer.observeDuration();
      }
    }
  }

  private static void simulateWork() {
    try {
      Thread.sleep((long) (Math.random() * 500));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
