package com.xoff.chessvger.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestMaterial {


  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("TestFaitDeJeu");
    databaseManager = new DatabaseManager(database);
    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }


  @Test
  @DisplayName("testMaterial2Dames")
  @Order(1)
  public void testMaterial2Dames() {

    String moves =
        " 1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 a6 6. Be3 e5 7. Nb3 Be6 8. Qd2 Nbd7 9. f3 Be7 10. g4 O-O 11. g5 Nh5 12. O-O-O Nb6 13. Kb1 Rc8 14. Rg1 g6 15. Qf2 Nc4 16. Bxc4 Rxc4 17. Nd5 Bxd5 18. Rxd5 b5 19. Qd2 Qc7 20. Rd1 Rc8 21. c3 Rb8 22. Bc5 Nf4 23. Bxd6 Bxd6 24. Rxd6 b4 25. cxb4 Rcxb4 26. Rd8+ Kg7 27. Rxb8 Qxb8 28. Nc5 Rd4 29. Qc2 Rxd1+ 30. Qxd1 Qb5 31. Na4 Nh3 32. Nc3 Qc4 33. Qd6 Qf1+ 34. Kc2 Qxf3 35. Qxe5+ Kg8 36. b4 Qg2+ 37. Kb3 Nxg5 38. Ka4 Nf3 39. Qf6 h5 40. Ka5 Nxh2 41. a4 Ng4 42. Qd8+ Kh7 43. Kxa6 Qg3 44. Nd5 h4 45. Qf8 Qf2 46. b5 h3 47. b6 h2 48. b7 h1=Q 49. b8=Q Qa1 50. Qbe8 Qg7 51. Qfe7 g5 52. a5 Nh6 53. Qc6 Ng8 54. Qec7 g4 55. Kb7 g3 56. a6 g2 57. a7 g1=Q 58. a8=Q Qb1+ 59. Nb6 Nf6 60. e5 Ng4 61. Qae8 Qff5 62. Qce7 Nh6 63. Kc7 Qbe4 64. Nd5 Qfxe5+ 65. Qxe5 Qgxe5+ 66. Qxe5 Qxe5+ 67. Kd8 Kg7 68. Ne7 Qb8+ 69. Kd7 Qa7+ 70. Kd6 Qa3+ 71. Kd7 Qd3+ 72. Ke8 Qh3 73. Qd5 Qg4 74. Qe5+ f6 75. Qd5 Qa4+ 1/2-1/2";
    CommonGame game = GameBuilder.buildGame(moves);
    databaseManager.upsert(game, DBOperation.AJOUT);

    Filter f = new Filter("testMaterial2Dames", 1);
    f.setNbdamenoir(2);
    f.setCasOtherCriteria(true);
    f.setCasGeneral(false);
    List<CommonGame> l1 = databaseManager.search(f);

    assertEquals(1, l1.size());
  }

  @Test
  @DisplayName("testMaterial3Dames")
  @Order(2)
  public void testMaterial3Dames() {

    // on utilise le game de test de testMaterial2Dames

    Filter f = new Filter("testMaterial3Dames", 1);
    f.setNbdameblanc(3);
    f.setCasOtherCriteria(true);
    f.setCasGeneral(false);
    f.setMaterialPrecis(false);
    List<CommonGame> l1 = databaseManager.search(f);

    assertEquals(1, l1.size());
  }


}
