package com.xoff.chessvger.chess.engine;

import org.junit.jupiter.api.Tag;

@Tag("IT")
public class TestEngine {
/* FIXME TODO
    private static final String SF_PATH = Global.getProperty("stockfish.path");


    private Engine engine;

    @BeforeEach
    public void setUp() {
        Global.getInstance().getGlobalKeyGenerator().reset();
        engine = EngineFactory.createDefaultEngineForSystem();

    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {

        byte[] serialized = SerializationUtil.serialize(engine);
        Object deserialized = SerializationUtil.deserialize(serialized);

        assertTrue(deserialized instanceof Engine);
        assertEquals(engine.toString(), deserialized.toString());
    }

    @Test
    @DisplayName("testLigneevalueees")
    public void testLigneevalueees() {
        String FEN_EXEMPLE = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";

        StockFish s = new StockFish(5, 15, SF_PATH);
        s.startEngine();
        String res = s.getBestMove(FEN_EXEMPLE, 1500);
        EvalAndBest eb = engine.evalue(FEN_EXEMPLE, 1500);
        s.stopEngine();
        System.out.println("resulat " + res);
        assertTrue("Nc6".equals(res) || "d6".equals(res));
        assertNotNull(eb);

    }

    @Test
    @DisplayName("testBestmoveeteval")
    public void testBestmoveeteval() {
        StockFish client = new StockFish(5, 15, SF_PATH);
        String FEN = "8/6pk/8/1R5p/3K3P/8/6r1/8 b - - 0 42";
        String FEN_EXEMPLE = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";

// initialize and connect to engine
        if (client.startEngine()) {
            System.out.println("Engine has started..");
        } else {
            System.out.println("Oops! Something went wrong..");
        }

// send commands manually

// getDatabaseManager the best move for a position with a given think time
        int temps = 1000;

        float evalScore = client.getEvalScore(FEN_EXEMPLE, temps);
// getDatabaseManager all the legal moves from a given position

        client.stopEngine();

        assert (evalScore < 0.0);

    }*/
}
