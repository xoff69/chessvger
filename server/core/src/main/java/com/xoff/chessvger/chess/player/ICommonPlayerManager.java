package com.xoff.chessvger.chess.player;

import java.util.List;

public interface ICommonPlayerManager {

  void forceUpdate();

  void clear();

  List<CommonPlayer> findByNameOrID(String param);

  List<CommonPlayer> findByNameOrIDSearch(String param);

  CommonPlayer findOrAdd(String pname, long fideID);

  List<String> listPlayer();

  CommonPlayer findById(long idFide);

  boolean isWellKnowPlayer(String name);

  CommonPlayer findByName(String name);


  int importeFidePlayer();

  int importeFidePlayer(String emplacement);

  void finish();


  SynonymPlayerManager getSynonymPlayerManager();


  FamousPlayerManager getFamousPlayerManager();
}
