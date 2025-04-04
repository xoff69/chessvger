package com.xoff.chessvger.chess.filter;


import com.xoff.chessvger.builder.SerializationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    private Filter filter;

    @BeforeEach
    public void setUp() {

        filter = new Filter("test", 1);
        filter.setMaterialPrecis(true);
        filter.setBlack("noir");
        filter.setWhite("blanc");
    }


    @Test
    void testSerialization() throws IOException, ClassNotFoundException {

        byte[] serialized = SerializationUtil.serialize(filter);
        Object deserialized = SerializationUtil.deserialize(serialized);

        assertInstanceOf(Filter.class, deserialized);
        assertEquals(filter.toString(), deserialized.toString());
    }
}