package com.xoff.chessvger.backoffice.util;


import brave.Tracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.sampler.Sampler;
import brave.Tracer;
import brave.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Metrics {

  private static final String SERVER="http://zipkin";

  public static void mesure(String serviceName, String span, Runnable runnable) throws InterruptedException {
    // Configure Zipkin Sender and Reporter
    OkHttpSender sender = OkHttpSender.create(SERVER+":9411/api/v2/spans");
    AsyncReporter<zipkin2.Span> spanReporter = AsyncReporter.create(sender);

    // Configure Tracing with Brave
    Tracing tracing = Tracing.newBuilder()
        .localServiceName(serviceName)
        .spanReporter(spanReporter)
        .currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder().build())
        .sampler(Sampler.ALWAYS_SAMPLE)
        .build();

    Tracer tracer = tracing.tracer();

    // Executor for parallel tasks
    ExecutorService executor = Executors.newSingleThreadExecutor();

    // Create a parent span
    Span parentSpan = tracer.newTrace().name(serviceName).start();
    try (Tracer.SpanInScope ws = tracer.withSpanInScope(parentSpan)) {
      System.out.println("Parent span started...");

      executor.submit(() -> {
        // Propagate the parent context to the child span
        Span childSpan = tracer.newChild(parentSpan.context()).name(span).start();
        try (Tracer.SpanInScope childScope = tracer.withSpanInScope(childSpan)) {
          System.out.println("runnable running...");
          childSpan.tag("key1", "test christophe");
          runnable.run();
        } finally {
          childSpan.finish();
          System.out.println("Child span finished.");
        }
      });
    } finally {
      parentSpan.finish();
      System.out.println("Parent span finished.");
    }

    // Shutdown executor properly and wait for completion
    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);

    // Close the reporter to flush all spans
    spanReporter.close();
  }


}
