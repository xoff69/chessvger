package com.xoff.chessvger.chess.board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RulesManager {


  private static boolean isCheckByPawn(Position position, char currentPlayer, Case casePosRoi) {

    char caseChercheePion = MetierConstants.PION_NOIR;
    if (currentPlayer == MetierConstants.BLANC) {
      caseChercheePion = MetierConstants.PION_BLANC;
    }

    int sens = -1;
    if (currentPlayer == MetierConstants.BLANC) {
      sens = 1;
    }
    int l;
    int c;
    l = casePosRoi.raw - sens;
    c = casePosRoi.column + 1;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseChercheePion) {
      return true;
    }

    c = casePosRoi.column - 1;
    return l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseChercheePion;
  }

  /**
   * isCheckByRookBishopQueen
   */
  private static boolean isCheckByRookBishopQueen(Position position, char currentPlayer,
                                                  Case casePosRoi) {


    char caseChercheeTour = MetierConstants.TOUR_NOIR;
    char caseChercheeDame = MetierConstants.DAME_NOIR;
    char caseChercheeFou = MetierConstants.FOU_NOIR;
    if (currentPlayer == MetierConstants.BLANC) {
      caseChercheeTour = MetierConstants.TOUR_BLANC;
      caseChercheeFou = MetierConstants.FOU_BLANC;
      caseChercheeDame = MetierConstants.DAME_BLANC;
    }
    int l;
    int c;
    // lignes : tour ou dame
    l = casePosRoi.raw + 1;
    c = casePosRoi.column;
    while (l < 8 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      l++;
    }
    if ((l < 8) &&
        (position.getAt(c, l) == caseChercheeTour || position.getAt(c, l) == caseChercheeDame)) {
      return true;
    }

    l = casePosRoi.raw - 1;
    //  c = casePosRoi.c;
    while (l > -1 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      l--;
    }
    if ((l != -1) &&
        (position.getAt(c, l) == caseChercheeTour || position.getAt(c, l) == caseChercheeDame)) {
      return true;

    }
    // colonnes : tour ou dame
    l = casePosRoi.raw;
    c = casePosRoi.column + 1;
    while (c < 8 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      c++;
    }
    if ((c < 8) &&
        (position.getAt(c, l) == caseChercheeTour || position.getAt(c, l) == caseChercheeDame)) {
      return true;

    }
    //l = casePosRoi.l;
    c = casePosRoi.column - 1;
    while (c > -1 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      c--;
    }
    if ((c != -1) &&
        (position.getAt(c, l) == caseChercheeTour || position.getAt(c, l) == caseChercheeDame)) {
      return true;

    }
    // diagonales fou ou dame
    // colonnes : tour ou dame
    // +1 +1
    l = casePosRoi.raw + 1;
    c = casePosRoi.column + 1;
    while (c < 8 && l < 8 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      c++;
      l++;
    }
    if ((c < 8 && l < 8) &&
        (position.getAt(c, l) == caseChercheeFou || position.getAt(c, l) == caseChercheeDame)) {
      return true;

    }
    // +1 -1
    l = casePosRoi.raw + 1;
    c = casePosRoi.column - 1;
    while (c > -1 && l < 8 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      c--;
      l++;
    }
    if ((c > -1 && l < 8) &&
        (position.getAt(c, l) == caseChercheeFou || position.getAt(c, l) == caseChercheeDame)) {
      return true;

    }
    // -1 +1
    l = casePosRoi.raw - 1;
    c = casePosRoi.column + 1;
    while (c < 8 && l > -1 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      c++;
      l--;
    }
    if ((c < 8 && l > -1) &&
        (position.getAt(c, l) == caseChercheeFou || position.getAt(c, l) == caseChercheeDame)) {
      return true;

    }
    // -1 -1
    l = casePosRoi.raw - 1;
    c = casePosRoi.column - 1;
    while (c > -1 && l > -1 && position.getAt(c, l) == MetierConstants.CASE_VIDE) {
      c--;
      l--;
    }
    return (c > -1 && l > -1) &&
        (position.getAt(c, l) == caseChercheeFou || position.getAt(c, l) == caseChercheeDame);
  }

  private static boolean isCheckKnight(Position position, char currentPlayer, Case casePosRoi) {


    char caseCherchee = MetierConstants.CAVALIER_NOIR;
    if (currentPlayer == MetierConstants.BLANC) {
      caseCherchee = MetierConstants.CAVALIER_BLANC;
    }
    int l;
    int c;
    l = casePosRoi.raw - 2;
    c = casePosRoi.column - 1;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;
    }
    l = casePosRoi.raw + 2;
    //    c = casePosRoi.c - 1;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;

    }
    //      l = casePosRoi.l + 2;
    c = casePosRoi.column + 1;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;
    }
    l = casePosRoi.raw - 2;
    //     c = casePosRoi.c + 1;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;
    }
    l = casePosRoi.raw - 1;
    c = casePosRoi.column + 2;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;
    }
    //      l = casePosRoi.l - 1;
    c = casePosRoi.column - 2;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;
    }
    l = casePosRoi.raw + 1;
    //    c = casePosRoi.c - 2;
    if (l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee) {

      return true;
    }
    //    l = casePosRoi.l + 1;
    c = casePosRoi.column + 2;
    return l > -1 && l < 8 && c > -1 && c < 8 && position.getAt(c, l) == caseCherchee;

  }

  public static boolean isCheck(Position position, char currentPlayer) {

    boolean echec = false;
    try {
      Case roiPos = position.getCaseRoiBlanc();

      char oppcouleur = MetierConstants.NOIR;
      if (currentPlayer != MetierConstants.BLANC) {
        oppcouleur = MetierConstants.BLANC;
        roiPos = position.getCaseRoiNoir();
      }
      // pion
      echec = isCheckByPawn(position, oppcouleur, roiPos);

      // cavalier
      if (!echec) {
        echec = isCheckKnight(position, oppcouleur, roiPos);
      }
      // fou, dame, tour
      if (!echec) {
        echec = isCheckByRookBishopQueen(position, oppcouleur, roiPos);
      }

    } catch (Exception e) {
      log.error("isCheck Erreur:", e);
    }
    return echec;
  }


}
