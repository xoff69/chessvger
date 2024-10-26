package com.xoff.chessvger.init;

import static com.xoff.chessvger.ui.web.navigation.NavigationConstants.*;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.chess.pack.PackDatabase;
import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.common.GlobalManager;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LoadData {
  private static final String DEFAULT_ADMIN_USER = "admin";
  private static final String DEFAULT_ADMIN_PASSWORD = "admin";
  private static final String DEFAULT_USER = "user";
  private static final String DEFAULT_PASSWORD = "user";

  private static final String[] FEAT_DISABLED = {
      FOOTER, STATUS, BOARD, BROWSE, ENGINE, SEARCH_POSITION,
      SEARCH_OTHERS, SEARCH_FILTERS, BUTTONS_GAME, EDIT_GAME, FEN_GAME
  };



  private static final String[] FEAT_ENABLED = {
      PGN_IMPORT, PLAYER_IMPORT, CONNEXION, HISTORIC, FAVORITES, REPERTOIRE,PLAYER,
      PACK, MYPACK, ADMINSTAT, ADMINENGINE, ADMINFIDEPLAYER
  };


  public static void main(String[] args) {

    DatabaseManager databaseManager = CreateDBTool.createDbCore(args[0], args[1], args[2],args[3]);
    log.info("loadData : " + databaseManager.getDatabaseId());
    // peut etre qu il faut plusieurs utilitaires
    LoadData.initializeData(databaseManager);
    log.info("loadData done : " + databaseManager.getDatabaseId());
  }



  public static void initializeData(DatabaseManager databaseManager) {
    log.info(">initializeData:");
    createFeatures();
    createUsersPacksRepertoire(databaseManager);
    log.info("<initializeData");
  }


  private static void createUsersPacksRepertoire(DatabaseManager databaseManager) {

    User user = new User();
    user.setPassword(DEFAULT_ADMIN_USER);
    user.setLogin(DEFAULT_ADMIN_PASSWORD);
    user.setIsAdmin(true);
    user.setName("Dr. Chessvger");
    user.setCustomPresentation("");
    user = GlobalManager.getInstance().getUserManager().create(user);

    User userBase = new User();
    userBase.setPassword(DEFAULT_USER);
    userBase.setLogin(DEFAULT_PASSWORD);
    userBase.setIsAdmin(false);
    userBase.setName("master. Chessvger");
    userBase.setCustomPresentation("");
    userBase = GlobalManager.getInstance().getUserManager().create(userBase);
    log.info("users created " + user);


    Pack pack = new Pack();
    pack.setName("defaultPack");

    pack = GlobalManager.getInstance().getPackManager().create(pack);
    log.info("pack created " + pack);
    PackDatabase packDatabase=new PackDatabase();
    packDatabase.setPackId(pack.getId());
    packDatabase.setBdId(databaseManager.getDatabaseId());
    GlobalManager.getInstance().getPackManager().update(pack);
    log.info("packDatabase created " + packDatabase);
    UserPack userPack = new UserPack();
    userPack.setUserId(user.getId());
    userPack.setPackId(pack.getId());
    GlobalManager.getInstance().getUserPackManager().create(userPack);
    log.info("usersPack created " + userPack);

    long newDatabaseID = databaseManager.duplicate(userBase.getId());


    Repertoire repertoire = new Repertoire();
    repertoire.setDatabaseId(newDatabaseID);
    repertoire.setUserId(userBase.getId());
    GlobalManager.getInstance().getRepertoireManager().create(repertoire);
    log.info("repertoire created " + repertoire);



    log.info("createUsersPacksRepertoire done");
  }


  private static void createFeatures() {

    long idFeature = 1L;
    GlobalManager.getInstance().getFeatureManager().clear();
    for (String s : LoadData.FEAT_DISABLED) {
      Feature feature = new Feature();
      feature.setId(idFeature++);
      feature.setName(s);
      feature.setEnabled(false);
      GlobalManager.getInstance().getFeatureManager().create(feature);
    }
    for (String s : LoadData.FEAT_ENABLED) {
      Feature feature = new Feature();
      feature.setId(idFeature++);
      feature.setName(s);
      feature.setEnabled(true);
      GlobalManager.getInstance().getFeatureManager().create(feature);
    }
    log.info(
        "features created " + GlobalManager.getInstance().getFeatureManager().findAll().size());
  }
}

