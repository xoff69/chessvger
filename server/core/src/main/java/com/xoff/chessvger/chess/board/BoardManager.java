package com.xoff.chessvger.chess.board;

import static com.xoff.chessvger.chess.board.MetierConstants.BLANC;
import static com.xoff.chessvger.chess.board.MetierConstants.NOIR;

import com.xoff.chessvger.chess.move.ResultInterpretation;
import com.xoff.chessvger.exception.FindPieceException;
import com.xoff.chessvger.util.FaitsDeJeuUtil;
import com.xoff.chessvger.util.PgnUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class BoardManager {

  protected static final String PC_POINT = ".";
  protected static final String PC_DIEZE_SP = "# ";
  protected static final String PC_TAB = "\t";
  protected static final String PC_SP = " ";
  protected static final String PC_DIEZE = "#";
  protected static final String PC_O = "O";
  protected static final String PC_ZER = "0";


  private static EnumPiece letterToEnumPiece(Character letter) {
    return switch (letter) {
      case 'B' -> EnumPiece.FOU;
      case 'Q' -> EnumPiece.REINE;
      case 'K' -> EnumPiece.ROI;
      case 'R' -> EnumPiece.TOUR;
      case 'N' -> EnumPiece.CAVALIER;
      default -> EnumPiece.CASE_VIDE;
    };
  }


  private static ResultInterpretation interprete(Position position, String move, Case ccOrig,
                                                 Case ccDest) {
    try {
      ResultInterpretation resultInterpretation = new ResultInterpretation();
      if (move.contains("++")) {
        resultInterpretation.set(FaitsDeJeuUtil.DOUBLECHECK);
      }
      if (move.contains("ep") || move.contains("e.p")) {
        resultInterpretation.set(FaitsDeJeuUtil.EP);
      }

      move = PgnUtil.removeComment(move);
      move = StringUtils.replace(move, PC_POINT, PC_DIEZE_SP);
      move = StringUtils.replace(move, PC_DIEZE, PC_POINT);
      move = StringUtils.replace(move, PC_TAB, PC_SP);
      move = StringUtils.replace(move, PC_O, PC_ZER);

      if (position.getJoueurAuTrait() == BLANC) {  // LES BLANCS
        switch (move) {
          case MetierConstants.PETIT_ROQUE:

            resultInterpretation.set(FaitsDeJeuUtil.WHITE_CASTLE_SHORT);
            break;
          case MetierConstants.GRAND_ROQUE:
            resultInterpretation.set(FaitsDeJeuUtil.WHITE_CASTLE_LONG);
            break;
          case "Bxf7":
            resultInterpretation.set(FaitsDeJeuUtil.BXF7);
            break;
          case "Bxh6":
            resultInterpretation.set(FaitsDeJeuUtil.BXF7);
            break;
          default:
            break;
        }
      } else {  // LES NOIRS
        switch (move) {
          case MetierConstants.PETIT_ROQUE:
            resultInterpretation.set(FaitsDeJeuUtil.BLACK_CASTLE_SHORT);
            break;
          case MetierConstants.GRAND_ROQUE:
            resultInterpretation.set(FaitsDeJeuUtil.BLACK_CASTLE_LONG);
            break;
          case "Bxf2":
            resultInterpretation.set(FaitsDeJeuUtil.BXF2);
            break;
          case "Bxh3":
            resultInterpretation.set(FaitsDeJeuUtil.BXH3);
            break;
          default:
            break;

        }
      }

      List<Case> lstSourcesPossibles = resultInterpretation.getLstSourcesPossibles();
      switch (move.charAt(0)) {
        // coup de pion

        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
          resultInterpretation.setPieceSource(EnumPiece.PION);
          switch (move.charAt(1)) {
            // prise + ep
            case 'x': // les deux suivants sont la destination + evt ep +
              // evt promo
              resultInterpretation.setPrise(true);
              if (position.getJoueurAuTrait() == BLANC) {
                position.setNbnoires(position.getNbnoires() - 1);
              } else {
                position.setNbblanches(position.getNbblanches() - 1);
              }
              ccOrig.column = PgnUtil.letter2int(move.charAt(0));
              // manque info iLigO axb3
              ccDest.raw = PgnUtil.letter2int(move.charAt(3));
              ccDest.column = PgnUtil.letter2int(move.charAt(2));
              CoupleResultat cr = findPiece(position, ' ', ccOrig, ccDest);
              if (!cr.isResultatCoherent()) {
                resultInterpretation.setInvalide(true);
              } else {
                lstSourcesPossibles.addAll(cr.getLc());

                // si la chaine contient ep ou =
                if (move.contains(MetierConstants.EP) ||
                    position.getAt(ccDest.column, ccDest.raw) == MetierConstants.CASE_VIDE) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PEP);
                  return resultInterpretation;
                } else if (move.contains("=N")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_N);
                  return resultInterpretation;
                } else if (move.contains("=R")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_R);
                  return resultInterpretation;

                } else if (move.contains("=B")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_B);
                  return resultInterpretation;
                } else if (move.contains("=Q") || move.contains("=")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_Q);
                  return resultInterpretation;
                }
                // correction de l'ancien probleme de promotion
                if ((position.getJoueurAuTrait() == BLANC && ccDest.raw == 7) ||
                    (position.getJoueurAuTrait() == NOIR && ccDest.raw == 0)) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_Q);
                  return resultInterpretation;
                }
                resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
              }
              return resultInterpretation;
            // pas prise
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
              // trouver orig a4
              ccDest.raw = PgnUtil.letter2int(move.charAt(1));
              ccDest.column = PgnUtil.letter2int(move.charAt(0));

              CoupleResultat cr2 = findPiece(position, ' ', ccOrig, ccDest);
              if (!cr2.isResultatCoherent()) {
                resultInterpretation.setInvalide(true);
              } else {
                lstSourcesPossibles.addAll(cr2.getLc());

                if (move.contains("=N")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_N);
                  return resultInterpretation;
                } else if (move.contains("=R")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_R);
                  return resultInterpretation;
                } else if (move.contains("=B")) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_B);
                  return resultInterpretation;
                } else if (move.contains("=Q") || move.contains("=")) {
                  //        System.out.println("promotion dame "+move);
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_Q);
                  return resultInterpretation;
                }
                // correction de l'ancien probleme de promotion
                if ((position.getJoueurAuTrait() == BLANC && ccDest.raw == 7) ||
                    (position.getJoueurAuTrait() == NOIR && ccDest.raw == 0)) {
                  resultInterpretation.setTypeCoup(EnumTypeMove.PROMO_Q);
                  return resultInterpretation;
                }
                resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
              }
              return resultInterpretation;
            default:
          } // fin case interne
          //    log.error("interprete 1.1");
          break;
        // roque
        case '0': // verifier la grammaire :GRAMMAIRE OK
          resultInterpretation.setPieceSource(EnumPiece.ROI);
          if (move.equals(MetierConstants.PETIT_ROQUE)) {
            resultInterpretation.setTypeCoup(EnumTypeMove.PETIT_ROQUE);
            return resultInterpretation;

          } else if (move.equals(MetierConstants.GRAND_ROQUE)) {
            resultInterpretation.setTypeCoup(EnumTypeMove.GRAND_ROQUE);
            return resultInterpretation;
          }
          break;
        //    log.error("interprete 1.2");
        // fou, DAMe, ROI, TOUR, CAVALIER
        case 'B':
        case 'Q':
        case 'K':
        case 'R':
        case 'N': // tout pareil que la lettre qui change et donc l'origine

          resultInterpretation.setPieceSource(letterToEnumPiece(move.charAt(0)));

          switch (move.charAt(1)) {
            // Nf3 ou Naf3 ou Naxf3
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
              switch (move.charAt(2)) {
                // Nf3 ou Naf3 ou Naxf3
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                  // Naf3
                  ccDest.raw = PgnUtil.letter2int(move.charAt(3));
                  ccDest.column = PgnUtil.letter2int(move.charAt(2));
                  ccOrig.column = PgnUtil.letter2int(move.charAt(1));

                  CoupleResultat cr3 = findPiece(position, move.charAt(0), ccOrig, ccDest);
                  if (!cr3.isResultatCoherent()) {
                    resultInterpretation.setInvalide(true);
                  } else {
                    lstSourcesPossibles.addAll(cr3.getLc());

                    resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
                  }
                  return resultInterpretation;
                // Nf3
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                  ccDest.raw = PgnUtil.letter2int(move.charAt(2));
                  ccDest.column = PgnUtil.letter2int(move.charAt(1));

                  CoupleResultat cr4 = findPiece(position, move.charAt(0), ccOrig, ccDest);
                  if (!cr4.isResultatCoherent()) {
                    resultInterpretation.setInvalide(true);
                  } else {
                    lstSourcesPossibles.addAll(cr4.getLc());
                    resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
                  }
                  return resultInterpretation;
                case 'x': // Naxf3
                  if (position.getJoueurAuTrait() == BLANC) {
                    position.setNbnoires(position.getNbnoires() - 1);
                  } else {
                    position.setNbblanches(position.getNbblanches() - 1);
                  }
                  ccDest.raw = PgnUtil.letter2int(move.charAt(4));
                  ccDest.column = PgnUtil.letter2int(move.charAt(3));
                  ccOrig.column = PgnUtil.letter2int(move.charAt(1));

                  CoupleResultat cr5 = findPiece(position, move.charAt(0), ccOrig, ccDest);
                  if (!cr5.isResultatCoherent()) {
                    resultInterpretation.setInvalide(true);
                  } else {
                    lstSourcesPossibles.addAll(cr5.getLc());
                    resultInterpretation.setPrise(true);
                    resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
                  }
                  return resultInterpretation;
                default:
                  //   log.error("interprete 1.3");
              }
              break;
            // N1xf3 ou N1f3
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
              switch (move.charAt(2)) {
                case 'x': // N1xf3
                  resultInterpretation.setPrise(true);

                  if (position.getJoueurAuTrait() == BLANC) {
                    position.setNbnoires(position.getNbnoires() - 1);
                  } else {
                    position.setNbblanches(position.getNbblanches() - 1);
                  }
                  ccDest.raw = PgnUtil.letter2int(move.charAt(4));
                  ccDest.column = PgnUtil.letter2int(move.charAt(3));
                  ccOrig.raw = PgnUtil.letter2int(move.charAt(1));

                  CoupleResultat cr5 = findPiece(position, move.charAt(0), ccOrig, ccDest);
                  if (!cr5.isResultatCoherent()) {
                    resultInterpretation.setInvalide(true);
                  } else {
                    lstSourcesPossibles.addAll(cr5.getLc());
                    resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
                  }
                  return resultInterpretation;
                // N1f3
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                  ccDest.raw = PgnUtil.letter2int(move.charAt(3));
                  ccDest.column = PgnUtil.letter2int(move.charAt(2));
                  ccOrig.raw = PgnUtil.letter2int(move.charAt(1));

                  CoupleResultat cr6 = findPiece(position, move.charAt(0), ccOrig, ccDest);
                  if (!cr6.isResultatCoherent()) {
                    resultInterpretation.setInvalide(true);
                  } else {
                    lstSourcesPossibles.addAll(cr6.getLc());
                    resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
                  }
                  return resultInterpretation;
                default:
                  //   log.error("interprete 1.4");
              }
              break;
            // Nxf3
            case 'x':
              if (position.getJoueurAuTrait() == BLANC) {
                position.setNbnoires(position.getNbnoires() - 1);
              } else {
                position.setNbblanches(position.getNbblanches() - 1);
              }
              ccDest.raw = PgnUtil.letter2int(move.charAt(3));
              ccDest.column = PgnUtil.letter2int(move.charAt(2));

              CoupleResultat cr6 = findPiece(position, move.charAt(0), ccOrig, ccDest);
              if (!cr6.isResultatCoherent()) {
                resultInterpretation.setInvalide(true);
              } else {
                lstSourcesPossibles.addAll(cr6.getLc());
                resultInterpretation.setPrise(true);
                resultInterpretation.setTypeCoup(EnumTypeMove.COUP_NORMAL);
              }
              return resultInterpretation;

            default:
              //  log.error("interprete 1.5");
          }
          break;
        default:
          //  log.error("interprete 1.6");
          //   resultInterpretation.setInvalide(true);

      }
      log.error("sm=" + move + "-" + position);
      log.error("err.interpretation.interprete.casimpossible");
      resultInterpretation.setInvalide(true);
      return resultInterpretation;
    } catch (FindPieceException tpe) {
      log.error("erratic move=" + move + "-" + position);
      log.error("err.interpretation.interprete.trouvepiece.casimpossible");
    }
    ResultInterpretation resultInterpretation = new ResultInterpretation();
    resultInterpretation.setInvalide(true);
    return resultInterpretation;
  }

  public static CoupleResultat findPiece(Position position, char cpieceSource, Case ccOrig,
                                         Case ccDest) throws FindPieceException {

    CoupleResultat cr = new CoupleResultat();

    switch (cpieceSource) {
      case ' ': // PION
        cr = findPawn(position, ccOrig, ccDest);
        break;
      case 'N':
        cr = findKnight(position, ccOrig, ccDest);
        break;
      case 'B':
        cr = findPieceBishop(position, ccOrig, ccDest);
        break;
      case 'Q':
        cr = findPieceQueen(position, ccOrig, ccDest);
        break;
      case 'R':
        cr = findPieceRook(position, ccOrig, ccDest);
        break;
      case 'K':
        cr = findPieceKing(position);
        break;
      default:
    }
    if (cr.getLc().isEmpty()) {
      cr.setResultatCoherent(false);
      throw new FindPieceException("findPiece");
    }
    return cr;
  }


  private static CoupleResultat findPawn(Position position, Case ccOrig, Case ccDest) {
    CoupleResultat cr = new CoupleResultat();
    Set<Case> lst = cr.getLc();

    int sens = position.getSens();
    if (ccOrig.raw == -1) { // simple avancee et la prise
      if (ccOrig.column == -1) { // avancee de 1 ou 2
        ccOrig.column = ccDest.column;
        if (position.getAt(ccDest.column, ccDest.raw + sens) == MetierConstants.CASE_VIDE) {
          ccOrig.raw = ccDest.raw + 2 * sens;
        } else {
          ccOrig.raw = ccDest.raw + sens;
        }
      } else { // prise

        if (position.getJoueurAuTrait() == BLANC) {
          ccOrig.raw = ccDest.raw - 1;
        } else {
          ccOrig.raw = ccDest.raw + 1;
        }
      }
      if (ccOrig.column < 0 || ccOrig.column > 7) {
        log.error("TROUVE PIECE PION: " + ccOrig.column);
        log.error("err.interpretation.TrouvePiecePion");
        cr.setResultatCoherent(false);
      }
      lst.add(ccOrig);
      return cr;
    } else { // prise ou prise ep * cOrig renseigne
      // est ce que �a arrive ?
      log.info("TROUVE PIECE : PRISE SPECIALE ");
      // inversion des couleurs
      // modif cpn 19/09/2002
      if (position.getJoueurAuTrait() == BLANC) {
        ccOrig.raw = ccDest.raw - 1;
      } else {
        ccOrig.raw = ccDest.raw + 1;
      }
      lst.add(ccOrig);
      return cr;
    }
  }

  private static CoupleResultat findKnight(Position position, Case ccOrig, Case ccDest) {

    CoupleResultat cr = new CoupleResultat();
    Set<Case> lst = cr.getLc();


    char pieceCherchee = position.getJoueurAuTrait() == BLANC ? MetierConstants.CAVALIER_BLANC :
        MetierConstants.CAVALIER_NOIR;

    // aller voir les cases autour de la destination
    if (ccOrig.column == -1 && ccOrig.raw == -1) {
      int l;
      int c;
      // 8 positions
      l = ccDest.raw - 2;
      c = ccDest.column - 1;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 2;
      c = ccDest.column - 1;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 2;
      c = ccDest.column + 1;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw - 2;
      c = ccDest.column + 1;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw - 1;
      c = ccDest.column + 2;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw - 1;
      c = ccDest.column - 2;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 1;
      c = ccDest.column - 2;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 1;
      c = ccDest.column + 2;
      if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }

      return cr;
    } else if (ccOrig.raw != -1) {  // fin pas d'indication initiale
      int l;
      int c;
      // modif sur ccOrig.l
      c = ccDest.column - 2;
      if (c > -1 && c < 8 && position.getAt(c, ccOrig.raw) == pieceCherchee) {
        Case ca = new Case(c, ccOrig.raw, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      c = ccDest.column + 2;
      if (c > -1 && c < 8 && position.getAt(c, ccOrig.raw) == pieceCherchee) {
        Case ca = new Case(c, ccOrig.raw, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      c = ccDest.column + 1;
      if (c > -1 && c < 8 && position.getAt(c, ccOrig.raw) == pieceCherchee) {
        Case ca = new Case(c, ccOrig.raw, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      c = ccDest.column - 1;
      if (c > -1 && c < 8 && position.getAt(c, ccOrig.raw) == pieceCherchee) {
        Case ca = new Case(c, ccOrig.raw, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      cr.setResultatCoherent(false);
      cr.setExtraInfoError("err.interpretation.TrouvePieceCavalier2");

    } else if (ccOrig.column != -1) {
      int l;
      int c;
      l = ccDest.raw - 2;
      if (l > -1 && l < 8 && position.getAt(ccOrig.column, l) == pieceCherchee) {
        Case ca = new Case(ccOrig.column, l, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      l = ccDest.raw + 2;
      if (l > -1 && l < 8 && position.getAt(ccOrig.column, l) == pieceCherchee) {
        Case ca = new Case(ccOrig.column, l, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      l = ccDest.raw + 1;
      if (l > -1 && l < 8 && position.getAt(ccOrig.column, l) == pieceCherchee) {
        Case ca = new Case(ccOrig.column, l, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      l = ccDest.raw - 1;
      if (l > -1 && l < 8 && position.getAt(ccOrig.column, l) == pieceCherchee) {
        Case ca = new Case(ccOrig.column, l, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      log.info("Piece cherchee " + pieceCherchee);
      cr.setExtraInfoError("err.interpretation.TrouvePieceCavalier3");
      cr.setResultatCoherent(false);
    }

    return cr;
  }


  private static CoupleResultat trouvePieceLigneColonne(Position position, Case ccOrig, Case ccDest,
                                                        char pieceCherchee) {
    int l;
    int c;

    CoupleResultat cr = new CoupleResultat();
    Set<Case> lst = cr.getLc();
    // faire les lignes et les colonnes si ligne precisee ne regarder que la
    // ligne
    // idem pour colonne
    if (ccOrig.column == -1 && ccOrig.raw == -1) {
      l = ccDest.raw - 1;
      c = ccDest.column;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 1;
      c = ccDest.column;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }

      return cr;
    } else if (ccOrig.raw != -1) {
      c = 0;
      while ((c > -1 && c < 8 && position.getAt(c, ccOrig.raw) != pieceCherchee)) {
        c++;
      }
      if (c > -1 && c < 8 && position.getAt(c, ccOrig.raw) == pieceCherchee) {
        Case ca = new Case(c, ccOrig.raw, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      cr.setExtraInfoError("err.interpretation.TrouvePieceTour2");
      cr.setResultatCoherent(false);
    } else if (ccOrig.column != -1) {
      l = 0;
      while ((l > -1 && l < 8 && position.getAt(ccOrig.column, l) != pieceCherchee)) {
        l++;
      }
      if (l > -1 && l < 8 && position.getAt(ccOrig.column, l) == pieceCherchee) {
        Case ca = new Case(ccOrig.column, l, pieceCherchee);
        lst.add(ca);
        return cr;
      }
      cr.setExtraInfoError("err.interpretation.TrouvePieceTour3");
      cr.setResultatCoherent(false);
    }
    //     cr.setExtraInfo("err.interpretation.TrouvePieceTour4");
    //     cr.setResultat(false);
    return cr;
  }

  private static CoupleResultat findPieceRawColAsQueen(Position position, Case ccOrig, Case ccDest,
                                                       char pieceCherchee) {
    int l;
    int c;

    CoupleResultat cr = new CoupleResultat();
    Set<Case> lst = cr.getLc();
    // faire les lignes et les colonnes si ligne precisee ne regarder que la
    // ligne
    // idem pour colonne
    if (ccOrig.column == -1 && ccOrig.raw == -1) {
      l = ccDest.raw - 1;
      c = ccDest.column;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 1;
      c = ccDest.column;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }

      return cr;
    } else if (ccOrig.raw != -1) {
      if (ccOrig.raw != ccDest.raw) {
        if (position.getAt(ccDest.column, ccOrig.raw) == pieceCherchee) {
          Case ca = new Case(ccDest.column, ccOrig.raw, pieceCherchee);
          lst.add(ca);
          return cr;
        }
        cr.setExtraInfoError("err.interpretation.TrouvePieceDame2");
        cr.setResultatCoherent(false);
        return cr;
      } else {

        c = 0;
        while ((c > -1 && c < 8 && position.getAt(c, ccOrig.raw) != pieceCherchee)) {
          c++;
        }
        if (c > -1 && c < 8 && position.getAt(c, ccOrig.raw) == pieceCherchee) {
          Case ca = new Case(c, ccOrig.raw, pieceCherchee);
          lst.add(ca);
          return cr;
        }
        cr.setExtraInfoError("err.interpretation.TrouvePieceDame2b");
        cr.setResultatCoherent(false);

      }

    } else if (ccOrig.column != -1) {
      if (ccOrig.column != ccDest.column) {
        if (position.getAt(ccOrig.column, ccDest.raw) == pieceCherchee) {
          Case ca = new Case(ccOrig.column, ccDest.raw, pieceCherchee);
          lst.add(ca);
          return cr;
        }
        cr.setExtraInfoError("err.interpretation.TrouvePieceDame3");
        cr.setResultatCoherent(false);
        return cr;
      } else {
        l = 0;
        while ((l > -1 && l < 8 && position.getAt(ccOrig.column, l) != pieceCherchee)) {
          l++;
        }
        if (l > -1 && l < 8 && position.getAt(ccOrig.column, l) == pieceCherchee) {
          Case ca = new Case(ccOrig.column, l, pieceCherchee);
          lst.add(ca);
          return cr;
        }
        cr.setExtraInfoError("err.interpretation.TrouvePieceDame4");
        cr.setResultatCoherent(false);
      }

    }

    return cr;
  }

  private static CoupleResultat findPieceDiag(Position position, Case ccOrig, Case ccDest,
                                              char pieceCherchee) {
    int l;
    int c;

    CoupleResultat cr = new CoupleResultat();
    Set<Case> lst = cr.getLc();
    if (ccOrig.column == -1 && ccOrig.raw == -1) {
      l = ccDest.raw - 1;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 1;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw - 1;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
      l = ccDest.raw + 1;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        lst.add(ca);
      }
    } else if (ccOrig.column != -1 && ccOrig.raw == -1) {
      l = ccDest.raw - 1;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.column == ccOrig.column) {
          lst.add(ca);
        }
      }
      l = ccDest.raw + 1;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.column == ccOrig.column) {
          lst.add(ca);
        }
      }
      l = ccDest.raw - 1;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.column == ccOrig.column) {
          lst.add(ca);
        }
      }
      l = ccDest.raw + 1;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.column == ccOrig.column) {
          lst.add(ca);
        }
      }
    } else if (ccOrig.column == -1 && ccOrig.raw != -1) {
      l = ccDest.raw - 1;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.raw == ccOrig.raw) {
          lst.add(ca);
        }
      }
      l = ccDest.raw + 1;
      c = ccDest.column - 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
        c--;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.raw == ccOrig.raw) {
          lst.add(ca);
        }
      }
      l = ccDest.raw - 1;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l--;
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.raw == ccOrig.raw) {
          lst.add(ca);
        }
      }
      l = ccDest.raw + 1;
      c = ccDest.column + 1;
      while ((PgnUtil.isInBorne(l, c) && position.getAt(c, l) == MetierConstants.CASE_VIDE)) {
        l++;
        c++;
      }
      if (PgnUtil.isInBorne(l, c) && position.getAt(c, l) == pieceCherchee) {
        Case ca = new Case(c, l, pieceCherchee);
        if (ca.raw == ccOrig.raw) {
          lst.add(ca);
        }
      }
    }
    return cr;
  }


  private static CoupleResultat findPieceBishop(Position position, Case ccOrig, Case ccDest) {

    char pieceCherchee =
        position.getJoueurAuTrait() == BLANC ? MetierConstants.FOU_BLANC : MetierConstants.FOU_NOIR;
    CoupleResultat cr = findPieceDiag(position, ccOrig, ccDest, pieceCherchee);
    if (cr.getLc().isEmpty()) {
      cr.setResultatCoherent(false);
      cr.setExtraInfoError("err.interpretation.TrouvePieceFou");
    }
    return cr;
  }


  private static CoupleResultat findPieceRook(Position position, Case ccOrig, Case ccDest) {

    char pieceCherchee = position.getJoueurAuTrait() == BLANC ? MetierConstants.TOUR_BLANC :
        MetierConstants.TOUR_NOIR;

    CoupleResultat cr = trouvePieceLigneColonne(position, ccOrig, ccDest, pieceCherchee);
    Set<Case> lst = cr.getLc();
    if (lst.isEmpty()) {
      cr.setResultatCoherent(false);
      //   cr.setExtraInfo("err.interpretation.TrouvePieceTour1");
    }
    return cr;
  }


  private static CoupleResultat findPieceQueen(Position position, Case ccOrig, Case ccDest) {
    char pieceCherchee = position.getJoueurAuTrait() == BLANC ? MetierConstants.DAME_BLANC :
        MetierConstants.DAME_NOIR;


    CoupleResultat cr = findPieceDiag(position, ccOrig, ccDest, pieceCherchee);
    Set<Case> lst = cr.getLc();
    CoupleResultat cr2 = findPieceRawColAsQueen(position, ccOrig, ccDest, pieceCherchee);

    lst.addAll(cr2.getLc());
    if (lst.isEmpty()) {
      cr.setResultatCoherent(false);
      cr.setExtraInfoError("err.interpretation.TrouvePieceDame");
    }

    return cr;
  }


  private static CoupleResultat findPieceKing(Position position) {
    CoupleResultat cr = new CoupleResultat();
    Set<Case> lst = cr.getLc();
    if (position.getJoueurAuTrait() == BLANC) {
      lst.add(position.getCaseRoiBlanc());
    } else {
      lst.add(position.getCaseRoiNoir());
    }
    return cr;

  }

  private static Piece findPiece(Position position, Case caset) {

    if (position.getJoueurAuTrait() == BLANC) {
      List<Piece> listPiece = position.getListePiece().getWhitePieces();
      for (Piece p : listPiece) {
        if (p.getActualPosition().column == caset.column &&
            p.getActualPosition().raw == caset.raw) {
          return p;
        }
      }
    } else {
      List<Piece> listPiece = position.getListePiece().getBlackPieces();
      for (Piece p : listPiece) {
        if (p.getActualPosition().column == caset.column &&
            p.getActualPosition().raw == caset.raw) {
          return p;
        }
      }
    }
    return null;
  }

  private static void manageCastle(ResultInterpretation res, Position position, String move) {

    List<Case> lstCaseResult = new ArrayList<>();

    if (res.getTypeCoup() == EnumTypeMove.GRAND_ROQUE) {
      int lg = 0;
      if (position.getJoueurAuTrait() == BLANC) {
        position.setHasWhiteRocked(true);
        position.setCaseRoiBlanc(new Case(2, 0, MetierConstants.ROI_BLANC));

        position.setWhiteGrandRoque(false);
        position.setWhitePetitRoque(false);

      } else {
        lg = 7;
        position.setHasBlackRocked(true);
        position.setCaseRoiNoir(new Case(2, 7, MetierConstants.ROI_NOIR));

        position.setBlackGrandRoque(false);
        position.setBlackPetitRoque(false);
      }

      // ligne,colonne => colonne ligne
      lstCaseResult.add(new Case(2, lg, position.getAt(4, lg)));
      Case case40 = new Case(4, lg, MetierConstants.CASE_VIDE);
      lstCaseResult.add(case40);
      lstCaseResult.add(new Case(3, lg, position.getAt(0, lg)));
      Case case00 = new Case(0, lg, MetierConstants.CASE_VIDE);
      lstCaseResult.add(case00);

      Piece roiPiece = findPiece(position, case40);
      roiPiece.getActualPosition().column = 2;

      Piece tourPiece = findPiece(position, case00);

      tourPiece.getActualPosition().column = 3;
      roiPiece.getMovesSinceStart().add(move);
      tourPiece.getMovesSinceStart().add(move);

    } else {
      int lg = 0;
      if (position.getJoueurAuTrait() == BLANC) {
        position.setHasWhiteRocked(true);
        position.setCaseRoiBlanc(new Case(6, lg, MetierConstants.ROI_BLANC));

        position.setWhiteGrandRoque(false);
        position.setWhitePetitRoque(false);
      } else {
        lg = 7;
        position.setHasBlackRocked(true);
        position.setCaseRoiNoir(new Case(6, lg, MetierConstants.ROI_NOIR));
        position.setBlackGrandRoque(false);
        position.setBlackPetitRoque(false);

      }

      lstCaseResult.add(new Case(6, lg, position.getAt(4, lg)));
      Case case40 = new Case(4, lg, MetierConstants.CASE_VIDE);
      lstCaseResult.add(case40);
      lstCaseResult.add(new Case(5, lg, position.getAt(7, lg)));
      Case case70 = new Case(7, lg, MetierConstants.CASE_VIDE);
      lstCaseResult.add(case70);

      Piece roiPiece = findPiece(position, case40);

      roiPiece.getActualPosition().column = 6;
      Piece tourPiece = findPiece(position, case70);

      roiPiece.getMovesSinceStart().add(move);

      tourPiece.getActualPosition().column = 5;
      tourPiece.getMovesSinceStart().add(move);

    }

    for (Case cas : lstCaseResult) {

      position.setAt(cas);
    }
  }

  public static ResultInterpretation play(Position position, String move) {

    //  System.out.println("avant joue :"+position.toString());

    Case caseSource = new Case();
    Case caseTarget = new Case();
    ResultInterpretation res = interprete(position, move, caseSource, caseTarget);

    // log.info("joue -> " + move + "--->" + caseSource);
    //   log.info("dans joue : "+move+" "+caseSource);
    if (res.isInvalide()) {
      log.info("le coup a probleme is " + move);
      return res;
    } else {

      // liste des choses � faire apres
      List<Case> lstCaseResult = null;

      position.setDestinationEnPassant(MetierConstants.TIRET);
      if (res.isPrise()) {
        position.setNbDemiCoupsCapture(0);
      }

      if (res.getTypeCoup() != EnumTypeMove.GRAND_ROQUE &&
          res.getTypeCoup() != EnumTypeMove.PETIT_ROQUE) {

        List<Case> lstSourcesPossibles = res.getLstSourcesPossibles();
        for (int index = 0; index < lstSourcesPossibles.size(); index++) {
          caseSource = lstSourcesPossibles.get(index);
          Case sauvOrig = new Case(caseSource);
          lstCaseResult = new ArrayList<>();

          if (null != res.getTypeCoup()) {
            switch (res.getTypeCoup()) {
              case COUP_NORMAL:
                caseTarget.pieceOnTheBoard = position.getAt(caseSource.column, caseSource.raw);
                lstCaseResult.add(caseTarget);
                caseSource.pieceOnTheBoard = MetierConstants.CASE_VIDE;
                lstCaseResult.add(caseSource);
                // si c'est un coup de pion est une avancee de 2 , mettre a
                // jour
                if (res.getPieceSource() == EnumPiece.PION &&
                    ((caseSource.raw - caseTarget.raw) * position.getSens() == 2)) {
                  String destinationEnPassant =
                      move.charAt(0) + ((position.getJoueurAuTrait() == NOIR) ? "6" : "3");
                  position.setDestinationEnPassant(destinationEnPassant);
                } else if (res.getPieceSource() == EnumPiece.ROI) {
                  // log.info("coup du roi:"+caseTarget+"//"+caseSource);

                  if (position.getJoueurAuTrait() == BLANC) {
                    position.setWhiteGrandRoque(false);
                    position.setWhitePetitRoque(false);
                    position.setCaseRoiBlanc(caseTarget);

                  } else {
                    position.setBlackGrandRoque(false);
                    position.setBlackPetitRoque(false);
                    position.setCaseRoiNoir(caseTarget);
                  }

                } else if (res.getPieceSource() == EnumPiece.TOUR) {
                  if (position.getJoueurAuTrait() == BLANC) {
                    if (caseSource.column == 7) {
                      position.setWhitePetitRoque(false);
                    } else if (caseSource.column == 0) {
                      position.setWhiteGrandRoque(false);
                    }

                  } else {
                    if (caseSource.column == 7) {
                      position.setBlackPetitRoque(false);
                    } else if (caseSource.column == 0) {
                      position.setBlackGrandRoque(false);
                    }
                  }
                }
                break;
              case PROMO_N:
                // log.info("2013/05/20: (b) promotion cavalier" + position);
                if (position.getJoueurAuTrait() == NOIR) {
                  caseTarget.pieceOnTheBoard = MetierConstants.CAVALIER_NOIR;
                } else {
                  caseTarget.pieceOnTheBoard = MetierConstants.CAVALIER_BLANC;
                }
                lstCaseResult.add(caseTarget);
                caseSource.pieceOnTheBoard = MetierConstants.CASE_VIDE;
                lstCaseResult.add(caseSource);

                break;
              case PROMO_R:
                if (position.getJoueurAuTrait() == NOIR) {
                  caseTarget.pieceOnTheBoard = MetierConstants.TOUR_NOIR;
                } else {
                  caseTarget.pieceOnTheBoard = MetierConstants.TOUR_BLANC;
                }
                lstCaseResult.add(caseTarget);
                caseSource.pieceOnTheBoard = MetierConstants.CASE_VIDE;
                lstCaseResult.add(caseSource);
                break;
              case PROMO_B:
                if (position.getJoueurAuTrait() == NOIR) {
                  caseTarget.pieceOnTheBoard = MetierConstants.FOU_NOIR;
                } else {
                  caseTarget.pieceOnTheBoard = MetierConstants.FOU_BLANC;
                }
                lstCaseResult.add(caseTarget);
                caseSource.pieceOnTheBoard = MetierConstants.CASE_VIDE;
                lstCaseResult.add(caseSource);
                break;
              case PROMO_Q:
                if (position.getJoueurAuTrait() == NOIR) {
                  caseTarget.pieceOnTheBoard = MetierConstants.DAME_NOIR;
                } else {
                  caseTarget.pieceOnTheBoard = MetierConstants.DAME_BLANC;
                }
                lstCaseResult.add(caseTarget);
                caseSource.pieceOnTheBoard = MetierConstants.CASE_VIDE;
                lstCaseResult.add(caseSource);
                break;
              case PEP:
                caseTarget.pieceOnTheBoard = position.getAt(caseSource.column, caseSource.raw);
                lstCaseResult.add(caseTarget);
                caseSource.pieceOnTheBoard = MetierConstants.CASE_VIDE;
                lstCaseResult.add(caseSource);
                lstCaseResult.add(
                    new Case(caseTarget.column, caseSource.raw, MetierConstants.CASE_VIDE));
                break;
              default:
                break;
            }
          }

          List<Case> savedCases = new ArrayList<>();
          for (Case cas : lstCaseResult) {
            savedCases.add(new Case(cas.column, cas.raw, position.getAt(cas.column, cas.raw)));
            position.setAt(cas);
          }
          boolean isEchec = RulesManager.isCheck(position, position.getJoueurAuTrait());

          savedCases.forEach((cas) -> {
            position.setAt(cas);
          });

          if (!isEchec) {
            // si y'a echec fo recommencer
            index = lstSourcesPossibles.size(); // on sort de la boucle
            Piece pieceDestination = null;
            if (position.getJoueurAuTrait() == NOIR) {
              List<Piece> pieces = position.getListePiece().getWhitePieces();
              for (Piece p : pieces) {
                if (p.getActualPosition().column == caseTarget.column &&
                    p.getActualPosition().raw == caseTarget.raw) {
                  pieceDestination = p;
                  break;
                }
              }
              if (pieceDestination != null) {

                position.getListePiece().removeWhitePiece(pieceDestination);
              }
            } else {
              List<Piece> pieces = position.getListePiece().getBlackPieces();
              for (Piece p : pieces) {
                if (p.getActualPosition().column == caseTarget.column &&
                    p.getActualPosition().raw == caseTarget.raw) {
                  pieceDestination = p;
                  break;
                }
              }
              if (pieceDestination != null) {

                position.getListePiece().removeBlackPiece(pieceDestination);

              }
            }
            Piece piecePartante = findPiece(position, sauvOrig);

            if (piecePartante != null) {

              piecePartante.getActualPosition().column = caseTarget.column;
              piecePartante.getActualPosition().raw = caseTarget.raw;
              piecePartante.getMovesSinceStart().add(move);

            }
            // cas particulier Prise EP et PROMOTION
            if ((res.getTypeCoup() != EnumTypeMove.PEP &&
                res.getTypeCoup() != EnumTypeMove.COUP_NORMAL) &&
                (piecePartante != null && null != res.getTypeCoup())) {
              switch (res.getTypeCoup()) {
                case PROMO_Q:
                  piecePartante.setPromotionIn(EnumPiece.REINE);
                  break;
                case PROMO_R:
                  piecePartante.setPromotionIn(EnumPiece.TOUR);
                  break;
                case PROMO_B:
                  piecePartante.setPromotionIn(EnumPiece.FOU);
                  break;
                case PROMO_N:
                  //      log.info("2013/05/20: (c) promotion cavalier" + position);
                  piecePartante.setPromotionIn(EnumPiece.CAVALIER);
                  break;
                default:
                  break;
              }
            }
            // fin sauv liste piece
            for (Case cas : lstCaseResult) {

              position.setAt(cas);

            }
          }
        } // en for

      } else {
        manageCastle(res, position, move);
      }

      if (position.getJoueurAuTrait() == BLANC) {
        position.setJoueurAuTrait(NOIR);
        position.setNbCoups(position.getNbCoups() + 1);
      } else {
        position.setJoueurAuTrait(BLANC);
      }
      position.getMoves().add(move);
      position.setLastMove(move);
    }
    return res;
  }


}
