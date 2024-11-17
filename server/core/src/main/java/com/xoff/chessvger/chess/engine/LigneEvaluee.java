package com.xoff.chessvger.chess.engine;

import lombok.Data;

@Data
public class LigneEvaluee {

  private String pv;
  private float eval;
  private String ligne;
  private String profondeur;


}
