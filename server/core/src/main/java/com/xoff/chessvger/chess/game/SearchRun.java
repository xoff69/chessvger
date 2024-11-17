package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.common.GlobalManager;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We want that")
public class SearchRun implements Runnable {
  private final DatabaseManager databaseManager;
  private final Filter filter;
  private final String first;


  private List<CommonGame> resultat;


  public SearchRun(DatabaseManager databaseManager, Filter filter, String first) {
    this.databaseManager = databaseManager;
    this.filter = filter;
    this.first = first;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    //  sb.append("SearchRun:" + first);
    if (resultat != null) {
      sb.append(" -taille =" + resultat.size());
    } else {
      sb.append("PAS DE DONNEE");
    }
    return sb.toString();
  }

  @Override
  public void run() {

    //  for (String s : Constants.ALL_FIRST_MOVE) {
    // FIXME le position DB nous est donnee par le filter
    ICommonGameManager gameManager =
        GlobalManager.getInstance().getDatabaseManager(databaseManager.getDatabaseId()).getGlobalGameManager()
            .get(first);
    //  log.info("recherche +" + gameManager.getGames().size());

    if (gameManager == null) {
      return;
    }
    resultat = gameManager.search(filter);


  }


  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  public List<CommonGame> getResultat() {
    return resultat;
  }
}
