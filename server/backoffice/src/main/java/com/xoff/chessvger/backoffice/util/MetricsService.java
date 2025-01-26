package com.xoff.chessvger.backoffice.util;

import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;
import java.io.IOException;

public class MetricsService {

  private static final Summary executionSummary = Summary.build()
      .name("method_execution_time_seconds")
      .help("Time taken to execute a method in seconds").labelNames("method_name")
      .register();
  public MetricsService() {
    try {
      System.out.println(">MetricsService: init");
      new HTTPServer(8087); // expose Metrics
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void executeProcess(Runnable r, String name) {
    System.out.println(">Executing process2: " + name);


    Summary.Timer timer = executionSummary.labels(name).startTimer();
    try {
      new Thread(r).start();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      timer.observeDuration();
    }
    System.out.println("<Executing process: " + name);
  }
}
