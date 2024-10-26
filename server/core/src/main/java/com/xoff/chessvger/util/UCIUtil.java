package com.xoff.chessvger.util;

import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.Case;
import com.xoff.chessvger.chess.board.MetierConstants;
import com.xoff.chessvger.chess.board.Piece;
import com.xoff.chessvger.chess.board.Position;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class UCIUtil {

  /**
   * d7d6 d2d4 c5d4 f3d4 g8f6 b1c3 = en d6 d4 cxd4 etat initial: une position
   * on renvoie la traduction de moves, pas depuis le debut
   */
  public static String convertNotationToSuccint(String moves, Position position) {
    StringBuilder res = new StringBuilder();
    String[] tok = moves.split(" ");
    for (int i = 0; i < tok.length; i++) {

      String newMove = StringUtils.EMPTY;
      //    System.out.println(" on etudie " + tok[i]);
      if (tok[i].trim().length() < 4) {
        log.error("erreur " + tok[i]);
        continue;
      }
      int startc = PgnUtil.letter2int(tok[i].charAt(0));
      int startl = PgnUtil.letter2int(tok[i].charAt(1));
      //        System.out.println(" on regarde " + startc + "==" + startl);
      List<Piece> lp = position.getListePiece().getWhitePieces();
      if (position.getJoueurAuTrait() != MetierConstants.BLANC) {
        lp = position.getListePiece().getBlackPieces();
      }
      for (Piece p : lp) {
        Case cas = p.getActualPosition();
        //          System.out.println(" on regarde cas " + cas);
        if (cas.column == startc && cas.raw == startl) {
          // on a trouve l origine

          switch (p.getTypePiece()) {
            case CAVALIER:
              newMove = "N";
              break;
            case FOU:
              newMove = "B";
              break;
            case REINE:
              newMove = "Q";
              break;
            case ROI:
              newMove = "K";
              break;
            case TOUR:
              newMove = "R";
              break;
            default:
              // pion et je sais pas
          }
          int endc = PgnUtil.letter2int(tok[i].charAt(2));
          int endl = PgnUtil.letter2int(tok[i].charAt(3));
          char cd = position.getAt(endc, endl);
          if (cd != MetierConstants.CASE_VIDE) {
            newMove = newMove + "x";
            log.info("prise " + newMove);
          }
          // on met la fin
          newMove = newMove + tok[i].charAt(2) + tok[i].charAt(3);
          log.info("fin " + newMove);
          break;
        }
      }
      log.info("on joue " + newMove);
// TODO il nous manquera le roque et les prises, et les coups ambigus, les prises, ep
      BoardManager.play(position, newMove);
      res.append(newMove).append(" ");
      // gerer les numeros de move
      log.info(position.toString());
    }

    return res.toString();
  }


}