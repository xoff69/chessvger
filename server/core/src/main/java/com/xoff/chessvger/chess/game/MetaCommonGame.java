package com.xoff.chessvger.chess.game;

import lombok.Data;

@Data
public class MetaCommonGame {
  private String commentairePartie;
  /**
   * les commentaires directement invisibles mais importants: - fleches, ronds
   * - commentaires moves format [numerodemicoup, fleche|rond|texte,
   * {debut,fin,couleur,rond}|{debut,fin,couleur,fleche}|{blahblah} ]
   */
  private String metaCommentaireMove;
  private long lastSeen;
  private boolean flipBoard; // true black
  private String source;
  private String commentateurPrincipal;

  public MetaCommonGame() {
    metaCommentaireMove = "[]";
    flipBoard = false;
    lastSeen = 0;
    commentairePartie = "[]";
    source = "";
    commentateurPrincipal = "";
  }

  public MetaCommonGame duplicate() {
    MetaCommonGame other = new MetaCommonGame();
    other.metaCommentaireMove = metaCommentaireMove;
    other.commentairePartie = commentairePartie;
    other.lastSeen = lastSeen;
    other.flipBoard = flipBoard;
    other.setSource(source);
    other.setCommentateurPrincipal(commentateurPrincipal);
    return other;

  }
}
