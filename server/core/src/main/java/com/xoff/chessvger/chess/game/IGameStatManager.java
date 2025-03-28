package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.model.CommonGame;
import com.xoff.chessvger.view.StatGame;
import java.util.List;

public interface IGameStatManager {
  StatGame getStatGame(List<CommonGame> list);
}
