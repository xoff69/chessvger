CREATE TABLE %s.game_of_a_player
(
    id_player
    BIGINT
    NOT
    NULL,
    id_game
    BIGINT
    NOT
    NULL,
    PRIMARY
    KEY
(
    id_player,
    id_game
)
    );