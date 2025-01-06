package com.xoff.chessvger.chess.move;

import com.xoff.chessvger.chess.board.Case;
import com.xoff.chessvger.chess.board.EnumPiece;
import com.xoff.chessvger.chess.board.EnumTypeMove;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultInterpretation {

  private boolean isInvalide = false;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<Case> lstSourcesPossibles = null;

  private long faitsdejeu;
  /**
   * indique la piece source
   */
  private EnumPiece pieceSource;
  /**
   * indique le type de coup
   */
  private EnumTypeMove typeCoup;
  /**
   * vrai si prise
   */
  private boolean isPrise;

  /**
   * default constructeur
   */
  public ResultInterpretation() {
    isPrise = false;
    typeCoup = EnumTypeMove.COUP_NORMAL;
    pieceSource = EnumPiece.CASE_VIDE;
    lstSourcesPossibles = new ArrayList();
  }


  /**
   * ou bit a bit pour positionner la value
   */
  public void set(long value) {
    setFaitsdejeu(getFaitsdejeu() | value);
  }


}
