package com.xoff.chessvger.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.chess.board.Position;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestPosition {
  private final static String FEN_EXEMPLE = "1k6/3K1B2/8/2N5/8/8/8/8 b - - 0 9";

  private static final int DELTA = 5;

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
  @DisplayName("testRecherchePosition")
  public void testRecherchePosition() {

    // https://lichess.org/cqK8PklQ#34
    // on ajoute une partie la bd
    String moves =
        " 1. Nf3 Nf6 2. d4 b6 3. c4 Bb7 4. g3 e6 5. Bg2 c5 6. O-O cxd4 7. Qxd4 Nc6 8. Qc3 Bb4 9. Qc2 O-O 10. a3 Be7 11. Nc3 a6 12. Bf4 Na5 13. b3 Rc8 14. Qb2 d5 15. cxd5 Nxd5 16. Nxd5 Bxd5 17. Rfd1 Bf6 18. Be5 Bxe5 19. Nxe5 Qe7 20. e4 Bxb3 21. Rd7 Qc5 22. Re1 Qc3 23. Qxc3 Rxc3 24. Ra7 Ba4 25. Rxa6 Rxa3 26. Rxb6 Bb3 27. Rb1 f6 28. Nd7 Rd8 29. Nc5 Rd1+ 30. Rxd1 Bxd1 31. Nxe6 Ra1 32. Nd4 Be2+ 33. Bf1 Bxf1 34. Nc2 Rc1 35. Ne3 Bh3+ 0-1";
    CommonGame game = GameBuilder.buildGame(moves);
    databaseManager.upsert(game, DBOperation.AJOUT);

    // on isole une position de cette partie
    String fen = "2rq1rk1/5ppp/pp2pb2/n2b4/5B2/PP3NP1/1Q2PPBP/R2R2K1 w - - 2 18";
// verifier l encodage fen

    Position p = Position.fen2Position(fen);
    CoupleZobristMaterial czm = p.evaluateZobristAndMaterial();
    Filter f = new Filter("testRecherchePosition", 1);
    f.setCasGeneral(false);
    f.setCasOtherCriteria(false);
    f.setZobrist(czm.getZobrist());
    f.setCasPosition(true);
    List<CommonGame> l1 = databaseManager.search(f);

    assertEquals(1, l1.size());
  }


}
