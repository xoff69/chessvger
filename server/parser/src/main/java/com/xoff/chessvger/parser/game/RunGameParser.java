/*
 * This source file was generated by the Gradle 'init' task
 */

package com.xoff.chessvger.parser.game;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class RunGameParser implements Runnable {

  private final String folder;

  public RunGameParser(String folder) {
    this.folder = folder;
  }

  /**
   * @param filedir ex /data/twic114
   */
  private static void manageFile(String filedir) {
    System.out.println("games " + filedir);
    CommonGameDao commonGameDao = new CommonGameDao();
    Parser parser = new Parser();
    long start = System.currentTimeMillis();
    List<CommonGame> games = parser.parseDir(new File(filedir));

    long finish1 = System.currentTimeMillis();
    long timeElapsed = (finish1 - start) / 1000;
    System.out.println("after parse games done: " + games.size() + ":" + timeElapsed + " s");


    long id = 1L;
    for (CommonGame game : games) {

      game.setId(id++);

      try {
        commonGameDao.insertCommonGame(game);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
      // mettre a jour le reconciliation manager
      // envoyer sur les stats browser
      // envoyer game of player
      // List<CoupleZobristMaterial> list = MaterialPositionsUtil.parseMoves2(game.getMoves());
      // PositionMaterialProducer.enqueuePositionMaterial(game.getId(), list);
      //enqueueGameOfAPlayer(game.getId(), game.getWhiteFideId());
      //enqueueGameOfAPlayer(game.getId(), game.getBlackFideId());
      //ReconciliationManager.update(game.getId(), ReconciliationType.GAME);
    }
    long finish2 = System.currentTimeMillis();
    timeElapsed = (finish2 - finish1) / 1000;
    System.out.println("db insert games done " + games.size() + ":" + timeElapsed + " s");
  }

  @Override
  public void run() {

    manageFile(folder);
  }


}