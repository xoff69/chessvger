package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.prometheus.PrometheusCollector;
import io.prometheus.client.CollectorRegistry;
import java.time.Duration;

public class ExampleProcess {

  private static final Tracer tracer = GlobalOpenTelemetry.getTracer("example-tracer");

  // Créer un compteur pour suivre le nombre d'appels
  private static final Meter meter = GlobalOpenTelemetry.getMeter("example-metrics");
  private static final LongCounter callCounter = meter.counterBuilder("process_calls")
      .setDescription("Number of times the process is called")
      .setUnit("1")
      .build();

  // Créer un histogramme pour mesurer la durée
  private static final DoubleHistogram durationHistogram = meter.histogramBuilder("process_duration")
      .setDescription("Duration of the process execution")
      .setUnit("ms")
      .build();

  public void executeProcess() {
    // Start a span
    Span span = tracer.spanBuilder("executeProcess").startSpan();

    // Enregistrer un appel
    callCounter.add(1);

    long startTime = System.currentTimeMillis();
    try {
      // Simuler un traitement
      Thread.sleep(200); // Simule une tâche qui prend du temps
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      // Calculer la durée et enregistrer la métrique
      long duration = System.currentTimeMillis() - startTime;
      durationHistogram.record(duration);

      // Terminer le span
      span.end();
    }
  }

  public static void initOpenTelemetry() {
    // Configurer le serveur Prometheus pour exposer les métriques
    CollectorRegistry prometheusRegistry = new CollectorRegistry();

    // Configurer l'exportateur Prometheus avec OpenTelemetry
    SdkMeterProvider meterProvider = SdkMeterProvider.builder()
        .registerMetricReader(
            PeriodicMetricReader.create(
                PrometheusCollector.create(prometheusRegistry)
            )
        )
        .build();

    GlobalOpenTelemetry.resetForTest(); // Optionnel : réinitialise pour éviter les conflits
  }

  public static void main(String[] args) {
    initOpenTelemetry();

    ExampleProcess process = new ExampleProcess();

    // Appeler plusieurs fois la méthode pour générer des métriques
    process.executeProcess();
    process.executeProcess();
    process.executeProcess();

    System.out.println("Metrics available at http://localhost:9464/metrics");
  }
}
