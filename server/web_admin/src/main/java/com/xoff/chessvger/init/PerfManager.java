package com.xoff.chessvger.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerfManager {
  private static Path path = Paths.get("data/performance.txt");
  private static long startTime=0L;
  static {
    if( !Files.exists(path)) {
      try {
        Files.createFile(path);
      } catch (IOException e) {
        log.error("create file");
        throw new RuntimeException(e);
      }
    }
  }
  public static void start(String message){
    append(message);
    startTime=System.currentTimeMillis();
  }
  public static void end(String message,long nb){
    long t=System.currentTimeMillis()-startTime;
    long ts=t/1000;
    long average = nb/ts;
    append(message+" "+(t/1000)+" s., "+nb +" games with "+ average+" games/s");
    append("-------------------------");
  }
  private  static void append(String message){
    try {
      Files.write(path, (message+"\n").getBytes(),StandardOpenOption.APPEND);
    } catch (IOException e) {
      log.error("append "+message);
      throw new RuntimeException(e);
    }
  }
}
