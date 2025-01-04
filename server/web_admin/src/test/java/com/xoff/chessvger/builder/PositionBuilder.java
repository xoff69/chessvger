package com.xoff.chessvger.builder;

import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.Position;

public class PositionBuilder {
  // TODO
  public static Position buildPosition() {
    Position position = new Position();

    BoardManager.play(position, "e4");
    BoardManager.play(position, "c5");
    BoardManager.play(position, "Nf3");
    BoardManager.play(position, "d6");
    return position;
  }
}
