package com.xoff.chessvger.chess.move;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class TableCoupModelElement {

  private String move;
  private boolean hasVariation;
  private float evaluation;
  private String bestmove;
  private int id;
  private boolean hasEvaluation;
  /**
   * 1-0 ...
   */
  private String markEvaluation;
  /**
   * !?
   */
  private String markAppreciation;

  /*
  avec l'identifiant du ocup

  */
  public TableCoupModelElement(int id) {
    this.id = id;
    move = StringUtils.EMPTY;
    bestmove = StringUtils.EMPTY;
    markEvaluation = StringUtils.EMPTY;
    markAppreciation = StringUtils.EMPTY;
    hasEvaluation = false;

  }


}
