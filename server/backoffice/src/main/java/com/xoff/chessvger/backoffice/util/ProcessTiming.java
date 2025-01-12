package com.xoff.chessvger.backoffice.util;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

public class ProcessTiming {



  public static void measureProcess( String processName, Runnable process) {
    Tracer tracer = GlobalOpenTelemetry.getTracer("process-timing");
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