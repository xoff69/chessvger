package com.xoff.chessvger.chess.engine;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.util.PgnUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@SuppressFBWarnings(value = {"COMMAND_INJECTION"}, justification = "We want that")
public class StockFish {

  private final int pv;
  private final int prof;
  private final String path;
  private Process engineProcess;
  private BufferedReader processReader;
  private OutputStreamWriter processWriter;


  public StockFish(int pv, int prof, String path) {
    this.pv = pv;
    this.prof = prof;
    this.path = path;
  }

    /*
    public Process runAnalyse(HashMap<String, LigneEvaluee> hashEval, String fen) {
        try {

            hashEval.clear();
            log.info(path);
            ProcessBuilder builder = new ProcessBuilder(path + " ");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            process.getOutputStream().write((" position fen " + fen + "\n").getBytes());
            process.getOutputStream().write((" uci  \n").getBytes());
            process.getOutputStream().write((" setoption name MultiPV value " + pv + "  \n").getBytes());

            process.getOutputStream().write(("go depth " + prof + " \n").getBytes());

            process.getOutputStream().flush();
            return process;
        } catch (IOException ioe) {
            log.error(ioe, ioe);
        }
        return null;

    }
*/


  public boolean startEngine() {
    try {
      engineProcess = Runtime.getRuntime().exec(FilenameUtils.getName((path)));
      processReader = new BufferedReader(
          new InputStreamReader(engineProcess.getInputStream(), StandardCharsets.UTF_8));
      processWriter =
          new OutputStreamWriter(engineProcess.getOutputStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error(e.getMessage());
      return false;
    }
    sendCommand("uci");

    // receive output dump
    getOutput(0);  // IMPORTANT

    return true;
  }


  private void sendCommand(String command) {
    //  log.info("oommand "+command);
    try {
      processWriter.write(command + "\n");
      processWriter.flush();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * This is generally called right after 'sendCommand' for getting the raw
   * output from Stockfish
   */
  private String getOutput(int waitTime) {
    StringBuilder buffer = new StringBuilder();
    try {
      Thread.sleep(waitTime);
      sendCommand("isready");
      while (true) {
        String text = processReader.readLine();
        //log.info(text);
        if ("readyok".equals(text)) {
          break;
        } else {
          buffer.append(text).append("\n");
        }
      }
    } catch (IOException | InterruptedException e) {
      log.error(e.getMessage());
    }
    return buffer.toString();
  }

  /**
   * This function returns the best move for a given position after
   * calculating for 'waitTime' ms
   */
  public String getBestMove(String fen, int waitTime) {
    sendCommand("position fen " + fen);
    sendCommand("go movetime " + waitTime);
    String s = getOutput(waitTime + 50);
    //    log.info("evaluetion la meilleur "+s+"<<<<<<<<<<");
    if (s.contains("bestmove")) {
      String mv = s.split("bestmove ")[1].split(" ")[0];
      log.info("evaluetion la meilleur " + mv + "<<<<<<<<<<");
      return PgnUtil.convert1MoveSan2Pgn(Position.fen2Position(fen), mv);
    }
    // pas trouve
    return StringUtils.EMPTY;
  }

  public void stopEngine() {
    try {
      sendCommand("quit");
      processReader.close();
      processWriter.close();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  public float getEvalScore(String fen, int waitTime) {
    sendCommand("position fen " + fen);
    sendCommand("go movetime " + waitTime);

    float evalScore = 0.0f;
    String[] dump = getOutput(waitTime + 20).split("\n");
    for (int i = dump.length - 1; i >= 0; i--) {
      if (dump[i].startsWith("info depth ")) {
        try {
          evalScore = Float.parseFloat(dump[i].split("score cp ")[1].split(" nodes")[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          try {

            evalScore =
                Float.parseFloat(dump[i].split("score cp ")[1].split(" upperbound nodes")[0]);
          } catch (NumberFormatException | ArrayIndexOutOfBoundsException e2) {
            try {
              evalScore =
                  Float.parseFloat(dump[i].split("score cp ")[1].split(" lowerbound nodes")[0]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e3) {
              // log.error("avant plantage " + dump[i], e3);

            }
          }
        }
      }
    }
    return evalScore / 100;
  }
}
