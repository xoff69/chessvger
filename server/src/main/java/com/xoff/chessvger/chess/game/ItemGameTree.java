package com.xoff.chessvger.chess.game;

import static com.xoff.chessvger.util.PgnUtil.END_VARIATION;
import static com.xoff.chessvger.util.PgnUtil.START_VARIATION;

import com.xoff.chessvger.util.PgnUtil;
import com.xoff.chessvger.util.SessionKeyGenerator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@Setter
public class ItemGameTree {

  private String currentMove;

  private String comment;
  // to locate easily the comment
  private int commentId;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private ItemGameTree nextMove;

  private boolean isInVariation;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private ItemGameTree previousMove;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<ItemGameTree> variations;

  private int id;
  private String fen;


  public ItemGameTree() {
    variations = new ArrayList();
    comment = StringUtils.EMPTY;
    fen = StringUtils.EMPTY;
    id = SessionKeyGenerator.getInstance().next();
    commentId = SessionKeyGenerator.getInstance().next();
    previousMove = null;
    nextMove = null;
    isInVariation = false;
  }

  /**
   * partitionne le coup initial en sous parties : move, eval, appreciation on
   * laisse les choses comme ep.# qui sont objectifs
   */
  public static void isoleMark(String moveOrig, StringBuilder movePur, StringBuilder ev,
                               StringBuilder app) {
    for (int i = 0; i < moveOrig.length(); i++) {
      char c = moveOrig.charAt(i);
      switch (c) {
        case '!':
        case '?':
          app.append(c);
          break;
        case '+':

        case '=':
          ev.append(c);
          break;
        case '-': // cas du roque

          if (i != moveOrig.length() - 1) {
            if (moveOrig.charAt(i + 1) != 'O') { // une evaluation
              ev.append(c);

            } else { // un roque
              movePur.append(c);
            }
          } else { // une evaluation
            ev.append(c);

          }
          break;
        default:
          movePur.append(c);
      }
    }
  }

  /**
   * renvoie lde dernier coup de la ligne jouee dans variantes ou pas
   */
  public ItemGameTree lastMoveFind(List<String> dejaJoues, int where) {
    ItemGameTree courant = this;
    String mv = dejaJoues.get(where);
    if (courant.getCurrentMove().equals(mv)) {
      // on continue sur le suivant
      //  log.info("cas 0 "+courant.getCurrentMove());
      if (getNextMove() != null) {
        return getNextMove().lastMoveFind(dejaJoues, where + 1);
      }
    } else { // sinon on cherche sur la variation
      List<ItemGameTree> v = courant.getVariations();
      for (ItemGameTree ig : v) {
        //       log.info(mv +" vari "+ig.getCurrentMove());
        courant = ig.lastMoveFind(dejaJoues, where);
        if (courant != null) {
          return courant;
        }
      }
      //   log.error("pas trouve dans la variation "+mv);
      return null;
    }
    return courant;
  }

  public ItemGameTree findById(int id) {

    // log.info("identifiant cherche=" + id + "-" + getId());
    if (getId() == id) {
      return this;
    }
    if (commentId == id) {
      return this;
    }
    for (ItemGameTree item : variations) {
      ItemGameTree loc = item.findById(id);
      if (loc != null) {
        return loc;
      }
    }

    if (getNextMove() != null) {
      return getNextMove().findById(id);
    }
    return null; // pas trouve
  }


  public void updateMark(String sa, String se) {
    StringBuilder move = new StringBuilder();
    StringBuilder ev = new StringBuilder();
    StringBuilder app = new StringBuilder();

    isoleMark(currentMove, move, ev, app);

    currentMove = move + sa + se;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();

    sb.append(currentMove).append(" ");
    if (!StringUtils.isEmpty(comment)) {
      sb.append(" ").append(PgnUtil.START_COMMENT).append(comment).append(PgnUtil.END_COMMENT);
    }


    for (ItemGameTree i : variations) {
      sb.append(" ").append(START_VARIATION);
      ItemGameTree p = i;
      while (p != null) {
        sb.append(p).append(" ");
        p = p.getNextMove();
      }

      sb.append(END_VARIATION).append(" ");
    }


    /*
    if (getNextMove() != null && !StringUtils.isEmpty(getNextMove().getCurrentMove())) {
      sb.append(getNextMove());
    }*/
    return sb.toString();

  }


  public void buildTree(DefaultMutableTreeNode parent) {

    if (variations.size() > 0) {
      // on ajoute au parent
      //       log.info("build tree variations");
      for (ItemGameTree i : variations) {
        i.buildTreeSub(parent);

      }

    }

  }

  private void buildTreeSub(DefaultMutableTreeNode parent) {

    DefaultMutableTreeNode node = new DefaultMutableTreeNode(this);
    parent.add(node);

    if (variations.size() > 0) {
      // on ajoute au parent
      for (ItemGameTree i : variations) {
        i.buildTreeSub(parent);

      }

    }
    // on ajoute au noeud courant
    if (getNextMove() != null && !StringUtils.isEmpty(getNextMove().getCurrentMove())) {
      getNextMove().buildTreeSub(node);
    }
  }


}
