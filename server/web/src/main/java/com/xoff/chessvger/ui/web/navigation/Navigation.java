package com.xoff.chessvger.ui.web.navigation;

import com.xoff.chessvger.chess.database.IDatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.web.form.FilterForm;
import com.xoff.chessvger.ui.web.view.GameView;
import com.xoff.chessvger.ui.web.view.UserDto;
import com.xoff.chessvger.util.SessionKeyGenerator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component("navigation")
@SessionScope
@Getter
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
@Slf4j
public class Navigation {
  @Autowired
  ApplicationBean applicationBean;
  @Autowired
  private MessageSource messageSource;
  @Setter
  private int level1Tab;
  @Setter
  private int level2Tab;
  private TabList tabs;

  private HashMap<Long, FilterForm> bdFilters;
  @Setter
  private UserDto userDto;

  @Setter
  private String serverMessage;
  @Setter
  private String serverError;

  private List<CommonGame> gamesCopied;

  private HashMap<Long, DBOpened> dbOpeneds;

  private HashMap<String, GameView> cacheGameView;

  public Navigation() {
    log.info("new naviggation");
    tabs = new TabList();
    bdFilters = new HashMap();
    gamesCopied = new ArrayList();
    dbOpeneds = new HashMap<>();
    userDto = null;
    cacheGameView = new HashMap<>();
  }
  public Tab findById(long id) {
    for (Tab tab : tabs.getList()) {
      if (tab.getId() == id) {
        return tab;
      }
      for (Tab stab : tab.getFils()) {
        if (stab.getId() == id) {
          return stab;
        }
      }
    }
    return null;

  }
  public Tab getByObjectKey(long objectKey) {
    for (Tab tab : tabs.getList()) {
      if (tab.getObjectKey() == objectKey) {
        return tab;
      }
      for (Tab stab : tab.getFils()) {
        if (stab.getObjectKey() == objectKey) {
          return stab;
        }
      }
    }
    return null;

  }

  public String toString() {
    StringBuilder sb = new StringBuilder("<ul>");
    for (Tab tab : tabs.getList()) {
      sb.append("<li>").append(tab.toString()).append("</li>");
    }
    sb.append("</ul>");
    sb.append("openeed");
    for (DBOpened dbOpened : dbOpeneds.values()) {
      sb.append("op " + dbOpened.getBdId() + " " + dbOpened.getGameOpeneds().size());
    }
    return sb.toString();
  }

  public void removeTabAdmin() {
    for (Tab tab : tabs.getList()) {
      if (tab.getTabType() == TabType.ADMIN) {
        tabs.getList().remove(tab);
        return;
      }
    }
  }

  public Tab removeTabBd(long bdId) {
    for (Tab tab : tabs.getList()) {
      if (tab.getTabType() == TabType.BD) {
        if (tab.getObjectKey() == bdId) {
          tabs.remove(tab);
          return tab;
        }
      }
    }
    return null;
  }

  public Tab removeTabGame(long bdId, long gameId) {
    for (Tab tab : tabs.getList()) {
      if (tab.getTabType() == TabType.BD) {
        if (tab.getObjectKey() == bdId) {
          List<Tab> tabFils = tab.getFils();
          for (Tab tabf : tabFils) {
            if (tabf.getObjectKey() == gameId) {
              tabFils.remove(tabf);
              return tabf;
            }
          }
        }
      }
    }
    return null;
  }

  public void appendTabAdmin() {

    // on l ajoute si pas deja la
    for (Tab tab : tabs.getList()) {
      if (tab.getTabType() == TabType.ADMIN) {
        return;
      }
    }

    String lbl = messageSource.getMessage("tab.admin", null, Locale.getDefault());
    Tab tabAdmin = new Tab(SessionKeyGenerator.getInstance().next(), lbl, TabType.ADMIN);
    Tab tabUsers = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.users", null, Locale.getDefault()), TabType.USERS);
    tabAdmin.getFils().add(tabUsers);
    Tab tabFeatures = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.features", null, Locale.getDefault()), TabType.FEATURES);
    tabAdmin.getFils().add(tabFeatures);
    Tab tabPack = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.packs", null, Locale.getDefault()), TabType.PACK);
    tabAdmin.getFils().add(tabPack);

    Tab tabUserPack = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.userpacks", null, Locale.getDefault()), TabType.USERPACK);
    tabAdmin.getFils().add(tabUserPack);
    Tab tavStats = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.adminstat", null, Locale.getDefault()), TabType.ADMINSTAT);
    tabAdmin.getFils().add(tavStats);
// on ne verifie jamais si c est autorise pour l admin?
    Tab tabFide = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.fide", null, Locale.getDefault()), TabType.ADMINFIDEPLAYER);
    tabAdmin.getFils().add(tabFide);

    Tab tabEngine = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.engine", null, Locale.getDefault()), TabType.ADMINENGINE);
    tabAdmin.getFils().add(tabEngine);
    tabs.getList().add(tabAdmin);

  }

  public void appendTabSimpleUser() {
    String lblUser = messageSource.getMessage("tab.features", null, Locale.getDefault());
    Tab tabUser = new Tab(SessionKeyGenerator.getInstance().next(), lblUser, TabType.MYFEATURES);

    if (applicationBean.isAllowedFeature(NavigationConstants.FAVORITES)) {
      Tab tabFavorite = new Tab(SessionKeyGenerator.getInstance().next(),
          messageSource.getMessage("tab.favorites", null, Locale.getDefault()), TabType.FAVORITES);

      tabUser.getFils().add(tabFavorite);
    }

    if (applicationBean.isAllowedFeature(NavigationConstants.MYPACK)) {
      Tab tabPack = new Tab(SessionKeyGenerator.getInstance().next(),
          messageSource.getMessage("tab.packs", null, Locale.getDefault()), TabType.MYPACK);
      tabUser.getFils().add(tabPack);
    }
    tabs.getList().add(tabUser);
  }

  public Tab appendTabGame(long bdId, long gameId, String title) {
// on cherche le object keu bdId dans les tab de niveau 1

    for (Tab tab : tabs.getList()) {

      if (tab.getObjectKey() == bdId) {
        for (Tab stab : tab.getFils()) {

          log.info("appendTabGame " + stab);

          String lbl =
              messageSource.getMessage("tab.game", new Object[] {title}, Locale.getDefault());
          Tab tabGame = new Tab(SessionKeyGenerator.getInstance().next(), lbl, TabType.GAME);
          tabGame.setObjectKey(gameId);

          tab.getFils().add(tabGame);
          return tab;
        }
      }
    }
    log.error("error appendTabGame " + bdId + "  " + gameId);
    return null;
    // on ajoute aux fils un nouveau tab ayant un object keu a bdid
  }

  /**
   * addDatabaseManager tab set when opening db
   */
  public Tab appendDatabaseTab(long bdId) {
    log.info("bdid " + bdId);
    //    String url = "showBd/bdId=" + bdId;
    IDatabaseManager databaseManager = GlobalManager.getInstance().getDatabaseManager(bdId);
    Tab bdTab = new Tab(SessionKeyGenerator.getInstance().next(), databaseManager.getDatabaseName(),
        TabType.BD);
    bdTab.setObjectKey(bdId);

    Tab tabGames = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.games", null, Locale.getDefault()), TabType.GAMES);


    bdTab.getFils().add(tabGames);
    if (applicationBean.isAllowedFeature(NavigationConstants.PLAYER)) {
      Tab tabPlayers = new Tab(SessionKeyGenerator.getInstance().next(),
          messageSource.getMessage("tab.players", null, Locale.getDefault()), TabType.PLAYERS);
      bdTab.getFils().add(tabPlayers);
    }
    if (applicationBean.isAllowedFeature(NavigationConstants.BROWSE)) {
      Tab tabBrowse = new Tab(SessionKeyGenerator.getInstance().next(),
          messageSource.getMessage("tab.browse", null, Locale.getDefault()), TabType.BROWSE);

      bdTab.getFils().add(tabBrowse);
    }
    // il faut aussi que le user soit connecte
    if (applicationBean.isAllowedFeature(NavigationConstants.HISTORIC) && userDto != null) {
      Tab tabHistorique = new Tab(SessionKeyGenerator.getInstance().next(),
          messageSource.getMessage("tab.historic", null, Locale.getDefault()), TabType.HISTORIC);

      bdTab.getFils().add(tabHistorique);
    }

    tabs.add(bdTab);
    return bdTab;

  }


  public void initDbs() {
    Tab onglet = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.bds", null, Locale.getDefault()), TabType.BDS);
    tabs.getList().add(onglet);
    level1Tab = onglet.getId();
    level2Tab = 0;
  }

  @PostConstruct
  private void init() {
    Tab onglet = new Tab(SessionKeyGenerator.getInstance().next(),
        messageSource.getMessage("tab.homepage", null, Locale.getDefault()), TabType.HOMEPAGE);
    tabs.getList().add(onglet);
    level1Tab = onglet.getId();
    level2Tab = 0;
  }

}
