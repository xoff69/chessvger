package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.builder.GameBuilder;
import com.xoff.chessvger.builder.SerializationUtil;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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

    assertTrue(deserialized instanceof CommonGame);
    assertEquals(commonGame.toString(), deserialized.toString());
  }


}
