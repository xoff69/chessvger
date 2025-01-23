CREATE TABLE %s.stat_browser_best_players (
                                           id SERIAL PRIMARY KEY,
                                           stat_browser_id BIGINT NOT NULL,
                                           player VARCHAR(255) NOT NULL
   -- ,                                           FOREIGN KEY (stat_browser_id) REFERENCES stat_browser(id) ON DELETE CASCADE
);