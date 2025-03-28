package com.xoff.chessvger.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatBrowserEntity  {

  private long id;

  // Niveau (profondeur des coups)
  private int level;

  // Statistiques des résultats
  private int whiteWin = 0;


  private int nul = 0;


  private int blackWin = 0;

  // Dernière apparition (date sous forme de chaîne)

  private String lastGameDate;

  // Élo minimal

  private int eloMin = Integer.MAX_VALUE;

  // Liste des meilleurs joueurs (JSON ou chaîne délimitée)

  private List<String> bestPlayers = new ArrayList<>();

  // Méthodes utilitaires pour gérer les meilleurs joueurs
  public void addBestPlayer(String player) {
    if (!bestPlayers.contains(player)) {
      bestPlayers.add(player);
    }
  }

  public void removeBestPlayer(String player) {
    bestPlayers.remove(player);
  }
}
