package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonPlayerMap extends AdbCommonKeyLong<CommonPlayer> {


  private final HashMap<String, CommonPlayer> mapByName;
  private final HashMap<Long, CommonPlayer> mapById;

  private final List<String> names;


  public CommonPlayerMap() {

    super(ParamConstants.DATA_FOLDER_COMMON + "CommonPlayerMap" + Constants.MAP_SFX);
    log.info(">>> DEbut :CommonPlayerMap");
    names = new ArrayList();
    mapByName = new HashMap();
    mapById = new HashMap();
    List<CommonPlayer> l = super.list();
    log.info("  " + l.size());
    for (CommonPlayer n : l) {
      mapByName.put(n.getName(), n);
      mapById.put(n.getIdnumber(), n);
      names.add(n.getName());
    }
    log.info("Fin:CommonPlayerMap");
  }
  public List<CommonPlayer> list() {
      List<CommonPlayer> result=new ArrayList<>();
    mapById.forEach((key, value) -> {
      result.add(value);
    });
      return result;
  }
  public void commit() {

    mapById.forEach((key, value) -> {
        super.add(key,value);
      });


    super.commit();

  }

  public void clear() {
    names.clear();
    mapByName.clear();
    super.clear();
  }
public CommonPlayer findById(long id){
    return mapById.get(id);
}

  public CommonPlayer findByName(String name) {

    return mapByName.get(name);
  }

  public void add(long key, CommonPlayer va) {

    super.add(key, va);
    mapByName.put(va.getName(), va);
    names.add(va.getName());
    mapById.put(key,va);
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  public List<String> listNames() {
    log.info(">>listNames");

    if (names.isEmpty()) {
      List<CommonPlayer> l = list();
      log.info("  " + l.size());
      for (CommonPlayer s : l) {
        names.add(s.getName());
        //    log.info("name="+s.getName()+"-");
      }

    }
    log.info("<<<listNames=" + names.size());
    return names;
  }


  public List<Long> listFideId() {
    List<Long> resultat = new ArrayList();
    for (CommonPlayer s : list()) {
      resultat.add(s.getIdnumber());
    }
    return resultat;
  }

}
