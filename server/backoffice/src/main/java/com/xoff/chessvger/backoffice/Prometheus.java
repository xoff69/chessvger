package com.xoff.chessvger.backoffice;

import com.sun.net.httpserver.HttpServer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
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

    // Configurer le serveur HTTP intégré
    HttpServer server = HttpServer.create(new InetSocketAddress(8087), 0);
    server.createContext("/metrics", httpExchange -> {
      httpExchange.getResponseHeaders().set("Content-Type", TextFormat.CONTENT_TYPE_004);

      // Utilisation d'un Writer au lieu d'un OutputStream
      try (Writer writer = new OutputStreamWriter(httpExchange.getResponseBody())) {
        TextFormat.write004(writer, registry.metricFamilySamples());
        httpExchange.sendResponseHeaders(200, 0);
      } catch (Exception e) {
        httpExchange.sendResponseHeaders(500, -1);
      }
    });

    server.start();
    System.out.println("Prometheus metrics available at http://localhost:9464/metrics");
    System.out.println("Starting Prometheus...");
    int cpmte = 50;
    for (int i = 0; i < cpmte; i++) {
      requestCounter.inc(); // Incrémenter le compteur
      Thread.sleep(10); // Pause de 1 seconde
    }
    System.out.println("end Prometheus...");
  }
}
