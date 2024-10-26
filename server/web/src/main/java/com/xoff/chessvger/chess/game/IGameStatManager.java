package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.ui.web.view.StatGame;
import java.util.List;

public interface IGameStatManager {
  public StatGame getStatGame(List<CommonGame> list);
}
