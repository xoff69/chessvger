package com.xoff.chessvger.ui.web.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.game.ItemGameTree;
import com.xoff.chessvger.chess.game.OneGameTree;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@ToString
@Getter
@Setter
@Slf4j
public class GameView {

  private int id;
  private String event;
  private String site;

  private String date;
  private String round;
  private String resultat;
  private String whiteTitle;
  private String blackTitle;
  private int whiteElo;
  private int blackElo;
  private String eco;
  private String opening;

  private String joueurBlanc;
  private String joueurNoir;

  private String eventDate;
  private int nbcoups;
  private int lastPosition;
  private long informationsFaitDeJeu;
  private String lastUpdate;

  private boolean isDeleted;

  private String firstMove;
  private String moves;


  private int interet;
  private boolean theorique;
  private boolean favori;

  private String metaCommentaireMove;
  private String commentairePartie;
  private long lastSeen;
  private boolean flipBoard;
  private String source;
  private String commentateurPrincipal;

  private boolean partieAnalysee;

  private long bdId;
  private long gameId;


  private String movesHtml;
  @JsonIgnore
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private OneGameTree oneGameTree;


  public void computeHtml() {
    oneGameTree = new OneGameTree((moves));
    movesHtml = toHtml(oneGameTree.getParent(), new Position()) + resultat;
  }

  private String toHtml(ItemGameTree current, Position position) {

    StringBuilder sb = new StringBuilder();
    if (StringUtils.isEmpty(current.getCurrentMove())) {
      return sb.toString();
    }

    BoardManager.play(position, current.getCurrentMove());
    String fen = position.tofen();
    current.setFen(fen);

    String functionName = "javascript:clickMove" + bdId + "_" + gameId;
    String param = "(" + current.getId() + ",'" + fen + "');";
    String idhref = " id='link_" + current.getId() + "' ";
    sb.append("<a href=\"" + functionName + param + "\" " + idhref + ">");
    sb.append(current.getCurrentMove());
    sb.append("</a>");
    sb.append("&nbsp;");

    /*
    if (!StringUtils.isEmpty(comment)) {
        sb.append("<i>{");
        sb.append(CHAINE_LINK_TREE);
        sb.append(getCommentId());
        sb.append("\">");
        sb.append(comment);
        sb.append("</a>");
        sb.append("}</i>");
    }

    if (variations.size() > 0) {
        sb.append("<font color=\"blue\">");
        sb.append(" ").append(DEBUT_VARIATION);

        for (ItemGameTree i : variations) {

            sb.append(i.toHtml(additionnal)).append(" ");

        }
        sb.append(FIN_VARIATION).append(" ");
        sb.append("</font>");

    }

     */
    if (current.getNextMove() != null &&
        !StringUtils.isEmpty(current.getNextMove().getCurrentMove())) {
      sb.append(" ").append(toHtml(current.getNextMove(), position));
    }
    return sb.toString();

  }

  @JsonIgnore
  public String getTitle() {
    if (getId() == 0) {
      return "label.nouellepartie";
    }

    return getId() + " " + joueurBlanc + "/" + joueurNoir;
  }
}
