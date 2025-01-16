package com.xoff.chessvger.backoffice.util;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

public class ProcessTiming {
  private static final String JAEGER_HOST = "jaeger"; // Matches the service name in Docker Compose
  private static final int JAEGER_PORT = 4318;

  public static OpenTelemetry initOpenTelemetry() {
    JaegerGrpcSpanExporter jaegerExporter = JaegerGrpcSpanExporter.builder()
        .setEndpoint("http://" + JAEGER_HOST + ":" + JAEGER_PORT)
        .build();

    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(BatchSpanProcessor.builder(jaegerExporter).build())
        .build();

    return OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build();
  }

  public static void measureProcess( String processName, Runnable process) {


    Tracer tracer = initOpenTelemetry().getTracer("process-timing");
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