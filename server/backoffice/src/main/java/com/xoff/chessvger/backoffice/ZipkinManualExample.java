package com.xoff.chessvger.backoffice;

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

public class ZipkinManualExample {

  public static void main(String[] args) throws InterruptedException {
    // Configure Zipkin Sender and Reporter
    OkHttpSender sender = OkHttpSender.create("http://localhost:9411/api/v2/spans");
    AsyncReporter<zipkin2.Span> spanReporter = AsyncReporter.create(sender);

    // Configure Tracing with Brave
    Tracing tracing = Tracing.newBuilder()
        .localServiceName("zipkin-manual-example")
        .spanReporter(spanReporter)
        .currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder().build())
        .sampler(Sampler.ALWAYS_SAMPLE)
        .build();

    Tracer tracer = tracing.tracer();

    // Executor for parallel tasks
    ExecutorService executor = Executors.newSingleThreadExecutor();

    // Create a parent span
    Span parentSpan = tracer.newTrace().name("parent-span").start();
    try (Tracer.SpanInScope ws = tracer.withSpanInScope(parentSpan)) {
      System.out.println("Parent span started...");

      executor.submit(() -> {
        // Propagate the parent context to the child span
        Span childSpan = tracer.newChild(parentSpan.context()).name("count-task").start();
        try (Tracer.SpanInScope childScope = tracer.withSpanInScope(childSpan)) {
          countSomething();
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

  private static void countSomething() {
    System.out.println("Counting task started...");
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(100); // Simulate some work
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    System.out.println("Counting task completed.");
  }
}
