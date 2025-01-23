package com.xoff.chessvger.backoffice;

import io.opentelemetry.api.OpenTelemetry;


public class TestOpenTelemetry {
  public static void main(String[] args) {
    // Initialiser OpenTelemetry
    OpenTelemetry openTelemetry = OpenTelemetryConfig.initOpenTelemetry();

    // Exemple d'utilisation
    ProcessExample processExample = new ProcessExample();
    processExample.executeProcess();

    MethodCounterExample counterExample = new MethodCounterExample();
    counterExample.myMethod();
    counterExample.myMethod(); // Appel supplémentaire pour compter

    System.out.println("Instrumentation complète.");
  }




}
