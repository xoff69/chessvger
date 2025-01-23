CREATE TABLE %s.search_criteria (
                                 id SERIAL PRIMARY KEY, -- Génère automatiquement un identifiant unique
                                 criteria JSONB NOT NULL, -- Champ pour stocker les critères de recherche en format JSONB
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Optionnel : champ pour enregistrer la date de création
);