INSERT INTO admin.common_game (
    black_elo, date, event_date, favori, interet,
    is_deleted, last_position, nb_coups, partie_analysee, theorique,
    white_elo, black_fide_id, id, informations_fait_de_jeu, last_update,
    white_fide_id, moves, black_player, black_title, eco,
    event, first_move, opening, result, round,
    site, white_player, white_title
)
SELECT *
FROM dblink(
             'host=source_host dbname=source_db user=source_user password=source_password',
             'SELECT black_elo, date, event_date, favori, interet,
                     is_deleted, last_position, nb_coups, partie_analysee, theorique,
                     white_elo, black_fide_id, id, informations_fait_de_jeu, last_update,
                     white_fide_id, moves, black_player, black_title, eco,
                     event, first_move, opening, result, round,
                     site, white_player, white_title
              FROM admin.common_game'
     ) AS source_table (
                        black_elo INTEGER, date DATE, event_date DATE, favori BOOLEAN, interet INTEGER,
                        is_deleted BOOLEAN, last_position INTEGER, nb_coups INTEGER, partie_analysee BOOLEAN, theorique BOOLEAN,
                        white_elo INTEGER, black_fide_id BIGINT, id BIGINT, informations_fait_de_jeu BIGINT, last_update BIGINT,
                        white_fide_id BIGINT, moves VARCHAR(3000), black_player VARCHAR(255), black_title VARCHAR(255), eco VARCHAR(255),
                        event VARCHAR(255), first_move VARCHAR(255), opening VARCHAR(255), result VARCHAR(255), round VARCHAR(255),
                        site VARCHAR(255), white_player VARCHAR(255), white_title VARCHAR(255)
    );