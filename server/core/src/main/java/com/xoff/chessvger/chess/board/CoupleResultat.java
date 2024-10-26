package com.xoff.chessvger.chess.board;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
public class CoupleResultat {

  private boolean resultatCoherent;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private Set<Case> lc;

  private String extraInfoError;

  public CoupleResultat() {
    resultatCoherent = true;
    lc = new HashSet<>();
    extraInfoError = StringUtils.EMPTY;
  }


}
