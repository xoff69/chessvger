package com.xoff.chessvger.util;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

  public static final char YES = 'y';
  public static final char NO = 'n';
  public static final char NA = '-';

  public static final String NB_GAMES = "nbgames";

  public static final long ID_MIN_NON_FIDE_PLAYER = 10_000_000_000L;
  public static final String[] INTERET = {"-", "1", "2", "3", "4", "5"};

  public static final int NON_SIGNIFICATIVE_INT = -1;
  public static final String NONFIDE = "NF";
  public static final String TODEL = "todel.txt";
  public static final String PRESENT = "PRESENT";


  public static final int RATINGFORFAMOUS = 2450;


  public static final int OPENING_TENDANCE_NUMBER = 5;
  public static final int OPENING_FAVORITES_NUMBER = 10;

  public static final int SIZE_ELO = 6;
  public static final int SIZE_FIDE = 8;
  /**
   * utilise pour les map et le fichier de proprietes
   */
  public static final String MAP_SEP = "#";

  public static final String MODE_DEBUG = "DEBUG";
  public static final String MODE_RUN = "RUN";

  public static final List<String> ALL_FIRST_MOVE = Collections.unmodifiableList(
      Arrays.asList("e4", "d4", "c4", "Nf3", "Nc3", "g3", "b3", "f4", "a3", "a4", "b4", "c3", "d3",
          "e3", "f3", "g4", "h3", "h4", "Na3", "Nh3"));

  public static final String FORMAT_DATE_PGN = "YYYY.MM.dd";

  public static final int TAILLE_MAX_CHAINE = 30;
  public static final int TAILLE_MAX_DATE = 4;
  public static final int TAILLE_MAX_INT = 6;
  public static final String CVDB_FILE = "cvdb";
  public static final String CVDB_EXT = "." + CVDB_FILE;
  public static final String PGN_FILE = "pgn";
  public static final String ZIP_FILE = "zip";


  public static final String KEY_SFX = "_key.cvd";


  public static final String PSTAT_SFX = "_pstat_";


  public static final String POSITION_SFX = "_position.cvd";

  public static final String MAP_SFX = ".map";

  public static final String RESULT_1_0 = "1-0";
  public static final String RESULT_1_2 = "1/2-1/2";
  public static final String RESULT_0_1 = "0-1";

  public static final int INF_SEL = 1;
  public static final int EGAL_SEL = 2;
  public static final int SUP_SEL = 3;
  public static final int BETWEEN_SEL = 4;

  public static final int NO_SEL = 0;
  public static final List<String> FIDE_TITLE = Collections.unmodifiableList(
      Arrays.asList("-", "GM", "IGM", "WGM", "IM", "FM", "CM", "NM", "WIM", "WFM", "WCM"));

}
