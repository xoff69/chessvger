package com.xoff.chessvger.chess.board;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
public class ListPiece {

  @Getter
  private final List<Piece> whitePieces;
  @Getter
  private final List<Piece> blackPieces;

  public ListPiece() {
    whitePieces = new ArrayList<>();
    blackPieces = new ArrayList<>();
  }

  public ListPiece duplicate() {
    ListPiece lp = new ListPiece();
    for (Piece p : whitePieces) {
      lp.addWhite(p.duplicate());
    }
    for (Piece p : blackPieces) {
      lp.addBlack(p.duplicate());
    }
    return lp;
  }

  public void addWhite(Piece p) {
    whitePieces.add(p);

  }

  public void addBlack(Piece p) {

    blackPieces.add(p);
  }

  private void removePiece(Piece pieceToDelete, List<Piece> pieces) {
    int i = 0;

    for (Piece p : pieces) {

      if (p.getActualPosition().column == pieceToDelete.getActualPosition().column &&
          p.getActualPosition().raw == pieceToDelete.getActualPosition().raw) {
        pieces.remove(i);
        break;
      }
      i++;
    }
  }

  public void removeWhitePiece(Piece pieceToDelete) {
    removePiece(pieceToDelete, whitePieces);
  }

  public void removeBlackPiece(Piece pieceToDelete) {
    removePiece(pieceToDelete, blackPieces);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Piece p : whitePieces) {
      sb.append(p).append("\n");
    }
    sb.append("--");
    for (Piece p : blackPieces) {
      sb.append(p).append("\n");
    }
    return sb.toString();
  }

  public void clear() {
    whitePieces.clear();
    blackPieces.clear();
  }


}
