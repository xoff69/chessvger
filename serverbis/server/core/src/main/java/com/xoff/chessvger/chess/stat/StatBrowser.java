package com.xoff.chessvger.chess.stat;

import com.xoff.chessvger.util.StringUtility;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class StatBrowser implements Serializable {

  private static final long serialVersionUID = 1479196290106048668L;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<String> listMeilleursJoueurs;
  private long id;
  private int level;
  private int blanc;
  private int nul;
  private int noir;
  /**
   * derniere apparition
   */
  private String lastGameDate;
  private int eloMin;


  public StatBrowser() {
    lastGameDate = StringUtils.EMPTY;
    listMeilleursJoueurs = new ArrayList();
    eloMin = Integer.MAX_VALUE;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {

    out.writeLong(getId());

    out.writeInt(getLevel());
    out.writeInt(getBlanc());

    out.writeInt(getNul());
    out.writeInt(getNoir());

    out.writeInt(getEloMin());
    out.writeUTF(getLastGameDate());
    out.writeUTF(getBestPlayer());

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    setId(in.readLong());

    setLevel(in.readInt());
    setBlanc(in.readInt());
    setNul(in.readInt());
    setNoir(in.readInt());

    setEloMin(in.readInt());
    setLastGameDate(in.readUTF());
    String mj = in.readUTF();

    // pas super optimal mais bon

    List<String> conv = StringUtility.stringToList(mj);
    listMeilleursJoueurs = new ArrayList();
    for (String s : conv) {
      addBestPlayer(s);
    }

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
