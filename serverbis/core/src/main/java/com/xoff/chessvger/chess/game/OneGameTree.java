package com.xoff.chessvger.chess.game;

import static com.xoff.chessvger.util.PgnUtil.END_COMMENT;
import static com.xoff.chessvger.util.PgnUtil.END_VARIATION;
import static com.xoff.chessvger.util.PgnUtil.START_COMMENT;
import static com.xoff.chessvger.util.PgnUtil.START_VARIATION;

import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.util.PgnUtil;
import com.xoff.chessvger.util.SessionKeyGenerator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class OneGameTree {

  private static final String NONE = ")(";
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  @Getter
  @Setter
  private ItemGameTree parent;


  public OneGameTree(String moves) {
    parent = extractMoves(moves, new AtomicInteger(), false);
  }

  public List<ItemGameTree> findPathToId(int id) {
// on met le noeud il faudrait pas
    ItemGameTree courant = findById(id);
    // si on trouve on remonte a la racine
    List<ItemGameTree> result = new ArrayList();
    ItemGameTree dernier = courant;
    while (courant != null) {

      dernier = courant;
      courant = courant.getPreviousMove();
      if (courant != null) {
        // on ajoute pas
        //      log.info("pas d' ajout:"+dernier.getCurrentMove());
        //      log.info("pas d' ajout:"+dernier.getNumeroCoup());
        //       log.info("pas d' ajout:"+courant.getNumeroCoup());
        result.add(dernier);
        // on ajoute pas courant
        courant = courant.getPreviousMove();
      } else {
        // on ajoute
        //     log.info("ajout:"+dernier.getCurrentMove());
        //      log.info(" d' ajout:"+dernier.getNumeroCoup());

        result.add(dernier);
      }
    }
    // on inverse pour etre dans le bon sens
    Collections.reverse(result);

    return result;
  }

  /**
   * indique si avec les coups deja joues on se trouve dans la ligne
   * principale permet de detecter si c'est une variante ou pas
   */
  public boolean isLastMoveInMainLine(List<String> dejaJoues) {

    ItemGameTree courant = parent;
    int compteur = 0;
    for (String s : dejaJoues) {
      //    log.info("s vs courant:" + s + "-" + courant.getCurrentMove());
      if (!courant.getCurrentMove().equals(s)) {
        return false;
      }
      courant = courant.getNextMove();
      compteur++;
      if (courant == null && compteur != dejaJoues.size()) {
        return false;
      }
    }
    log.info("je isLastMoveInMainLine:TRUE");
    return true;
  }

  public List<String> extractListMove() {

    List<String> mv = new ArrayList();
    ItemGameTree courant = parent;
    while (courant != null) {
      mv.add(courant.getCurrentMove());
      courant = courant.getNextMove();
    }
    return mv;
  }

  public ItemGameTree lastMoveInMainLine(List<String> dejaJoues) {

    ItemGameTree courant = parent;
    int compteur = 0;
    for (String s : dejaJoues) {
      //  log.info("s vs courant:" + s + "-" + courant.getCurrentMove());
      if (!courant.getCurrentMove().equals(s)) {
        return courant;
      }
      courant = courant.getNextMove();
      compteur++;
      if (courant == null && compteur != dejaJoues.size()) {
        return courant;
      }
    }
    //     log.info("je lastMoveInMainLine:TRUE");
    return courant;
  }

  public ItemGameTree lastMoveFind(List<String> dejaJoues) {
    ItemGameTree courant = parent;
    if (courant != null) {
      return courant.lastMoveFind(dejaJoues, 0);
    }
    return courant;
  }

  public ItemGameTree findById(int id) {
    return parent.findById(id);

  }

  public void update(Position p) {

    if (p.getMoves().isEmpty()) {
      return;
    }
    parent = new ItemGameTree();
    ItemGameTree courant = parent;
    int i = 0;
    for (String s : p.getMoves()) {
      courant.setCurrentMove(s);
      i++;
      ItemGameTree last = courant;
      courant = new ItemGameTree();
      courant.setPreviousMove(last);
      last.setNextMove(courant);
    }
  }

  @Override
  public String toString() {

    return StringUtils.EMPTY;
  }

  private ItemGameTree extractMoves(String ls, AtomicInteger cptchar, boolean isInVariation) {
    ItemGameTree lparent = new ItemGameTree();
    ItemGameTree currentItem = lparent;
    lparent.setInVariation(isInVariation);
    STATES waitingForMove = STATES.NUMBER;

    if (ls == null) {
      return null;
    }
    boolean outOfLoop = false;
    while (cptchar.get() < ls.length() && !outOfLoop) {

      char c = ls.charAt(cptchar.get());
      switch (c) {
        case START_COMMENT:
          cptchar.getAndIncrement();
          //
          StringBuilder comment = new StringBuilder();
          while (cptchar.get() < ls.length()) {
            char cmt = ls.charAt(cptchar.get());
            comment.append(cmt);
            cptchar.getAndIncrement();
            if (cmt == END_COMMENT) {
              break;
            }
          }

          String resultat = comment.substring(0, comment.length() - 1);
          //    System.out.println("commentaire:" + resultat);

          if (currentItem.getPreviousMove() != null) {
            currentItem.getPreviousMove().setComment(resultat);
          } else {
            currentItem.setComment(resultat);
          }
          break;
        case START_VARIATION:
          cptchar.getAndIncrement();
          ItemGameTree variation = extractMoves(ls, cptchar, true);
          currentItem.getPreviousMove().getVariations().add(variation);
          currentItem.setInVariation(true);
          break;
        case END_VARIATION:
          cptchar.getAndIncrement();
          outOfLoop = true;
          break;
        case '.':
          if (isInVariation) {
            cptchar.getAndIncrement();
            cptchar.getAndIncrement();
            waitingForMove = STATES.MOVE2;
          } else {
            log.error("extract . in " + ls);
          }
        case ' ':
          cptchar.getAndIncrement();
          break;
        default:
          if (waitingForMove != STATES.NUMBER) {
            StringBuilder move = new StringBuilder();
            while (cptchar.get() < ls.length() && ls.charAt(cptchar.get()) != ' ' &&
                ls.charAt(cptchar.get()) != START_VARIATION &&
                ls.charAt(cptchar.get()) != END_VARIATION) {
              move.append(ls.charAt(cptchar.get()));
              cptchar.getAndIncrement();
            }

            if (PgnUtil.isScore(move.toString())) {
              outOfLoop = true;
              break;
            }
            if (cptchar.get() < ls.length() && (ls.charAt(cptchar.get()) == START_VARIATION ||
                ls.charAt(cptchar.get()) == END_VARIATION)) {
              cptchar.getAndDecrement();
            }
            //System.out.println(  "move  "+move);
            currentItem.setCurrentMove(move.toString());
            currentItem.setInVariation(isInVariation);
            currentItem.setId(SessionKeyGenerator.getInstance().next());
            ItemGameTree n = new ItemGameTree();
            currentItem.setNextMove(n);
            n.setPreviousMove(currentItem);
            n.setCurrentMove(NONE);

            currentItem = n;
            if (waitingForMove == STATES.MOVE1) {
              waitingForMove = STATES.MOVE2;
            } else {
              waitingForMove = STATES.NUMBER;
            }
            cptchar.getAndIncrement();
          } else {
            // move number .
            StringBuilder moveNumber = new StringBuilder();
            while (cptchar.get() < ls.length() && ls.charAt(cptchar.get()) != '.') {
              moveNumber.append(ls.charAt(cptchar.get()));
              cptchar.getAndIncrement();
            }
            if (PgnUtil.isScore(moveNumber.toString())) {
              outOfLoop = true;
              break;
            }
            //   currentItem.setNumeroCoup(Integer.parseInt(moveNumber.toString()));
            waitingForMove = STATES.MOVE1;
            cptchar.getAndIncrement();
          }

      }
    }
    if (currentItem != null && currentItem.getPreviousMove() != null &&
        currentItem.getCurrentMove().equals(NONE)) {
      currentItem.getPreviousMove().setNextMove(null);
    }
    return lparent;
  }

  private enum STATES {NUMBER, MOVE1, MOVE2}

}
