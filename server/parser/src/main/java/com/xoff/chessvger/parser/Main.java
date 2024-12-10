package com.xoff.chessvger.parser;


import com.xoff.chessvger.parser.game.RunGameParser;
import com.xoff.chessvger.parser.player.RunPlayerParser;

public class Main {


  // localhost
  private static String dbhost = "localhost:19092"; //"db_chessvger"

  public static String getDBHost() {
    return dbhost;
  }

  public static void main(String[] args) {

    if (args.length > 0) {
      System.out.println("Main!" + args[0]);
      dbhost = "db_chessvger";
    }
    Runnable[] runnables = new Runnable[] {new RunGameParser(), new RunPlayerParser()};

    for (Runnable r : runnables) {
      Thread thread = new Thread(r);
      thread.start();
    }

  }

}
