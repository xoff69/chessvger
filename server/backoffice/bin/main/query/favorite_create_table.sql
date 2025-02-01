CREATE TABLE %s.favorite (
                          id SERIAL PRIMARY KEY, -- Identifiant unique pour chaque enregistrement
                          gameId INT NOT NULL, -- Identifiant du jeu
                          favorite_date DATE NOT NULL DEFAULT CURRENT_DATE -- Date associée, par défaut la date actuelle
);