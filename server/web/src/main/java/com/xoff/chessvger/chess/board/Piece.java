package com.xoff.chessvger.chess.board;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Piece {

  private int couleur;

  private EnumPiece typePiece;
  /**
   * la liste des mouvements de la piece depuis le debut
   */
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<String> movesSinceStart;

  /**
   * les moves protecteurs pas tous les moves, seuls ceux qui sont utiles
   * exemple pion x meme couleur, pas le pion qui avance
   */
  private int moveDefender;
  /*
   * la position actuelle
   */
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private Case actualPosition;

  /**
   * indique si la piece a subi une promotion CASE_VIDE si non
   */
  private EnumPiece promotionIn;

  /**
   * constructeur
   */
  public Piece() {
    promotionIn = EnumPiece.CASE_VIDE;
    typePiece = EnumPiece.CASE_VIDE;
    movesSinceStart = new ArrayList<>();

    actualPosition = new Case();
    couleur = MetierConstants.BLANC;
    moveDefender = 0;

  }

  /**
   * constructeur
   */
  public Piece(Piece other) {
    promotionIn = other.promotionIn;
    typePiece = other.typePiece;
    movesSinceStart = new ArrayList<>();
    for (String s : other.movesSinceStart) {
      movesSinceStart.add(s);
    }
    moveDefender = other.moveDefender;

    actualPosition = new Case(other.actualPosition);
    couleur = other.couleur;
  }

  public Piece duplicate() {
    Piece p = new Piece(this);
    return p;

  }


}
