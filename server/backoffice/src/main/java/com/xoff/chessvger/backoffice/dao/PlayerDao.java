package com.xoff.chessvger.backoffice.dao;

public class PlayerDao {
  public static final String TABLE_PLAYER="create table "+ CommonDao.COMMON_SCHEMA+".common_player (id bigint not null, birthday varchar(255),\n" +
      "                                   blitz_games varchar(255), blitz_rating varchar(255), blitzk varchar(255),\n" +
      "                                   country varchar(255), fide_id varchar(255), flag varchar(255),\n" +
      "                                   foa_title varchar(255), games varchar(255), k varchar(255), name varchar(255),\n" +
      "                                   o_title varchar(255), rapid_games varchar(255), rapid_rating varchar(255),\n" +
      "                                   rapidk varchar(255), rating varchar(255), sex varchar(255), title varchar(255),\n" +
      "                                   w_title varchar(255), primary key (id))\n" + ";";
}
