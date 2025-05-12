package com.xoff.chessvger.chess.stat;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class BrowserStatManager  {



/*

    public List<CommonGame> getGames(Position p) {
        //  log.info("getGames  :" );
        List<CommonGame> res = new ArrayList();
        List<StatBrowserView> lsb = getBrowseData(databaseManager, p.getMoves());

        ICommonGameManager cm = databaseManager.getGlobalGameManager().get(premier);
///log.info("getGames:" +lsb.size());
        for (StatBrowserView sb : lsb) {
            //   log.info("sb "+sb);
            List<Long> li = gameOfAStatMap.getValuesForKey(sb.getId());
            if (li != null) {
                //      log.info("sb= " + sb + "-" + li.size());
                for (long id : li) {
                    CommonGame g = cm.get(id);
                    if (g != null) {
                        res.add(g);
                    }
                }
            } else {
                log.error("pas de game pour la clef :" + sb);
            }
        }
        //    log.info("getGames:" + res.size());
        return res;
    }


    public void clear() {
        gameOfAStatMap.clear();
        browserMap.clear();
    }

*/
}
