package com.xoff.chessvger.chess.engine;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.common.queue.QueueManagerForGame;
import com.xoff.chessvger.util.PgnUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Slf4j
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "COMMAND_INJECTION"}, justification = "We want that")

public class EngineAnalyseLigne {

  private final Engine engine;
  private QueueManagerForGame queueManagerForGame;


  private HashMap<String, LigneEvaluee> hashEval;

  private Process process;


  public EngineAnalyseLigne(Engine engine, QueueManagerForGame queueManagerForGame) {
    hashEval = new HashMap();
    this.engine = engine;
    this.queueManagerForGame = queueManagerForGame;
  }

  private Process startAnalyse() {

    log.info("run analyse");
    try {

      hashEval.clear();
      ProcessBuilder builder = new ProcessBuilder(FilenameUtils.getName(engine.getPath()) + " ");
      builder.redirectErrorStream(true);
      process = builder.start();

      process.getOutputStream()
          .write((" position fen " + engine.getFen() + "\n").getBytes(StandardCharsets.UTF_8));
      process.getOutputStream().write((" uci  \n").getBytes(StandardCharsets.UTF_8));
      process.getOutputStream().write(
          (" setoption name MultiPV value " + engine.getPv() + "  \n").getBytes(
              StandardCharsets.UTF_8));

      process.getOutputStream()
          .write(("go depth " + engine.getProf() + " \n").getBytes(StandardCharsets.UTF_8));

      process.getOutputStream().flush();

      return process;
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return null;

  }


  public void runAnalyse() {

    String line;
    log.info("EngineAnalyseLigne: run Analuse");
    try {
      process = startAnalyse();
      if (process == null) {
        log.info("process null run analyse");
        return;
      }
      log.info("EngineAnalyseLigne: run Analuse (2) avant la boucle");
      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
      BufferedReader br = new BufferedReader(isr);

      while ((line = br.readLine()) != null) {
        String isL = getMovesLine(line);
        log.info("isL " + isL);
        if (!StringUtils.isEmpty(isL)) {
          queueManagerForGame.addProduction(isL);
        }
      }
      br.close();
      isr.close();
      is.close();


    } catch (IOException ioe) {
      log.error(ioe.getMessage());
    }

    log.info("runAnalyse fin analyse");
  }

  /**
   * info depth 23 seldepth 34 multipv 3 score cp -39 nodes 33775075 nps
   * 1009054 hashfull 998 tbhits 0 time 33472 pv d7d6 d2d4 c5d4 f3d4 g8f6 b1c3
   * a7a6 c1g5 b8d7 f1e2 e7e6 e1g1 f8e7 g5e3 e8g8 f2f4 d7c5 e2f3 d6d5 e4e5
   * f6e4 f3e4 c5e4 c3e4 d5e4 bestmove e7e6 ponder b1c3
   */
  private String getMovesLine(String line) {

    //   log.info(line);
    if (line.startsWith("info depth ") && line.contains("multipv ")) {
      try {

        LigneEvaluee ev = new LigneEvaluee();
        line = line.replace("upperbound ", "");
        String pv = line.split("multipv ")[1].split("score cp ")[0];

        float eval = Float.parseFloat(line.split("score cp ")[1].split(" nodes")[0]) / 100;
        String ligne = line.split(" pv ")[1];
        ev.setEval(eval);
        ev.setLigne(PgnUtil.convertSan2Pgn(Position.fen2Position(engine.getFen()), ligne));
        ev.setPv(pv);
        ev.setProfondeur(line.split("info depth ")[1].split("seldepth ")[0]);
        hashEval.put(pv, ev);

      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        log.error(StringUtils.normalizeSpace(line), e);

      }
    }
    StringBuilder sb = new StringBuilder();
    List<String> lkey = new ArrayList<>();
    hashEval.keySet().forEach((k) -> {
      lkey.add(k);
    });
    Collections.sort(lkey);
    lkey.stream().map((s) -> hashEval.get(s)).map((e) -> {
      sb.append(e.toString());
      return e;
    }).forEachOrdered((item) -> {
      sb.append("\n");
    });
    return sb.toString();
  }


  public void stop() {

    stop(process);
  }


  private void stop(Process process) {
    try {
      log.info("stop process");
      if (process == null) {
        return;
      }
      process.getOutputStream().write(("quit" + "\n").getBytes(StandardCharsets.UTF_8));
      //process.getOutputStream().write(("go infinite"+"\n").getBytes());
      process.getOutputStream().flush();
      //Wait to getDatabaseManager exit value

      int exitValue = process.waitFor();
      log.info("\n\nExit Value is " + exitValue);

    } catch (InterruptedException | IOException e) {
      log.error("stop", e);
    }
  }


}
