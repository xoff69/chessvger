package com.xoff.chessvger.chess.stat;


import com.xoff.chessvger.builder.SerializationUtil;
import com.xoff.chessvger.model.StatBrowser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StatBrowserTest {
    private StatBrowser statBrowser;

    @BeforeEach
    public void setUp() {
        statBrowser = new StatBrowser();
        statBrowser.setBlanc(1);
        statBrowser.setLevel(11);
        statBrowser.setLastGameDate("xxxxxxx");
    }


    @Test
    void testSerialization() throws IOException, ClassNotFoundException {

        byte[] serialized = SerializationUtil.serialize(statBrowser);
        Object deserialized = SerializationUtil.deserialize(serialized);

        assertInstanceOf(StatBrowser.class, deserialized);
        assertEquals(statBrowser.getLevel(), ((StatBrowser) deserialized).getLevel());
    }

}