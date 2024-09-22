package com.xoff.chessvger.ui.web.view;

import com.xoff.chessvger.chess.player.CommonPlayer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


public class JoueurView {
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private CommonPlayer joueur;
  private int nbgames;

  public CommonPlayer getJoueur() {
    return joueur;
  }

  public void setJoueur(CommonPlayer joueur) {
    this.joueur = joueur;
  }

  public int getNbgames() {
    return nbgames;
  }

  public void setNbgames(int nbgames) {
    this.nbgames = nbgames;
  }
}
