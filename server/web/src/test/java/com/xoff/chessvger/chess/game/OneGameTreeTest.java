package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xoff.chessvger.chess.board.BoardManager;
import com.xoff.chessvger.chess.board.Position;
import com.xoff.chessvger.util.SessionKeyGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
class OneGameTreeTest {

  private static void printResultat(OneGameTree oneGameTree) {
    System.out.println("RESULTAT");
    ItemGameTree courant = oneGameTree.getParent();
    while (courant != null) {
      System.out.println(courant);
      courant = courant.getNextMove();
    }
  }

  @Test
  @DisplayName("testOneGameTreeEasy")
  void testOneGameTreeEasy() {
    String smoves = " 1. e4 c5";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(smoves);
    List<String> moves = ong.extractListMove();

    assertNotNull(moves);
    assertEquals(2, moves.size());

  }

  @Test
  @DisplayName("testOneGameTreeTestLong")
  void testOneGameTreeTestLong() {
    String smoves =
        " 1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 a6 6. Be3 e5 7. Nb3 Be6 8. Qd2 Nbd7 9. f3 Be7 10. g4 O-O 11. g5 Nh5 12. O-O-O Nb6 13. Kb1 Rc8 14. Rg1 g6 15. Qf2 Nc4 16. Bxc4 Rxc4 17. Nd5 Bxd5 18. Rxd5 b5 19. Qd2 Qc7 20. Rd1 Rc8 21. c3 Rb8 22. Bc5 Nf4 23. Bxd6 Bxd6 24. Rxd6 b4 25. cxb4 Rcxb4 26. Rd8+ Kg7 27. Rxb8 Qxb8 28. Nc5 Rd4 29. Qc2 Rxd1+ 30. Qxd1 Qb5 31. Na4 Nh3 32. Nc3 Qc4 33. Qd6 Qf1+ 34. Kc2 Qxf3 35. Qxe5+ Kg8 36. b4 Qg2+ 37. Kb3 Nxg5 38. Ka4 Nf3 39. Qf6 h5 40. Ka5 Nxh2 41. a4 Ng4 42. Qd8+ Kh7 43. Kxa6 Qg3 44. Nd5 h4 45. Qf8 Qf2 46. b5 h3 47. b6 h2 48. b7 h1=Q 49. b8=Q Qa1 50. Qbe8 Qg7 51. Qfe7 g5 52. a5 Nh6 53. Qc6 Ng8 54. Qec7 g4 55. Kb7 g3 56. a6 g2 57. a7 g1=Q 58. a8=Q Qb1+ 59. Nb6 Nf6 60. e5 Ng4 61. Qae8 Qff5 62. Qce7 Nh6 63. Kc7 Qbe4 64. Nd5 Qfxe5+ 65. Qxe5 Qgxe5+ 66. Qxe5 Qxe5+ 67. Kd8 Kg7 68. Ne7 Qb8+ 69. Kd7 Qa7+ 70. Kd6 Qa3+ 71. Kd7 Qd3+ 72. Ke8 Qh3 73. Qd5 Qg4 74. Qe5+ f6 75. Qd5 Qa4+ 1/2-1/2";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(smoves);
    List<String> moves = ong.extractListMove();

    assertNotNull(moves);
    assertEquals(150, moves.size());
    printResultat(ong);
  }

  @Test
  @DisplayName("testOneGameTreeComment")
  void testOneGameTreeComment() {
    String smoves = " 1. e4 c5   {coucou} 2. Nf3 ";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(smoves);
    assertEquals(3, ong.extractListMove().size());
    printResultat(ong);


  }

  @Test
  @DisplayName("testOneGameTreeVariation")
  void testOneGameTreeVariation() {
    String moves = " 1. e4 c5 2. Nf3 ( 2. Nc3 d6 3. b3 ) d5 3. a3 ";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(moves);
    printResultat(ong);
    assertEquals(5, ong.extractListMove().size());

  }

  @Test
  @DisplayName("testOneGameTreeVariationBlack")
  void testOneGameTreeVariationBlack() {
    String moves = " 1. e4 c5 2. Nf3  d5  ( .. d6 3. b3 ) 3. a3 ";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(moves);
    printResultat(ong);
    assertEquals(5, ong.extractListMove().size());

  }

  @Test
  @DisplayName("testOneGameTreeMultipleVariation")
  void testOneGameTreeMultipleVariation() {
    String moves = " 1. e4 c5 2. Nf3 ( 2. Nc3 d6  3. b3 ) ( 2. h4 d6 (.. b6) 3. b3 )  d5 3. a3 ";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(moves);
    printResultat(ong);
    assertEquals(5, ong.extractListMove().size());

    String moves2 = " 1. e4 c5 2. Nf3 ( 2. Nc3 d6  3. b3 ) ( 2. h4 d6 (.. b6) 3. b3)  d5 3. a3 ";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong2 = new OneGameTree(moves2);
    printResultat(ong2);
    assertEquals(5, ong2.extractListMove().size());
  }

  @Test
  @DisplayName("testOneGameTrueGame")
  void testOneGameTrueGame() {
    String moves =
        "1.e4 e5 2.Nf3 Nc6 3.Bb5 a6 4.Ba4 ( 4.Bc4 ) ( 4.Bd3 ) 4...Nf6 5.d3 { This is a very solid development, to which I was much addicted at the time, because of my ignorance of the multiple variations of the openings. } 5...d6 6.c3 Be7 ( {  In this variation there is the alternative of developing this Bishop via **g7**, after  } 6...g6 ) 7.Nbd2 O-O 8.Nf1 b5 9.Bc2 d5 10.Qe2 dxe4 11.dxe4 Bc5 { Evidently to make room for the Queen at **e7**, but I do not think the move advisable at this stage. } ( 11...Be6 { is a more natural and effective move. It develops a piece and threatens } 12.-- Bc4 { which would have to be stopped. } ) 12.Bg5 Be6 { Now it is not so effective, because White's Queen's Bishop is out, and the Knight, in going to **e3** } 13.Ne3 { defends **c4** and does not block the Queen's Bishop. } 13...Re8 14.O-O Qe7 { # This is bad. Black's game was already not good. He probably had no choice but to take the Knight with the Bishop before making this move. } 15.Nd5 Bxd5 16.exd5 Nb8 { In order to bring it to **d7**, to support the other Knight and also his King's Pawn. White, however, does not allow time for this, and by taking advantage of his superior position is able to win a Pawn. } 17.a4 b4 ( {  Since he had no way to prevent the loss of a Pawn, he should have given it up where it is, and played  } 17...Nbd7 { in order to make his position more solid. The text move not only loses a Pawn, but leaves Black's game very much weakened. } ) 18.cxb4 Bxb4 19.Bxf6 Qxf6 20.Qe4 Bd6 21.Qxh7+ Kf8 { With\n" +
            "        a Pawn more and all his pieces ready for action, while Black is still backward in development, it only remains for White to drive home his advantage before Black can come out with his pieces, in which case, by using the open h-file, Black might be able to start a strong attack against White's King. White is able by his next move to eliminate all danger. # } 22.Nh4 Qh6 { This is practically forced. } ( {  Black could not play  } 22...g6 { because of } 23.Bxg6 { White meanwhile threatened } ) ( 22...-- 23.Qh8+ Ke7 24.Nf5+ Kd7 25.Qxg7 ) 23.Qxh6 gxh6 24.Nf5 h5 25.Bd1 Nd7 26.Bxh5 Nf6 27.Be2 Nxd5 28.Rfd1 Nf4 29.Bc4 Red8 30.h4 a5 { Black must lose time assuring the safety of this Pawn. } 31.g3 Ne6 32.Bxe6 fxe6 33.Ne3 Rdb8 34.Nc4 Ke7 { Black fights a hopeless battle. He is two Pawns down for all practical purposes, and the Pawns he has are isolated and have to be defended by pieces. } 35.Rac1 Ra7 { White threatened } ( 35...-- 36.Nxd6 cxd6 { followed by } 37.Rc7+ ) 36.Re1 Kf6 37.Re4 Rb4 38.g4 Ra6 ( {  If  } 38...Rxa4 { then } 39.Nxd6 { would of course win a piece. } ) 39.Rc3 Bc5 40.Rf3+ Kg7 41.b3 Bd4 42.Kg2 Ra8 43.g5 Ra6 44.h5 Rxc4 45.bxc4 Rc6 46.g6 { Black resigns. [%fmt inline] } 1-0\n" +
            "    ";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(moves);
    printResultat(ong);
    assertEquals(92, ong.extractListMove().size());


  }

  @Test
  @DisplayName("testOneGameTreeVariationAndComment")
  void testOneGameTreeVariationAndComment() {
    String moves = " 1. e4 c5";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(moves);
    assertEquals(2, ong.extractListMove().size());
  }

  @Test
  @DisplayName("testOneGamefindPathToId")
  void testOneGamefindPathToId() {
    String moves =
        " 1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 a6 6. Be3 e5 7. Nb3 Be6 8. Qd2 Nbd7 9. f3 Be7 10. g4 O-O 11. g5 Nh5 12. O-O-O Nb6 13. Kb1 Rc8 14. Rg1 g6 15. Qf2 Nc4 16. Bxc4 Rxc4 17. Nd5 Bxd5 18. Rxd5 b5 19. Qd2 Qc7 20. Rd1 Rc8 21. c3 Rb8 22. Bc5 Nf4 23. Bxd6 Bxd6 24. Rxd6 b4 25. cxb4 Rcxb4 26. Rd8+ Kg7 27. Rxb8 Qxb8 28. Nc5 Rd4 29. Qc2 Rxd1+ 30. Qxd1 Qb5 31. Na4 Nh3 32. Nc3 Qc4 33. Qd6 Qf1+ 34. Kc2 Qxf3 35. Qxe5+ Kg8 36. b4 Qg2+ 37. Kb3 Nxg5 38. Ka4 Nf3 39. Qf6 h5 40. Ka5 Nxh2 41. a4 Ng4 42. Qd8+ Kh7 43. Kxa6 Qg3 44. Nd5 h4 45. Qf8 Qf2 46. b5 h3 47. b6 h2 48. b7 h1=Q 49. b8=Q Qa1 50. Qbe8 Qg7 51. Qfe7 g5 52. a5 Nh6 53. Qc6 Ng8 54. Qec7 g4 55. Kb7 g3 56. a6 g2 57. a7 g1=Q 58. a8=Q Qb1+ 59. Nb6 Nf6 60. e5 Ng4 61. Qae8 Qff5 62. Qce7 Nh6 63. Kc7 Qbe4 64. Nd5 Qfxe5+ 65. Qxe5 Qgxe5+ 66. Qxe5 Qxe5+ 67. Kd8 Kg7 68. Ne7 Qb8+ 69. Kd7 Qa7+ 70. Kd6 Qa3+ 71. Kd7 Qd3+ 72. Ke8 Qh3 73. Qd5 Qg4 74. Qe5+ f6 75. Qd5 Qa4+ 1/2-1/2";
    SessionKeyGenerator.getInstance().reset();
    OneGameTree ong = new OneGameTree(moves);

    List<ItemGameTree> listItemGameTree = ong.findPathToId(24);
    assertEquals(4, listItemGameTree.size());

    List<String> listMoveExtract = ong.extractListMove();
    assertEquals(listMoveExtract.size(), 150);
    Position p = new Position();
    List<String> sub = listMoveExtract;
    for (String s : sub) {
      BoardManager.play(p, s);
    }
    ItemGameTree item = ong.lastMoveFind(p.getMoves());
    assertEquals(item.getCurrentMove(), "Qa4+");


    ItemGameTree itemfindById = ong.findById(24);
    assertEquals(itemfindById.getCurrentMove(), "Nf6");
    List<String> ls = new ArrayList<>();
    ls.add("e4");
    ls.add("c5");
    ls.add("Nf3");
    ls.add("d5");
    assertFalse(ong.isLastMoveInMainLine(ls));
    assertNotNull(ong.lastMoveInMainLine(ls));

  }


}