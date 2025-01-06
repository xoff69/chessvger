package com.xoff.chessvger.chess.opening;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.PgnUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class OpeningHelper {


  public static List<String> codeECO() {
    List<String> result = new ArrayList();
    String[] lettre = {"A", "B", "C", "D", "E"};
    for (String a : lettre) {
      for (int i = 0; i < 100; i++) {
        if (i < 10) {
          result.add(a + "0" + i);
        } else {
          result.add(a + i);
        }
      }
    }
    return result;
  }

  /**
   * renvoie les informations sur l'ouverture
   */
  public Opening getInfoOpening(CommonGame game) {


    if (!StringUtils.isEmpty(game.getEco())) {
      Opening op = GlobalManager.getInstance().getOpeningManager().findOpening(game.getEco());
      return op;
    } else {
      //-     log.info("game=" + game);
      String[] lm = PgnUtil.extractMovesFromString(game.getMoves());
      List<String> ls = Arrays.asList(lm);
      Opening op = getInfoOpening(ls);
      return op;
    }
  }


  private Opening getInfoOpening(List<String> moves) {

    //*      log.info("getInfoOpening");
    if (moves.isEmpty() || moves.size() < 2) {
      return new Opening();
    }

    return GlobalManager.getInstance().getOpeningManager().findOpening(moves);
  }
}
