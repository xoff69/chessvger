package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.ui.web.form.FilterForm;
import com.xoff.chessvger.ui.web.mapper.DatabaseMapper;
import com.xoff.chessvger.ui.web.navigation.DBOpened;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.ui.web.navigation.Tab;
import com.xoff.chessvger.ui.web.view.CoupleLongView;
import com.xoff.chessvger.ui.web.view.PageView;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseServiceImpl implements IDatabaseService {
  @Autowired
  Navigation navigation;

  @Autowired
  DatabaseMapper databaseMapper;

  public PageView managePage(Pageable paging, long userId) {
    User user = GlobalManager.getInstance().getUserManager().get(userId);
    List<Database> databases;
    if (user.getIsAdmin()) {
      databases = GlobalManager.getInstance().getDatabaseMap().list();
    } else {
      List<Repertoire> repertoires =
          GlobalManager.getInstance().getRepertoireManager().findRepertoire(userId);
      databases = new ArrayList<>();
      for (Repertoire repertoire : repertoires) {
        log.info("ajouy db "+repertoire);
        long databaseID = repertoire.getDatabaseId();
        databases.add(GlobalManager.getInstance().getDatabaseMap().get(databaseID));
      }

    }
    log.info("admin "+user.getIsAdmin()+ " managePage " + databases.toString() + " " + userId);

    return Page.compute(paging, databaseMapper.mapListEntity2Dto(databases));
  }

  public void createBD(DBForm dbForm) {

    log.info(dbForm.toString());
    // TODO faire les verifs
    // FIXME mettre ca dans un service

    Database database = databaseMapper.dbForm2Database(dbForm);
    database.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
    database.setLastUpdate(System.currentTimeMillis());
    GlobalManager.getInstance().getDatabaseMap().add(database.getId(), database);
    DatabaseManager dm = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(dm);
  }

  public void dbOpen(long bdId) {

    log.info("bd open " + bdId);
    Database database = GlobalManager.getInstance().getDatabaseMap().get(bdId);
    log.info("db= " + database);
    DatabaseManager dm = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(dm);
    log.info("db= " + GlobalManager.getInstance().getDatabaseManager(bdId));
    Tab t = navigation.appendDatabaseTab(bdId);
    navigation.setLevel1Tab(t.getId());
    navigation.setLevel2Tab(t.getFils().get(0).getId());

    if (navigation.getBdFilters().get(bdId) == null) {
      log.info("bdOpen ajout du filter");
      FilterForm filterForm = new FilterForm();
      filterForm.setBdId(bdId);
      navigation.getBdFilters().put(bdId, filterForm);
    }

    DBOpened dbOpened = new DBOpened();
    dbOpened.setBdId(bdId);
    navigation.getDbOpeneds().put(bdId, dbOpened);

    log.info("navigation bdOpen =" + navigation);
  }

  public CoupleLongView dbClose(long bdId) {

    // faire appel server pour remover le tab et le opened game + sauver le game
    DBOpened dbOpened = navigation.getDbOpeneds().remove(bdId);
    log.info(dbOpened.toString());

    navigation.removeTabBd(bdId);
    navigation.getBdFilters().remove(bdId);
// TODO methode clean + games
    CoupleLongView coupleView = new CoupleLongView();
    coupleView.setTabfirst(navigation.getTabs().get(0).getId());
    Tab bdTab = navigation.getByObjectKey(bdId);
    coupleView.setTabtoClose(bdTab.getId());
    return coupleView;
  }

}
