package com.xoff.chessvger.ui.web.navigation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Tab {


  private int id;

  /**
   * clef vers l objet entite affiche ici, ca peut etre la bd ou u n game
   */
  private long objectKey;

  private TabType type;

  private boolean visible;

  private String label;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<Tab> fils;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private TabType tabType;

  public Tab(int id, String label, TabType tabType) {
    this.id = id;

    this.label = label;
    this.tabType = tabType;
    fils = new ArrayList();
    visible = true;
    // le bd id?
  }

  public String toString() {
    return id + "--" + label + "--ok=" + objectKey + "--";

  }


  public void removeById(int id) {
    for (Tab f : fils) {
      f.removeById(id);
    }
    Tab f = tabById(id);
    if (f != null) {
      fils.remove(f);
    }
  }

  public Tab tabById(int pid) {

    if (id == pid) {
      return this;
    }
    for (Tab f : fils) {
      Tab o = f.tabById(pid);
      if (o != null) {
        return o;
      }
    }
    return null;
  }


}
