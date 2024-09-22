package com.xoff.chessvger.chess.opening;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.game.CommonGame;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class OpeningHelperTest {

  @Test
  void codeECO() {
    List<String> lop = OpeningHelper.codeECO();
    assertEquals(lop.size(), 5 * 100);
  }

  @Test
  void getInfoOpening() {
    String moves = " 1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 ";
    CommonGame game = GameBuilder.buildGame(moves);

    game.setEco("");
    OpeningHelper openingHelper = new OpeningHelper();
    Opening op = openingHelper.getInfoOpening(game);

    assertEquals(op.getEco(), "B98");

    assertEquals(op.toString(), "B98:Sicilian: Najdorf, 7...Be7");

  }
}