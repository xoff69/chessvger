CREATE TABLE %s.player_game (
                             id_player BIGINT NOT NULL,
                             id_game BIGINT NOT NULL,
                             PRIMARY KEY (id_player, id_game)
);