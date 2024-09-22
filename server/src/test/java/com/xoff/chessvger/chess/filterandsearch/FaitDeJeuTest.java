package com.xoff.chessvger.chess.filterandsearch;

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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
@Slf4j
public class FaitDeJeuTest {


  private static Database database;
  private static DatabaseManager databaseManager;


  @BeforeAll
  public static void beforeAll() {
    database = DatabaseBuilder.buildDatabase("DataBaseManagerTest");
    databaseManager = new DatabaseManager(database);

    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @BeforeEach
  public void beforeEach() {
    databaseManager.clear();
    DatabaseBuilder.feedDatabase(databaseManager);
  }

  @Test
  @DisplayName("testPionsTriples")
  public void testPionsTriples() {
    String moves =
        " 1. e4 Nf6 2. Bc4 e5 3. d3 c6 4. Qe2 Bc5 5. Nc3 b5 6. Bb3 a5 7. a3 d6 8. f4 a4 9. Ba2 Qa5 10. fxe5 dxe5 11. Bd2 Bg4 12. Nf3 Nbd7 13. Nd5 Qa7 14. Ne3 Be6 15. Bxe6 fxe6 16. Ng5 Nf8 17. Ng4 h6 18. Nxf6+ gxf6 19. Qh5+ Kd7 20. Nf3 Rd8 21. Ke2 Kc8 22. Rac1 Qg7 23. g3 Ng6 24. c4 Qf7 25. Qh3 Qd7 26. Ne1 bxc4 27. Rxc4 Ba7 28. Ba5 Rdf8 29. Nf3 f5 30. Nd2 fxe4 31. Nxe4 Rf3 32. Rxc6+ Kb8 33. Rd6 Re3+ 34. Kd2 Qb5 35. Bc3 Nf4 36. Qf1 Re2+ 37. Qxe2 Nxe2 38. Kxe2 Bd4 39. Rf1 1/2-1/2";
    CommonGame game = GameBuilder.buildGame(moves);
    databaseManager.upsert(game, DBOperation.AJOUT);

    Filter f = new Filter("testPionsTriples", 1);
    f.setPionstrples(true);
    f.setCasOtherCriteria(false);
    f.setCasGeneral(true);

    List<CommonGame> l1 = databaseManager.search(f);

    // FIXME assertEquals(1, l1.size());
  }

  @Test
  @DisplayName("testBxf7")
  public void testBxf7() {

    String moves =
        " 1. e4 e5 2. Nf3 Nc6 3. Bc4 Bc5 4. Bxf7+ Kxf7 5. Nxe5+ Nxe5 6. Qh5 Ng6 7. Qd5+ Ke8 8. Qxc5 d6 9. Qe3 N8e7 10. O-O Be6 11. d4 c6 12. f4 Bc4 13. Rf3 Qd7 14. b3 Bf7 15. f5 Nf8 16. f6 gxf6 17. Rxf6 Bg6 18. Ba3 Ng8 19. Rxd6 Qg4 20. Nd2 Rd8 21. e5 Rxd6 22. exd6+ Kd7 23. Re1 Ne6 24. Qe5 Qxd4+ 25. Qxd4 Nxd4 26. Nc4 Nxc2 27. Ne5+ Kd8  28. Rf1 Nxa3 29. Rf8+ Be8 30. d7 Ke7 31. dxe8=Q+ Kd6 32. Nf7+ 1-0";
    CommonGame game = GameBuilder.buildGame(moves);
    databaseManager.upsert(game, DBOperation.AJOUT);

    Filter f = new Filter("testBxf7", 1);
    f.setCasGeneral(true);
    f.setBxf7(true);
    // ca c est dans les faits de jeu, pas dans le materiel

    f.setCasOtherCriteria(false);
    List<CommonGame> l1 = databaseManager.search(f);

    assertEquals(1, l1.size());
  }


}
