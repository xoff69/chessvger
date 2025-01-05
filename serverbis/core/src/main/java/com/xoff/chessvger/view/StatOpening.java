package com.xoff.chessvger.view;

import com.xoff.chessvger.chess.opening.Opening;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;

@Data
public class StatOpening implements Comparable<StatOpening> {
  @SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "We want that")
  private Opening eco;
  /**
   * nombre de parties avec cette ouverture
   */
  private int nbgames;


  @Override
  public int compareTo(StatOpening o) {
    return o.getNbgames() - getNbgames();
  }

}
