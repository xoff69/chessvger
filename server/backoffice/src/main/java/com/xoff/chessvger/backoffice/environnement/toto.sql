-- appele lors de l initialisation de docker postgres
-- *********************************
-- COMMON
-- *********************************
CREATE SCHEMA  IF NOT EXISTS common;


create table common.common_player (id bigint not null, birthday varchar(255),
                                   blitz_games varchar(255), blitz_rating varchar(255), blitzk varchar(255),
                                   country varchar(255), fide_id varchar(255), flag varchar(255),
                                   foa_title varchar(255), games varchar(255), k varchar(255), name varchar(255),
                                   o_title varchar(255), rapid_games varchar(255), rapid_rating varchar(255),
                                   rapidk varchar(255), rating varchar(255), sex varchar(255), title varchar(255),
                                   w_title varchar(255), primary key (id))
;
-- *********************************
-- USERS
-- *********************************

CREATE SCHEMA IF NOT EXISTS users;

CREATE TABLE users.users (

                             id SERIAL PRIMARY KEY,
                             login VARCHAR(50) NOT NULL UNIQUE,
                             description TEXT,
                             password VARCHAR(255) NOT NULL,

                             date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             date_update TIMESTAMP,

                             profil BOOLEAN DEFAULT FALSE -- Profil: admin (TRUE) ou utilisateur normal (FALSE)

);
INSERT INTO users.users (login, description, password, profil)
VALUES     ('admin', 'Administrator account', 'admin', TRUE);

INSERT INTO users.users (login, description, password)
VALUES     ('test_user', 'Test user account', 'test_user');
-- *********************************
-- ADMIN FEATURES
-- *********************************
CREATE SCHEMA IF NOT EXISTS business;
-- feature
-- contract
-- *********************************
-- TENANT_ADMIN
-- *********************************
CREATE SCHEMA IF NOT EXISTS tenant_admin;

REATE TABLE database (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_update TIMESTAMP,

);

create table tenant_admin.common_game (black_elo integer, date date, event_date date, favori boolean not null, interet integer not null,
                                       is_deleted boolean, last_position integer, nb_coups integer, partie_analysee boolean, theorique boolean not null,
                                       white_elo integer, black_fide_id bigint, id bigint not null, informations_fait_de_jeu bigint, last_update bigint,
                                       white_fide_id bigint, moves varchar(3000), black_player varchar(255), black_title varchar(255), eco varchar(255),
                                       event varchar(255), first_move varchar(255), opening varchar(255), result varchar(255), round varchar(255),
                                       site varchar(255), white_player varchar(255), white_title varchar(255), primary key (id))
;
create table tenant_admin.position_games (id bigint not null, position bigint not null, primary key (id));

create table tenant_admin.position_entity_games (games bigint, position_entity_id bigint not null);

// FIXME index