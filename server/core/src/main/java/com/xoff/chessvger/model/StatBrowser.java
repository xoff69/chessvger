package com.xoff.chessvger.model;

import com.xoff.chessvger.util.StringUtility;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StatBrowser  implements Serializable {


    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
    private List<String> listMeilleursJoueurs;
    private long id;
    private int level;
    private int blanc;
    private int nul;
    private int noir;
    private String movesStart;
    /**s
     * derniere apparition
     */
    private String lastGameDate;
    private int eloMin;


    public StatBrowser() {
        id = 0L;
        lastGameDate = StringUtils.EMPTY;
        listMeilleursJoueurs = new ArrayList();
        eloMin = Integer.MAX_VALUE;
    }



    public String getBestPlayer() {
        return StringUtility.listToString(listMeilleursJoueurs);
    }


    public void addBestPlayer(String meilleurJoueur) {
        if (!listMeilleursJoueurs.contains(meilleurJoueur)) {
            listMeilleursJoueurs.add(meilleurJoueur);
        }
    }


}
