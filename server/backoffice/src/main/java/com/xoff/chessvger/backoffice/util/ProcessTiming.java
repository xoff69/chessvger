package com.xoff.chessvger.backoffice.util;

import io.opentelemetry.api.GlobalOpenTelemetry;
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



  public static void measureProcess( String processName, Runnable process) {
    // TODO static code
    JaegerGrpcSpanExporter jaegerExporter = JaegerGrpcSpanExporter.builder()
        .setEndpoint("http://localhost:14250")
        .build();

    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(BatchSpanProcessor.builder(jaegerExporter).build())
        .build();

    OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .buildAndRegisterGlobal();

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