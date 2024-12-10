package com.xoff.chessvger.parser.materialposition;

import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.parser.material.MaterialEntity;
import com.xoff.chessvger.parser.position.PositionEntity;
import java.util.ArrayList;
import java.util.List;

public class PositionMaterialProducer {
  public static void enqueuePositionMaterial(long gameId, List<CoupleZobristMaterial> list) {


    long id = 1L;
    for (CoupleZobristMaterial coupleZobristMaterial : list) {

      PositionEntity positionEntity = new PositionEntity();
      MaterialEntity materialEntity = new MaterialEntity();
      positionEntity.setId(id);
      materialEntity.setId(id);
      positionEntity.setPosition(coupleZobristMaterial.getZobrist());
      materialEntity.setMaterial(coupleZobristMaterial.getMaterial());
      positionEntity.setGames(new ArrayList<>());
      materialEntity.setGames(new ArrayList<>());
      positionEntity.getGames().add(gameId);
      materialEntity.getGames().add(gameId);

      id++;
      // insert material
      // insert position
    }
    System.out.println("positions/material in queue: " + list.size());
  }
}
