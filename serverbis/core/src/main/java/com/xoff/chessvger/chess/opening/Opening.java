package com.xoff.chessvger.chess.opening;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@Setter
public class Opening implements Serializable {
  @Serial
  private static final long serialVersionUID = 6460285275121792802L;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<String> moves = null;

  private String eco;

  private String name;


  public Opening() {

    moves = new ArrayList<>();
    name = StringUtils.EMPTY;
    eco = StringUtils.EMPTY;
  }

  @Override
  public int hashCode() {

    return eco.hashCode();
  }

  /**
   * retourne sous forme de chaine l'ouverture
   */
  @Override
  public String toString() {

    String str = eco + ":" + name;

    return str;
  }

  /**
   * isOpeningForGame
   */
  public boolean isOpeningForGame(List<String> moves) {
    try {
      int i = 0;
      for (String move : getMoves()) {
        if (i < moves.size()) {

          if (!move.equals(moves.get(i))) {
            return false;
          }
          i++;

        }
      }
    } catch (Exception e) {
      log.error("isOpeningForGame", e);
      return false;
    }

    return true;

  }


}
