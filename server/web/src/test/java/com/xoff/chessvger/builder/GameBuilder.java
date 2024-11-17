package com.xoff.chessvger.builder;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.MetaCommonGame;
import com.xoff.chessvger.util.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBuilder {
  public static final long WHITE_FIDE_ID = 123456L;
  public static final long BLACK_FIDE_ID = 678989L;
  private static final Random rand = new Random();
  private static final String[] PGNS =
      {"1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 e6 6. g4 h6 7. Bg2 a6 8. h3 g5 9. Be3 Nbd7 10. Qe2 Ne5 11. O-O-O Qc7 12. Nf3 Ng6 13. h4 Nxg4 14. hxg5 Bd7 15. Bd2 O-O-O 16. Nd4 Bg7 17. Qxg4 Bxd4 18. gxh6 Ne5 19. Qe2 Nc4 20. Rh3 Nxd2 21. Qxd2 Be5 22. f4 Bf6 23. Qxd6 Qxd6 24. Rxd6 Rdg8 25. Bf3 e5 26. f5 Rg1+ 27. Rd1 Bg5+ 28. Kb1 Rxd1+ 29. Bxd1 Rxh6 30. Rxh6 Bxh6 31. Bh5 f6 32. Bf7 Kd8 33. Bd5 Kc7 34. Ne2 b6 35. c3 Kd6 36. Kc2 Bxf5 37. Bb7 Bg6  38. Ng3 Bf4 39. Nf5+ Bxf5",
          "1. e4 e5 2. Nf3 Nc6 3. Bc4 Nf6 4. d3 Be7 5. O-O O-O 6. a4 d6 7. Nbd2 Be6 8. Re1 Bxc4 9. dxc4 Re8 10. Nf1 Bf8 11. Bg5 h6 12. Bxf6 Qxf6 13. Ne3 Qe6 14. a5 Ne7 15. Ra3 g6 16. h4 Bg7 17. h5 Rad8 18. a6 b6 19. Nd5 Rd7 20. hxg6 fxg6 21. Nh4 c6 22. Nxe7+ Rexe7 23. Rg3 g5 24. Nf5 Rf7 25. Rd3 Bf8 26. b3 d5 27. Qg4 Kh7 28. cxd5 cxd5 29. Nxh6 Qxg4 30. Nxg4 Bc5 31. Re2",
          "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 8. c3 O-O 9. h3 Na5 10. Bc2 c5 11. d4 Nd7 12. Nbd2 cxd4 13. cxd4 Nc6 14. Nf1 exd4 15. Nxd4 Nxd4 16. Qxd4 Ne5 17. Qd1 Bf6 18. Ne3 Be6 19. a4 Nc6 20. Nd5 Bxd5 21. exd5 Nb4 22. Bb1 bxa4 23. Rxa4 a5 24. Bd2 g6 25. Bxb4 axb4 26. Rxb4 Qa5 27. Ree4 Rab8 28. Ra4 Qb5 29. b4 Rfe8 30. Bd3 Qxd5 31. Qf3 Rxe4 32. Bxe4 Qd4 33. Ra8"};

  public static List<CommonGame> generateGames(int nombre) {
    List<CommonGame> l = new ArrayList<>();
    for (int i = 0; i < nombre; i++) {
      l.add(buildGame());
    }
    return l;
  }

  public static CommonGame buildGame() {
    CommonGame game = new CommonGame();
    game.setId(rand.nextInt(1000000));
    game.setPartieAnalysee(false);
    game.setDeleted(false);
    game.setLastUpdate(System.currentTimeMillis());

    game.setFirstMove("e4");
    game.setEvent("event 2023");
    game.setSite("site 2023");
    game.setDate("1998.10.21");
    game.setEventDate("1998.01.12");
    game.setRound("5.4");
    game.setResult(Constants.RESULT_1_0);
    game.setWhiteTitle("GM");
    game.setBlackTitle("GM");
    game.setWhiteElo(PlayerBuilder.buildElo());
    game.setBlackElo(PlayerBuilder.buildElo());
    game.setEco("A00");
    game.setOpening("Catalan");

    game.setWhiteFideId(WHITE_FIDE_ID);
    game.setBlackFideId(BLACK_FIDE_ID);


    game.setNbcoups(100);
    game.setLastPosition(rand.nextInt(10));

    game.setInformationsFaitDeJeu(0);
    game.setMoves(PGNS[rand.nextInt(100) % PGNS.length]);
    game.setInteret(rand.nextInt(10));
    game.setTheorique(rand.nextBoolean());
    game.setFavori(rand.nextBoolean());
    game.setMetaCommonGame(null);
    return game;
  }

  public static CommonGame buildGame(String moves) {
    CommonGame game = new CommonGame();
    game.setId(rand.nextInt(1000000));
    game.setPartieAnalysee(false);
    game.setDeleted(false);
    game.setLastUpdate(System.currentTimeMillis());

    game.setFirstMove("e4");
    game.setEvent("event 2023");
    game.setSite("site 2023");
    game.setDate("1998.10.21");
    game.setEventDate("1998.01.12");
    game.setRound("0.0");
    game.setResult(Constants.RESULT_1_0);
    game.setWhiteTitle("GM");
    game.setBlackTitle("GM");
    game.setWhiteElo(PlayerBuilder.buildElo());
    game.setBlackElo(PlayerBuilder.buildElo());
    game.setEco("A00");
    game.setOpening("Catalan");

    game.setWhiteFideId(WHITE_FIDE_ID);
    game.setBlackFideId(BLACK_FIDE_ID);


    game.setNbcoups(100);
    game.setLastPosition(rand.nextInt(10));

    game.setInformationsFaitDeJeu(0);
    game.setMoves(moves);
    game.setInteret(rand.nextInt(10));
    game.setTheorique(rand.nextBoolean());
    game.setFavori(rand.nextBoolean());
    game.setMetaCommonGame(new MetaCommonGame());
    return game;
  }
}
