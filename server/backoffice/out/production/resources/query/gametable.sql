create table %s.common_game (black_elo integer, date date, event_date date, favori boolean not null, interet integer not null,
  is_deleted boolean, last_position integer, nb_coups integer, partie_analysee boolean, theorique boolean not null,
  white_elo integer, black_fide_id bigint, id bigint not null, informations_fait_de_jeu bigint, last_update bigint,
  white_fide_id bigint, moves varchar(3000), black_player varchar(255), black_title varchar(255), eco varchar(255),
  event varchar(255), first_move varchar(255), opening varchar(255), result varchar(255), round varchar(255),
  site varchar(255), white_player varchar(255), white_title varchar(255), primary key (id))
  ;