package com.xoff.chessvger.util;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TripletPgnAnalyse {

  private boolean isWhatILookFor;
  private int debut;
  private int fin;

  public TripletPgnAnalyse(int pos) {
    isWhatILookFor = false;
    debut = pos;
    fin = pos;
  }

  @Override
  public String toString() {
    String sb = isWhatILookFor + "," + debut + "," + fin;

    return sb;
  }

  public void incrFin() {
    fin++;
  }

  public void incrDebut() {
    debut++;
  }

  public void decrDebut() {
    debut--;
  }


}
