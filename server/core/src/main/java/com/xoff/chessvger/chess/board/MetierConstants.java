package com.xoff.chessvger.chess.board;


public class MetierConstants {

  public static final String INIT_BOARD =
      "RNBQKBNRPPPPPPPP................................pppppppprnbqkbnr";
  /**
   * petit roque
   */
  public static final String PETIT_ROQUE = "0-0";
  /**
   * grand roque
   */
  public static final String GRAND_ROQUE = "0-0-0";
  /**
   * chaine vide
   */
  public static final String EMPTY = "";
  /**
   * tiret
   */
  public static final String TIRET = "-";
  public static final char CASE_VIDE = '.';
  public static final char PION_BLANC = 'P';
  public static final char TOUR_BLANC = 'R';
  public static final char CAVALIER_BLANC = 'N';
  public static final char FOU_BLANC = 'B';
  public static final char ROI_BLANC = 'K';
  public static final char DAME_BLANC = 'Q';
  public static final char PION_NOIR = 'p';
  public static final char TOUR_NOIR = 'r';
  public static final char CAVALIER_NOIR = 'n';
  public static final char FOU_NOIR = 'b';
  public static final char ROI_NOIR = 'k';
  public static final char DAME_NOIR = 'q';
  public static final int SIZE = 64;
  public static final char BLANC = 'W';
  public static final char NOIR = 'B';
  public static final String EP = "ep";

  private MetierConstants() {
  }
}
