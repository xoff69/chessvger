package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporters.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public class OpenTelemetryConfig {

  public static OpenTelemetry initOpenTelemetry() {
    // Nouvel exporteur de logs basé sur System.out
    SpanExporter logExporter = new LoggingSpanExporter();

    // Configure le fournisseur de trace
    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(SimpleSpanProcessor.create(logExporter))
        .setResource(Resource.getDefault())
        .build();

    // Construire OpenTelemetry SDK
    OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build();

    // Configurer l'instance comme globale
    GlobalOpenTelemetry.set(openTelemetry);

    return openTelemetry;
  }
}
