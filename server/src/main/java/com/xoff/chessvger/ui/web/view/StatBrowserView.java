package com.xoff.chessvger.ui.web.view;

import com.xoff.chessvger.chess.stat.StatBrowser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class StatBrowserView extends StatBrowser {
  // Todo StatBrowserView doit etre un dto
  @Getter
  @Setter
  private String coup;

  public StatBrowserView(StatBrowser parent, String coup) {
    setId(parent.getId());
    setBlanc(parent.getBlanc());
    setNoir(parent.getNoir());
    setNul(parent.getNul());
    setLevel(parent.getLevel());
    setLastGameDate(parent.getLastGameDate());
    addBestPlayer(parent.getBestPlayer());
    setEloMin(parent.getEloMin());
    this.coup = coup;
  }


}
