package com.xoff.chessvger.chess.game;


import com.xoff.chessvger.ui.web.view.StatGame;
import com.xoff.chessvger.util.Constants;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class GameStatManager implements IGameStatManager {


  public StatGame getStatGame(List<CommonGame> list) {
    StatGame stat = new StatGame();
    stat.setNbgames(list.size());

    //log.info("gsg=" + stat);
    for (CommonGame g : list) {
      switch (g.getResult()) {
        case Constants.RESULT_1_0:
          stat.setNbblanc(stat.getNbblanc() + 1);
          break;
        case Constants.RESULT_0_1:
          stat.setNbnul(stat.getNbnul() + 1);
          break;
        default:
          stat.setNbnoir(stat.getNbnoir() + 1);
          break;
      }
    }
    //  log.info("gsg=" + stat);
    return stat;
  }
}
