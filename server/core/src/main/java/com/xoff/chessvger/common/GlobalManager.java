package com.xoff.chessvger.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.board.RulesManager;
import com.xoff.chessvger.chess.callstat.CallStatManager;
import com.xoff.chessvger.chess.callstat.ICallStatManager;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.database.DatabaseMap;
import com.xoff.chessvger.chess.database.FiliationMap;
import com.xoff.chessvger.chess.database.IDatabaseManager;
import com.xoff.chessvger.chess.engine.EngineManager;
import com.xoff.chessvger.chess.engine.IEngineManager;
import com.xoff.chessvger.chess.favorite.FavoriteManager;
import com.xoff.chessvger.chess.favorite.IFavoriteManager;
import com.xoff.chessvger.chess.feature.FeatureManager;
import com.xoff.chessvger.chess.feature.IFeatureManager;
import com.xoff.chessvger.chess.filter.FilterManager;
import com.xoff.chessvger.chess.filter.IFilterManager;
import com.xoff.chessvger.chess.opening.IOpeningManager;
import com.xoff.chessvger.chess.opening.OpeningManager;
import com.xoff.chessvger.chess.pack.IPackManager;
import com.xoff.chessvger.chess.pack.PackManager;
import com.xoff.chessvger.chess.player.CommonPlayerManager;
import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.chess.position.AnalysedPositionManager;
import com.xoff.chessvger.chess.position.IAnalysedPositionManager;
import com.xoff.chessvger.chess.repertoire.IRepertoireManager;
import com.xoff.chessvger.chess.repertoire.RepertoireManager;
import com.xoff.chessvger.chess.user.IUserManager;
import com.xoff.chessvger.chess.user.UserManager;
import com.xoff.chessvger.chess.userpack.IUserPackManager;
import com.xoff.chessvger.chess.userpack.UserPackManager;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j


@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
public class GlobalManager {
  @SuppressFBWarnings(value = {"MS_EXPOSE_REP"}, justification = "We want that")
  private static GlobalManager _instance = null;

  @Getter
  private final DatabaseMap databaseMap;
  @Getter
  private final FiliationMap filiationMap;

  private final HashMap<Long, IDatabaseManager> databaseManagers;
  @Getter
  private final RulesManager rulesManager;
  @Getter
  private final IOpeningManager openingManager;
  @Getter
  private final ICommonPlayerManager commonPlayerManager;
  @Getter
  private final IAnalysedPositionManager apPositionManager;
  @Getter
  private final IEngineManager engineManager;
  @Getter
  private final IFilterManager filterManager;
  /**
   * time stamp de debut de session, utile pour les quickfilter
   */
  private final long debutSession;
  private final List<DatabaseManager> openedDatabase;
  @Getter
  private IFavoriteManager favoriteManager;
  @Getter
  private IRepertoireManager repertoireManager;
  @Getter
  private IFeatureManager featureManager;
  @Getter
  private ICallStatManager callStatManager;
  @Getter
  private IUserManager userManager;
  @Getter
  private IPackManager packManager;
  @Getter
  private IUserPackManager userPackManager;

  @Getter
  private ObjectMapper objectMapper;

  private GlobalManager() {
    log.info(">Globalmanager new");
    objectMapper = new ObjectMapper();
    databaseMap = new DatabaseMap();
    filiationMap=new FiliationMap();
    openedDatabase = new ArrayList<>();
    debutSession = System.currentTimeMillis();

    databaseManagers = new HashMap();
    rulesManager = new RulesManager();

    commonPlayerManager = new CommonPlayerManager();
    engineManager = new EngineManager();
    filterManager = new FilterManager();
    openingManager = new OpeningManager();
    apPositionManager = new AnalysedPositionManager();
    favoriteManager = new FavoriteManager();
    repertoireManager = new RepertoireManager();

    featureManager = new FeatureManager();
    callStatManager = new CallStatManager();

    userManager = new UserManager();
    packManager = new PackManager();
    userPackManager = new UserPackManager();
    log.info("<Globalmanager new");
  }

  public static GlobalManager getInstance() {

    if (_instance == null) {
      log.info("Globalmanager getInstance");
      _instance = new GlobalManager();
    }
    return _instance;
  }


  public IDatabaseManager getDatabaseManager(long clef) {

    return databaseManagers.get(clef);
  }


  public void addDatabaseManager(DatabaseManager dm) {
    databaseManagers.put(dm.getDatabaseId(), dm);
  }

  // TODO quand on ferme l onglet
  public void finish() {
    log.info(">>>>>>>>>>globalManager.anager.commit");


    for (val dbm : databaseManagers.entrySet()) {
      ((DatabaseManager) dbm).finish();
    }
    commonPlayerManager.finish();

    openingManager.finish();
    filterManager.finish();
    engineManager.finish();
    favoriteManager.finish();
    apPositionManager.finish();
    repertoireManager.finish();
    featureManager.finish();
    callStatManager.finish();
    userManager.finish();
    packManager.finish();
    userPackManager.finish();
    //log.info("<<<<<<<<<<globalManager.anager.commit");

  }



}
