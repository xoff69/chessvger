package com.xoff.chessvger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ChessVgerApplication.class)
@ActiveProfiles("ut")
public class BaseTest {

  @Value("${profile.property.value}")
  private String propertyString;

  @Test
  @DisplayName("checkProfileForTestUt")
  void checkProfileForTest() {
    Assertions.assertEquals("This the the application.yaml file unit test", propertyString);
  }
}
