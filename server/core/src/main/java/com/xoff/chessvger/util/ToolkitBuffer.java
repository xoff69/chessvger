package com.xoff.chessvger.util;

import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.model.CommonGame;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ToolkitBuffer {

    private static List<CommonGame> list;

    /**
     * copie les CommonGame (complets!) localement
     */
    @SuppressFBWarnings(value = {"EI_EXPOSE_STATIC_REP2"}, justification = "We want that")
    public static void copy(List<CommonGame> plist) {
        list = plist;
    }

    /**
     * ajoute tous les completes games a db 1) on change tous les id 2) on
     * ajoute
     */
    /*
    public static void paste(DatabaseManager db) {
        if (list == null) {
            return;
        }
        for (CommonGame cg : list) {

            db.upsert(cg, DBOperation.DUPLICATE);
        }
        log.info("fin du collage:" + list.size());
    }*/

}
