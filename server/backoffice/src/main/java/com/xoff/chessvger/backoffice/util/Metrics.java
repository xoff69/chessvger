package com.xoff.chessvger.backoffice.util;



import com.xoff.chessvger.backoffice.player.RunPlayerParser;
import java.util.UUID;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Metrics {

  private static final String SERVER="http://zipkin";

  public static void mesure(String serviceName, String span, Runnable runnable) throws InterruptedException {


    // Executor for parallel tasks
    ExecutorService executor = Executors.newSingleThreadExecutor();


      executor.submit(() -> {


          Thread thread = new Thread(runnable);
          thread.start();

        }

  );


    // Shutdown executor properly and wait for completion
    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);


  }


}
