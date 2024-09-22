package com.xoff.chessvger.common;

import com.xoff.chessvger.EnvManager;

public class ParamConstants {

  public static final int NB_POSITION_MAP = 100;


  // @Value("${chessvger.browser_maxfirst_size}")
  public static final int MAX_FIRST_MOVE = 10;
  // @Value("${chessvger.listGames_size}")
  public static final int MAX_RESULT = 1000;
  /**
   * FIXME
   */
  public static final String SF_PATH =
      "C:/mesapplis/stockfish_15_win_x64_avx2/stockfish_15_win_x64_avx2/stockfish_15_x64_avx2.exe";


  private static final String RUN_FOLDER =
      EnvManager.getInstance().getValue(EnvManager.RUN_FOLDER_PARAM);
  public static final String WORK_DIR = RUN_FOLDER + "/work/";
  public static final String PATH_IMPORT = WORK_DIR + "/import/";

  public static final String PATH_PGN = WORK_DIR + "/pgn/";
  public static final String REP_REJET = RUN_FOLDER + "/reject";
  public static final String OPENING_PATH = RUN_FOLDER + "/reference/openings.xml";
  public static final String PLAYERS_FOLDER = RUN_FOLDER + "/reference/";
  public static final String PLAYERS_PATH = RUN_FOLDER + "/reference/players_list_foa.txt";
  public static final String SYNONYM_PATH = RUN_FOLDER + "/reference/synonym.txt";
  public static final String REP_TRACE = RUN_FOLDER + "/trace";
  public static final String DATA_FOLDER_COMMON = RUN_FOLDER + "/common/";

  public static final String DATA_FOLDER_DB = RUN_FOLDER + "/db/";

}
