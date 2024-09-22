package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.chess.filter.QuickFilter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.ui.web.view.StatBrowserView;
import java.util.List;

public interface IGlobalBrowserStatManager {

  public void put(String first, IBrowserStatManager b);

  public IBrowserStatManager get(String first);

  public void clear();

  public void finish();

  public List<CommonGame> gameOfASB(QuickFilter qf);

  public List<StatBrowserView> getListSBForFilter(QuickFilter qf);

  public List<StatBrowserView> buildTreeFromGame(List<CommonGame> games);
}
