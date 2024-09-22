package com.xoff.chessvger.ui.web.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
public class StatJoueurView {
  @Getter
  @Setter
  private int stat10blanc;
  @Getter
  @Setter
  private int stat12blanc;
  @Getter
  @Setter
  private int stat01blanc;
  @Getter
  @Setter
  private int stat10noir;
  @Getter
  @Setter
  private int stat12noir;
  @Getter
  @Setter
  private int stat01noir;
  @Getter
  @Setter

  private int stat10global;
  @Getter
  @Setter
  private int stat12global;
  @Getter
  @Setter
  private int stat01global;

  @Setter
  private List<StatOpening> openingWhite;

  @Setter
  private List<StatOpening> openingBlack;
  /**
   * tendance des resultats
   * sur +-Constants.OPENING_TENDANCE_NUMBER
   */
  @Getter
  @Setter
  private int tendance;


}
