package com.xoff.chessvger.backoffice.util;

import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;
import java.io.IOException;

public class MetricsService {

  {
    try {
      System.out.println(">MetricsService: init" );
      new HTTPServer(8087); // expose Metrics
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void executeProcess(Runnable r, String name) {
    System.out.println(">Executing process: " + name);
    Summary executionSummary =
        Summary.build().name(name).help("Time taken to execute a method in seconds").register();

    Summary.Timer timer = executionSummary.startTimer();
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
