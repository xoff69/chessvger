package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

public class ProcessTiming {
  public static void test() {
    // Obtenir le tracer global
    Tracer tracer = GlobalOpenTelemetry.getTracer("process-timing");

    // Mesurer le temps d'un processus A
    measureProcess(tracer, "ProcessA", () -> {
      // Simuler un traitement
      try {
        Thread.sleep(200); // Exemple de délai (200ms)
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    // Mesurer un autre processus
    measureProcess(tracer, "ProcessB", () -> {
      // Simuler un autre traitement
      try {
        Thread.sleep(500); // Exemple de délai (500ms)
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
  }

  private static void measureProcess(Tracer tracer, String processName, Runnable process) {
    // Démarrer un span pour mesurer le temps
    Span span = tracer.spanBuilder(processName).startSpan();
    span.setAttribute("process.name", processName);
    span.setAttribute("status", "success");
    try {
      // Exécuter le processus
      process.run();
    } catch (Exception e) {
      span.recordException(e);
      span.setStatus(StatusCode.ERROR);
    }
  finally {
      // Terminer le span
      span.end();
    }
  }
}