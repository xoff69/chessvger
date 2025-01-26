package com.xoff.chessvger.backoffice;

import com.sun.net.httpserver.HttpServer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;

public class Prometheus {

  public static void start() throws Exception {
    System.out.println("Starting Prometheus...");
    // Créer un registre Prometheus
    CollectorRegistry registry = new CollectorRegistry();
    DefaultExports.initialize(); // Ajouter des métriques système par défaut

    // Créer un compteur personnalisé
    Counter requestCounter =
        Counter.build().name("requests_total").help("Total number of requests processed")
            .register(registry);
   Histogram methodOneDuration = Histogram.build().name("method_one_execution_duration_seconds")
       .help("Duration of method one executions in seconds").register(registry);

   // Histogram methodTwoDuration = Histogram.build().name("method_two_execution_duration_seconds")
   //     .help("Duration of method two executions in seconds").register(registry);
    // Configurer le serveur HTTP intégré
    HttpServer server = HttpServer.create(new InetSocketAddress(8087), 0);
    server.createContext("/metrics", httpExchange -> {
      httpExchange.getResponseHeaders().set("Content-Type", TextFormat.CONTENT_TYPE_004);

      try (Writer writer = new OutputStreamWriter(httpExchange.getResponseBody())) {
        TextFormat.write004(writer, registry.metricFamilySamples());
        System.out.println(" example " + registry.metricFamilySamples());
        httpExchange.sendResponseHeaders(200, 0);
      } catch (Exception e) {
        httpExchange.sendResponseHeaders(500, -1);
      }
    });

    server.start();
    System.out.println(
        "Prometheus metrics available at http://localhost:8087/metrics or http://backoffice:8087/metrics");
    System.out.println("Starting Prometheus...");
    int cpmte = 50;
    for (int i = 0; i < cpmte; i++) {
      requestCounter.inc(); // Incrémenter le compteur
      Thread.sleep(10); // Pause de 1 seconde
    }

    cpmte = 50;
    for (int i = 0; i < 50; i++) {
      Histogram.Timer timer = methodOneDuration.startTimer();
      try {
        // Simulez la méthode 1 (par exemple, traitement de requêtes)
        Thread.sleep(10); // Simule un temps d'exécution (10 ms)

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      } finally {
        timer.observeDuration();
      }
    }
    System.out.println("end Prometheus avec mesure temps.3.."+server.toString());
  }
}
