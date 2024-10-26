package com.xoff.chessvger.chess.board;


import lombok.Getter;

@Getter
public class CoupleZobristMaterial {


  private final long zobrist;

  private final long material;


  public CoupleZobristMaterial(long zobrist, long material) {
    this.zobrist = zobrist;
    this.material = material;
  }


}
