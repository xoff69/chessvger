package com.xoff.chessvger.chess.player;

import java.util.List;

public interface ICommonPlayerManager {

  public void forceUpdate();

  public void clear();

  public List<CommonPlayer> findByNameOrID(String param);

  public List<CommonPlayer> findByNameOrIDSearch(String param);

  public CommonPlayer findOrAdd(String pname, long fideID);

  public List<String> listPlayer();

  public CommonPlayer findById(long idFide);

  public boolean isWellKnowPlayer(String name);

  public CommonPlayer findByName(String name);


  public int importeFidePlayer();

  public int importeFidePlayer(String emplacement);

  public void finish();


  public SynonymPlayerManager getSynonymPlayerManager();


  public FamousPlayerManager getFamousPlayerManager();
}
