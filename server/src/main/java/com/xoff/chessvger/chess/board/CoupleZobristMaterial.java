package com.xoff.chessvger.chess.board;


import lombok.Getter;

@Getter
public class CoupleZobristMaterial {


  private final int zobrist;

  private final long material;


  public CoupleZobristMaterial(int zobrist, long material) {
    this.zobrist = zobrist;
    this.material = material;
  }


}
