package com.xoff.chessvger.chess.game;

import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.builder.SerializationUtil;
import com.xoff.chessvger.model.CommonGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IT")
public class TestSerializationCommonGameSerialization {
    private CommonGame commonGame;

    @BeforeEach
    public void setUp() {

        commonGame = GameBuilder.buildGame();
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {

        byte[] serialized = SerializationUtil.serialize(commonGame);
        Object deserialized = SerializationUtil.deserialize(serialized);

        assertInstanceOf(CommonGame.class, deserialized);
        assertEquals(commonGame.toString(), deserialized.toString());
    }


}
