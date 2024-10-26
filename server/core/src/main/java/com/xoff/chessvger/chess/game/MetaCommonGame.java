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

  public MetaCommonGame(){
    metaCommentaireMove="[]";
    flipBoard=false;
    lastSeen=0;
    commentairePartie="[]";
    source="";
    commentateurPrincipal="";
  }
  public MetaCommonGame duplicate() {
    MetaCommonGame other=new MetaCommonGame();
    other.metaCommentaireMove=new String(metaCommentaireMove);
    other.commentairePartie=new String(commentairePartie);
    other.lastSeen=lastSeen;
    other.flipBoard=flipBoard;
    other.setSource(new String(source));
    other.setCommentateurPrincipal(new String(commentateurPrincipal));
    return other;

  }
}
