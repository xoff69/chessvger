package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

public class OpenTelemetryExample {
  public static void vasy() {
    // Configurer l'exportateur Jaeger
    JaegerGrpcSpanExporter jaegerExporter = JaegerGrpcSpanExporter.builder()
        .setEndpoint("http://localhost:14250")
        .build();

    // Configurer le traceur SDK
    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(BatchSpanProcessor.builder(jaegerExporter).build())
        .build();

    // Initialiser OpenTelemetry SDK
    OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build();

    // Obtenir le traceur
    Tracer tracer = openTelemetrySdk.getTracer("example-tracer");

    // Créer une trace
    Span span = tracer.spanBuilder("chessvger-span").startSpan();

    try (Scope scope = span.makeCurrent()) {
      // Simuler du travail dans la trace
      System.out.println("Doing some work...");
    } finally {
      span.end();
    }

    // Fermer les ressources
    tracerProvider.close();
  }
}

