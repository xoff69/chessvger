CREATE TABLE %s.history (
                          id SERIAL PRIMARY KEY, -- Identifiant unique pour chaque enregistrement
                          gameId INT NOT NULL, -- Identifiant du jeu
                          history_date DATE NOT NULL DEFAULT CURRENT_DATE -- Date associée, par défaut la date actuelle
);