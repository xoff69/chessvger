package com.xoff.chessvger.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Persistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stat_browser")
public class StatBrowserEntity  {


  private static final long serialVersionUID = 1479196290106048668L;

  @Id
  private long id;

  // Niveau (profondeur des coups)
  @Column(nullable = false)
  private int level;

  // Statistiques des résultats
  @Column(nullable = false)
  private int whiteWin = 0;

  @Column(nullable = false)
  private int nul = 0;

  @Column(nullable = false)
  private int blackWin = 0;

  // Dernière apparition (date sous forme de chaîne)
  @Column(name = "last_game_date", length = 20)
  private String lastGameDate;

  // Élo minimal
  @Column(nullable = false)
  private int eloMin = Integer.MAX_VALUE;

  // Liste des meilleurs joueurs (JSON ou chaîne délimitée)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "stat_browser_best_players", joinColumns = @JoinColumn(name = "stat_browser_id"))
  @Column(name = "player", nullable = false)
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
