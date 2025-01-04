package com.xoff.chessvger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Tag("IT")

@SpringBootTest(classes = ChessVgerApplication.class)
@ActiveProfiles("it")
public class SBBaseTest {

  @Value("${profile.property.value}")
  private String propertyString;

  @Test
  @DisplayName("checkProfileForTestIt")
  void checkProfileForTest() {


    Assertions.assertEquals("This the the application.yaml file it test", propertyString);
  }
}
