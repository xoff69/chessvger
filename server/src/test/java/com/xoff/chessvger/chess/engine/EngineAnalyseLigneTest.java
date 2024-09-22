package com.xoff.chessvger.chess.engine;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class EngineAnalyseLigneTest {

  private final static String FEN_EXEMPLE = "1k6/3K1B2/8/2N5/8/8/8/8 b - - 0 9";

  @Test
  public void testAnalyse() throws InterruptedException {
        /*
        GlobalManager.getInstance().getEngineManager().clear();
        Engine e = EngineFactory.createDefaultEngineForSystem();
        GlobalManager.getInstance().getEngineManager().addDatabaseManager(e);
        EngineAnalyseLigne engineAnalyseLigne = new EngineAnalyseLigne(e.getId());
        Position p = Position.fen2Position(FEN_EXEMPLE);

        engineAnalyseLigne.getEngine().setFen(p.tofen());
        assertEquals(e.getId(), engineAnalyseLigne.getEngine().getId());


        MyRunner m = new MyRunner(engineAnalyseLigne);
        Thread t1 = new Thread(m);   // Using the constructor Thread(Runnable r)
        t1.start();
        Thread.sleep(1000);

        t1.interrupt();

         */
// TODO
  }

  public class MyRunner implements Runnable {
    private final EngineAnalyseLigne engineAnalyseLigne;

    public MyRunner(EngineAnalyseLigne engineAnalyseLigne) {
      this.engineAnalyseLigne = engineAnalyseLigne;
    }

    public void run() {
      engineAnalyseLigne.runAnalyse();
    }
  }
}