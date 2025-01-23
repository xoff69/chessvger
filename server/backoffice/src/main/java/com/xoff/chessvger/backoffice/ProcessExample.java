package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

public class ProcessExample {
  private static final Tracer tracer = GlobalOpenTelemetry.getTracer("example-tracer");

  public void executeProcess() {
    // Start a span
    Span span = tracer.spanBuilder("executeProcess").startSpan();

    try {
      // Code à mesurer
      System.out.println("Executing process...");
      Thread.sleep(200); // Simule un traitement
    } catch (InterruptedException e) {
      span.recordException(e);
    } finally {
      // Terminer le span
      span.end();
    }
  }
}