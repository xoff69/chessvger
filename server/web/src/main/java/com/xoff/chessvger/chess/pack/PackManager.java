package com.xoff.chessvger.chess.pack;

import com.xoff.chessvger.common.ACommonManager;
import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;

@Slf4j
public class PackManager extends ACommonManager<Long, Pack> implements IPackManager {

  private AdbCommonKeyLong<PackDatabase> mapPackBds;

  public PackManager() {
    super(ParamConstants.DATA_FOLDER_COMMON + "PackMap" + Constants.MAP_SFX);
    this.mapPackBds = new AdbCommonKeyLong<PackDatabase>(ParamConstants.DATA_FOLDER_COMMON + "PackDbMap" + Constants.MAP_SFX);
  }

  public Pack get(Long key) {

    Pack pack=super.get(key);
    List<PackDatabase> packDatabases=mapPackBds.list();
    populate(packDatabases,pack);
    return pack;
  }
  public void finish() {
    super.finish();
    mapPackBds.commit();
  }


  public void clear() {
    super.clear(); mapPackBds.clear();
  }

  private void populate(List<PackDatabase> packDatabases, Pack pack){
    for (PackDatabase packDatabase:packDatabases){
      if (packDatabase.getPackId()==pack.getId()){
        pack.getPackBds().add(packDatabase);
      }
    }
  }
  public List<Pack> findAll() {
    List<PackDatabase> packDatabases=mapPackBds.list();
    List<Pack> packs=super.findAll();
    for (Pack pack:packs){
      populate(packDatabases,pack);
    }
    return packs;
  }


  public void deleteById(Long key) {
    super.deleteById(key);
    List<PackDatabase> packDatabases=mapPackBds.list();
    for (PackDatabase packDatabase:packDatabases){
      if (packDatabase.getPackId()==key){
        mapPackBds.remove(packDatabase.getId());
      }
    }
  }
  public List<Pack> saveAll(List<Pack> list) {
    for (Pack t : list) {
      update(t);
    }
    return list;
  }

  public Pack create(Pack value) {

    super.create( value);
    List<PackDatabase> packDatabases=value.getPackBds();
    for (PackDatabase packDatabase:packDatabases){
      packDatabase.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
      mapPackBds.add(packDatabase.getId(),packDatabase);
    }
    return value;
  }

  public void update(Pack value) {
    log.info("update Pack");
    super.update( value);
    List<PackDatabase> packDatabases=value.getPackBds();
    for (PackDatabase packDatabase:packDatabases){
      if (packDatabase.getId()!=0){
        packDatabase.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
      }

      mapPackBds.add(packDatabase.getId(),packDatabase);
    }
  }


}
