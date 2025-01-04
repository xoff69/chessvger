package com.xoff.chessvger.chess.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

@Tag("IT")
class DataBaseTest {


  private Database database;

  @BeforeEach
  void setUp() {
    database = new Database();
    database.setNbgames(10);
    database.setId(1);
    database.setName("DB TEST");
    database.setDescription("DB TEST desc");

  }

  @Test
  void testSerializable() {
    Serializable serialized = SerializationUtils.serialize(database);
    Object deserialized = SerializationUtils.deserialize((byte[]) serialized);
    assertEquals(database, deserialized);
  }


}