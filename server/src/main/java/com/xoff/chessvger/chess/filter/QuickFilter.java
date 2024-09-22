package com.xoff.chessvger.chess.filter;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.game.CommonGame;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class QuickFilter {

  public static final int NOVALUE = -10000;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private final Position position;
  private int eloMin;
  private int yearMax;
  private boolean isMemory = false;

  private QuickFilter() {
    position = null;
  }


  public QuickFilter(Position position) {
    this.position = position;
    eloMin = NOVALUE;
    yearMax = NOVALUE;
    isMemory = false;

  }


  /**
   * vrai si le filtre est celui correspondant a celui du hd
   */
  public boolean isEmpty() {
    return eloMin == NOVALUE && yearMax == NOVALUE;
  }

  @Override
  public String toString() {

    return "QuickFilter[" + position + ",elo min=" + eloMin + "yearMax=" + yearMax + "]";

  }


  public boolean acceptGame(CommonGame commonGame) {
    int year = NOVALUE; // attention au  bug de NOVALUE
    // par dafaut on considere qu'une year inconnue est ok
    if (commonGame.getDate().length() > 4) {
      try {
        year = Integer.parseInt(commonGame.getDate().substring(0, 4));
      } catch (NumberFormatException nef) {
        log.error(" year acceptGame ");
      }
    }
    // pour le elo on compare au max des deux joueurs
    int elog = Math.max(commonGame.getBlackElo(), commonGame.getWhiteElo());

    return year > yearMax && elog > eloMin;
  }


}
