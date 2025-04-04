package com.xoff.chessvger.chess.position;


import com.xoff.chessvger.builder.SerializationUtil;
import com.xoff.chessvger.model.AnalyzedPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzedPositionTest {
    private AnalyzedPosition analyzedPosition;

    @BeforeEach
    public void setUp() {
        analyzedPosition = new AnalyzedPosition(10L, 1.0f, 10, "xxx");

    }


    @Test
    void testSerialization() throws IOException, ClassNotFoundException {

        byte[] serialized = SerializationUtil.serialize(analyzedPosition);
        Object deserialized = SerializationUtil.deserialize(serialized);

        assertInstanceOf(AnalyzedPosition.class, deserialized);
        assertEquals(analyzedPosition.toString(), deserialized.toString());
    }
}