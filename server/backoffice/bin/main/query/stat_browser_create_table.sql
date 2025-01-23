CREATE TABLE  %s.stat_browser (
                              id SERIAL PRIMARY KEY,
                              level INT NOT NULL,
                              white_win INT NOT NULL DEFAULT 0,
                              nul INT NOT NULL DEFAULT 0,
                              black_win INT NOT NULL DEFAULT 0,
                              last_game_date VARCHAR(20),
                              elo_min INT NOT NULL DEFAULT 2147483647
);


