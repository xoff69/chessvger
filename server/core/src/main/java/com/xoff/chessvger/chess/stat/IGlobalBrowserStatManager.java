package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.filter.QuickFilter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.view.StatBrowserView;
import java.util.List;

public interface IGlobalBrowserStatManager {

  void put(String first, IBrowserStatManager b);

  IBrowserStatManager get(String first);

  void clear();

  void finish();

  List<CommonGame> gameOfASB(QuickFilter qf);

  List<StatBrowserView> getListSBForFilter(QuickFilter qf);

  List<StatBrowserView> buildTreeFromGame(List<CommonGame> games);
}
