package com.xoff.chessvger.util;

import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.Case;
import com.xoff.chessvger.chess.board.CoupleResultat;
import com.xoff.chessvger.chess.board.MetierConstants;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.exception.FindPieceException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PgnUtil {

  //-Des commentaires peuvent être ajoutés entre accolades {...}
  // -Des variantes peuvent être ajoutées entre parenthèses (...)
  public static final char START_VARIATION = '(';
  public static final char END_VARIATION = ')';
  public static final char START_COMMENT = '{';
  public static final char END_COMMENT = '}';

  public static final String[] MARKS_APPRECIATION = {"", "!", "?", "!!", "??", "?!", "!?"};
  public static final String[] MARKS_EVALUATION = {"", "+-", "-+", "="};

  protected static final String COMMENT_UNIT = "!#+?";
  protected static final int lCOMMENT_UNIT = COMMENT_UNIT.length();

  public static String playerDenudeName(String origine) {
    return origine.replaceAll(",", "").replaceAll(" ", "").toLowerCase();

  }

  private static String formatList(List<String> coups, int numeroInitial, boolean traitAuxNoirs) {
    //  log.info("resultat:" + numeroInitial);
    StringBuilder resultat = new StringBuilder();
    int n = (numeroInitial + 1) * 2;
    if (!traitAuxNoirs) {
      resultat.append(".. ");
    } else {

      resultat.append(numeroInitial + 1).append(". ");
      n++;
    }
    //log.info("numero initial :"+numeroInitial);
    int compteur = coups.size();
    for (String coup : coups) {

      resultat.append(coup).append(" ");
      n++;
      compteur--;
      if (compteur != 0 && n % 2 != 0) {
        resultat.append(n / 2).append(". ");
      }
    }
    //    log.info("resultat:" + resultat);
    return resultat.toString();
  }


  private static String getPrecisionLigneOuColonne(CoupleResultat cr, String coupAuFormatSan) {

    if (!cr.isResultatCoherent()) {

      return StringUtils.EMPTY;
    }

    if (cr.getLc().size() > 1) {

//on a deux elements, il faut choisir le bon

      for (Case cc : cr.getLc()) {


        if (int2letter(cc.column + 1) == coupAuFormatSan.charAt(0) &&
            cc.raw == letter2int(coupAuFormatSan.charAt(1))) {
          // maintenant on va voir s'il faut faire la precision sur la ligne ou la colonne


          for (Case ccInside : cr.getLc()) {
            /*
            if (coupAuFormatSan.equals("f1d1")) {
            log.info(coupAuFormatSan +":dedans:" + int2letter(cc.c + 1)
            + "-"+(cc.l+1)+ "egalite"+ ccInside.equals(cc));

          }*/
            if (!ccInside.equals(cc)) {
              if (ccInside.raw == cc.raw) {
                //   log.info("precision a:" + String.valueOf(cc.l));
                return String.valueOf(int2letter(cc.column + 1));
              } else {
                //     log.info("precision b:" + int2letter(cc.c));
                return String.valueOf(cc.raw + 1);
              }
            }
          }
        }
      }
    }

    return StringUtils.EMPTY;
  }


  public static String convert1MoveSan2Pgn(Position position, String coupAuFormatSan) {
    if (coupAuFormatSan.length() >= 4) {
      //   log.info("position=" + position);

      int boardLine = Integer.parseInt(String.valueOf(coupAuFormatSan.charAt(1))) - 1;

      char source = position.getAt(letter2int(coupAuFormatSan.charAt(0)), boardLine);

      boolean isPion =
          source == MetierConstants.CASE_VIDE || source == MetierConstants.PION_BLANC ||
              source == MetierConstants.PION_NOIR;

      int boardLineD = Integer.parseInt(String.valueOf(coupAuFormatSan.charAt(3))) - 1;
      char dest = position.getAt(letter2int(coupAuFormatSan.charAt(2)), boardLineD);
      //      log.info("convert1MoveSan2Pgn:" + dest);
      String milieu = "";

      if (dest != MetierConstants.CASE_VIDE) {
        milieu = "x";
      }
      if (isPion) {
        //    log.info("ligne coup cas a1:" + coup);
        if (StringUtils.isEmpty(milieu)) {
          String res = "" + coupAuFormatSan.charAt(2) + coupAuFormatSan.charAt(3);
          // log.info("convert1MoveSan2Pgn a  :" + coup + "-> " + res);
          return res;
        } else {

          String res = coupAuFormatSan.charAt(0) + milieu + coupAuFormatSan.charAt(2) +
              coupAuFormatSan.charAt(3);
          /*
          if (source == MetierConstants.CASE_VIDE) { // comment c'est possible ?
              log.info("IMPOSSIBLE convert1MoveSan2Pgn b : source " + source
               + " dest =" + dest + " letter2int(coup.charAt(0) ="
               + letter2int(coupAuFormatSan.charAt(0)) + " coup="
               + coupAuFormatSan + " boardLine =" + boardLine
               + "  -> " + res);
              log.info("IMPOSSIBLE convert1MoveSan2Pgn b : position " + position);
              log.info(" lm =" + position.strListeMoves());
          }*/
          return res;
        }

      } else {
        //   log.info("ligne coup cas a2:" + coup);
        // le trouve piece ne s'attend pas à ce qu'on ait toutes les infos, (sinon on chercherait pas)
        try {
          Case csource = new Case(-1, -1, source);
          Case cdest =
              new Case(letter2int(coupAuFormatSan.charAt(2)), letter2int(coupAuFormatSan.charAt(3)),
                  source);

          CoupleResultat cr =
              BoardManager.findPiece(position, Character.toUpperCase(source), csource, cdest);
          // le coup a deja ete joue en fait ?
          String precision = getPrecisionLigneOuColonne(cr, coupAuFormatSan);
          //     log.info(" uu 182 precision " + precision + "-" + cr);
          String resultat1 =
              String.valueOf(Character.toUpperCase(source)).toUpperCase() + precision + milieu +
                  coupAuFormatSan.charAt(2) + coupAuFormatSan.charAt(3);

          if (!cr.isResultatCoherent()) {
            log.info("------------------------------" + resultat1);
            log.info(
                " cr.isResultat() pb convert1MoveSan2Pgn b : source " + source + " dest =" + dest +
                    " coupAuFormatSan=" + coupAuFormatSan);
            log.info("pb convert1MoveSan2Pgn b : position " + position);
            log.info(" pb =" + position.strListeMoves());
          }
          if (resultat1.equals("Kg8") && position.getAt(5, 7) == MetierConstants.CASE_VIDE) {
            resultat1 = "0-0";
          } else if (resultat1.equals("Kg1") && position.getAt(5, 0) == MetierConstants.CASE_VIDE) {
            resultat1 = "0-0";
          } else if (resultat1.equals("Kc8") && position.getAt(2, 7) == MetierConstants.CASE_VIDE) {
            resultat1 = "0-0-0";
          } else if (resultat1.equals("Kc1") && position.getAt(2, 0) == MetierConstants.CASE_VIDE) {
            resultat1 = "0-0-0";
          }
          /*
          if (position.getPlayer() == MetierConstants.BLANC) {
              position.setPlayer(MetierConstants.NOIR);
          } else {
              position.setPlayer(MetierConstants.BLANC);

          }
           */
          return resultat1;
        } catch (FindPieceException tpe) {
          log.error(tpe.getMessage());
        }
        return StringUtils.EMPTY;
      }
    } else {
      // un roque ? depart et arrivée du roi soit Kg8 Kc8
      log.info("ligne coup cas b1:" + coupAuFormatSan);
      coupAuFormatSan = coupAuFormatSan + "<E>";
    }
    return coupAuFormatSan;
  }

  public static String convertSan2Pgn(Position position, String ligneEvaluee) {
    String[] coups = ligneEvaluee.split(" ");
    // log.info("aaa convertSan2Pgn: etat de depart:" + position + "--" + ligneEvaluee);
    Position locale = position.duplicate();

    if (coups.length > 0) {
      List<String> lcoups = new ArrayList();
      for (String coup : coups) {
        // on inverse car lors de l'analyse on n' a pas encore joue le coup
        // !!!il faut jouer les coups pour pouvoir les interpreter correctement
        try {
          String conb = convert1MoveSan2Pgn(locale, coup);
          //log.info("convertSan2Pgn: avant jouer:::" + coup + " - " + conb + " -" + locale);
          BoardManager.play(locale, conb);

          lcoups.add(conb);


        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
          log.error("convertSan2Pgn plantage ici =" + coup + "-" + locale);
          return "san2pgn:" + ligneEvaluee;
        }
      }

      return formatList(lcoups, position.getNbCoups(),
          position.getJoueurAuTrait() == MetierConstants.BLANC);
    }

    return "san2pgn:" + ligneEvaluee;
  }


  public static String determineMove(Position p, int colOrig, int ligOrig, int colDest,
                                     int ligDest) {

    StringBuilder move = new StringBuilder();
    if (p.getAt(colOrig, ligOrig) != MetierConstants.PION_BLANC &&
        p.getAt(colOrig, ligOrig) != MetierConstants.PION_NOIR) {
      String s = String.valueOf(p.getAt(colOrig, ligOrig));
      move.append(s.toUpperCase());
    }

    if (p.getAt(colDest, ligDest) != MetierConstants.CASE_VIDE) {
      if (p.getAt(colOrig, ligOrig) == MetierConstants.PION_BLANC ||
          p.getAt(colOrig, ligOrig) == MetierConstants.PION_NOIR) {
        move.append(int2letter(colOrig + 1));
      }
      move.append("x");
    }
    move.append(int2letter(colDest + 1));
    move.append(ligDest + 1);
    // roque ?
    String sk = String.valueOf(p.getAt(colOrig, ligOrig));
    log.info(move + " sj=" + sk + "--->" + Math.abs(colOrig - colDest));

    if (sk.equalsIgnoreCase("K")) {
      if (Math.abs(colOrig - colDest) == 2) {
        if (colDest > colOrig) {
          move = new StringBuilder("0-0");
        } else {
          move = new StringBuilder("0-0-0");
        }
      }
    }
    if (sk.equalsIgnoreCase("P") && (ligDest == 0 || ligDest == 7)) {
      move.append("=");
    }
    log.info("proposition du coup " + move);
    return move.toString();
  }


  public static boolean isInBorne(int l, int c) {
    return (l > -1 && l < 8 && c > -1 && c < 8);
  }


  public static int letter2int(char c) {
    switch (c) {
      case 'd':
      case '4':
        return 3;
      case 'e':
      case '5':
        return 4;
      case 'a':
      case '1':
        return 0;
      case 'b':
      case '2':
        return 1;
      case 'c':
      case '3':
        return 2;
      case 'f':
      case '6':
        return 5;
      case 'g':
      case '7':
        return 6;
      case 'h':
      case '8':
        return 7;
      default:
        // erreur
    }
    log.error("letter2int:" + c);
    return -1;
  }

  public static char int2letter(int c) {
    switch (c) {
      case 1:
        return 'a';
      case 2:
        return 'b';
      case 3:
        return 'c';
      case 4:
        return 'd';
      case 5:
        return 'e';
      case 6:
        return 'f';
      case 7:
        return 'g';
      case 8:
        return 'h';

      default:
        // erreur
    }
    log.error("int2letter:" + c);
    return '#';
  }


  public static int interpreteValue(String s) {

    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static boolean isScore(String s) {
    return Constants.RESULT_1_0.equals(s) || Constants.RESULT_0_1.equals(s) ||
        Constants.RESULT_1_2.equals(s);
  }


  private static boolean isPonctuation(char c) {
    return c == '+' || c == '?' || c == '!';
  }


  public static String[] extractMovesFromString(String ls) {

    List<String> sb = new ArrayList();
    StringBuilder current = new StringBuilder();

    boolean isComment = false;
    boolean isInMove = false;
    int indice = 0;
    boolean finish = false;
    if (ls == null) {
      log.info("list null");

      String[] stockArr = new String[0];
      return stockArr;
    }
    for (char c : ls.toCharArray()) {

      switch (c) {
        case START_COMMENT:

          isComment = true;
          break;
        case END_COMMENT:

          isComment = false;
          break;
        case START_VARIATION:

          break;
        case END_VARIATION:

          isInMove = false;
          break;
        default:

          if (!isComment && !isPonctuation(c)) {
            if (c == ' ') {

              if (isInMove) {
                //   allList.append(SEP);

                sb.add(current.toString().trim());
                current.setLength(0);
              }
              isInMove = false;

            } else {
              // score ou coup ou numero de coup
              if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' ||
                  c == '6' || c == '7' || c == '8' || c == '9') {

                if (!isInMove) {
                  // numero coup ou  SCORE
                  //score
                  if (c == '1') {
                    if (indice + 1 < ls.length() && ls.charAt(indice + 1) == '-') {

                      //       allList.append(ls.substring(indice));
                      finish = true;
                      break;  // switch
                    }
                  } else if (c == '0') {
                    if (indice + 1 < ls.length() && ls.charAt(indice + 1) == '-') {
                      //   allList.append(ls.substring(indice));
                      finish = true;
                      break; // switch
                    }
                  }
                } else {
                  // move
                  current.append(c);
                }
              } else if (c == '.') {

                isInMove = false;

              } else { // n importe auel autre caractere

                if (isInMove) {
                  if (c != '#') {
                    current.append(c);
                  }

                } else {

                  isInMove = true;
                  current.append(c);
                }
              }

            }
          }

      }
      indice++;
      if (finish) {

        break;
      }
    }

    String[] stockArr = new String[sb.size()];
    stockArr = sb.toArray(stockArr);
    return stockArr;
  }


  public static int decodeURLDemiCoup(String s) {
    //   log.info("s=" + s);
    int i = s.indexOf("=") + 1;
    if (i > 0) {
      try {
        //  log.info("decodeURLDemiCoup->" + s.substring(i));
        return Integer.parseInt(s.substring(i).trim());
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    return 0;
  }

  public static String removeComment(String strRes) {

    StringBuilder result = new StringBuilder();

    for (int j = 0; j < strRes.length(); j++) {
      // efface les caracteres à la con
      boolean trouve = false;
      for (int i = 0; i < lCOMMENT_UNIT; i++) {
        if (strRes.charAt(j) == COMMENT_UNIT.charAt(i)) {
          trouve = true;
          break;
        }
      }
      if (!trouve) {
        result.append(strRes.charAt(j));
      }

    }

    return result.toString();
  }
}