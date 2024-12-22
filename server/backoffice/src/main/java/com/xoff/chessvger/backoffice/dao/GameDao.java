package com.xoff.chessvger.backoffice.dao;

public class GameDao {
  /**
   * tenant schema + db id
   */
  public static final String TABLE_GAME=" create table %s.db_%s_common_game (black_elo integer, date date, event_date date, favori boolean not null, interet integer not null,\n" +
  "is_deleted boolean, last_position integer, nb_coups integer, partie_analysee boolean, theorique boolean not null,\n" +
  "white_elo integer, black_fide_id bigint, id bigint not null, informations_fait_de_jeu bigint, last_update bigint,\n" +
  "white_fide_id bigint, moves varchar(3000), black_player varchar(255), black_title varchar(255), eco varchar(255),\n" +
  "event varchar(255), first_move varchar(255), opening varchar(255), result varchar(255), round varchar(255),\n" +
 " site varchar(255), white_player varchar(255), white_title varchar(255), primary key (id))\n" +
  ";";
  /**
   * tenant schema + db id
   */
  public static final String TABLE_POSITION_GAMES=
  "create table %s.position_games (id bigint not null, position bigint not null, primary key (id));";
  /**
   * tenant schema + db id
   */
  public static final String TABLE_POSITION_ENTITY_GAMES=
      "  create table %s.position_entity_games (games bigint, position_entity_id bigint not null);";
}
