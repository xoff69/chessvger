package com.xoff.chessvger.queues.materialposition;

import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.chess.game.ItemGameTree;
import com.xoff.chessvger.chess.game.OneGameTree;
import com.xoff.chessvger.chess.move.ResultInterpretation;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaterialPositionsUtil {

  public static List<CoupleZobristMaterial> parseMoves2(String moves) {

    List<CoupleZobristMaterial> result = new ArrayList<>();
    OneGameTree ogt = new OneGameTree(moves);

    //TraceUtils.traceInFile("ogt="+ogt.toString());
    Position position = new Position();
    ItemGameTree courant = ogt.getParent();
    int compteur = 0;
    while (courant != null) {

      ResultInterpretation rer = BoardManager.play(position, courant.getCurrentMove());
      //  TraceUtils.traceInFile("\n "+(compteur+1)+ " je joue : "+courant.getCurrentMove()+" "+position.toString());

      if (rer.isInvalide()) {
        log.error(moves);
        return result;
      }
      // tres important ce OU, en effet, ce n est qu a la fin du parsin des coups que l information est complete
      // FIME
      //game.setInformationsFaitDeJeu(game.getInformationsFaitDeJeu() | rer.getFaitsdejeu());
      CoupleZobristMaterial czm = position.evaluateZobristAndMaterial();
      // FIXME game.setLastPosition(czm.getZobrist());
      CoupleZobristMaterial couple = new CoupleZobristMaterial(czm.getZobrist(), czm.getMaterial());
      result.add(couple);


      courant = courant.getNextMove();
      compteur++;
    }
    // FIXME game.setNbcoups(compteur);

    return result;
  }
}
