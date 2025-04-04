package com.xoff.chessvger.service;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.view.StatBrowserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrowseServiceImpl implements IBrowseService {

    public List<StatBrowserView> loadAll(long bdId) {
        GlobalManager.getInstance().getCallStatManager().appendStat("BROWSE");
        // FIXME on devrait pas faire un new DM
        Database database = GlobalManager.getInstance().getDatabaseMap().get(bdId);
        DatabaseManager dm = new DatabaseManager(database);
        return dm.getBrowseData(new ArrayList<>());

    }
}
