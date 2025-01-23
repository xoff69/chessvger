package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

public class MethodCounterExample {
  private static final Meter meter = GlobalOpenTelemetry.getMeter("example-meter");
  private static final LongCounter methodCallCounter = meter
      .counterBuilder("method_calls")
      .setDescription("Counts the number of method calls")
      .setUnit("calls")
      .build();

  public void myMethod() {
    // Incrémenter le compteur
    methodCallCounter.add(1);
    System.out.println("Method called!");
  }
}