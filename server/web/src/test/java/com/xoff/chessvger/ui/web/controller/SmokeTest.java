package com.xoff.chessvger.ui.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.xoff.chessvger.ui.web.controller.tomigrate.BrowseController;
import com.xoff.chessvger.ui.web.controller.tomigrate.FeatureController;
import com.xoff.chessvger.ui.web.controller.tomigrate.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SmokeTest {

  @Autowired
  private GamesController gamesController;
  @Autowired
  private DatabaseController bdController;
  @Autowired
  private FeatureController featureController;

  @Autowired
  private BrowseController browseController;

  @Autowired
  private PlayersController playersController;

  @Autowired
  private UserController userController;

  @Test
  void contextLoads() throws Exception {
    assertThat(gamesController).isNotNull();
    assertThat(bdController).isNotNull();
    assertThat(featureController).isNotNull();
    assertThat(browseController).isNotNull();
    assertThat(playersController).isNotNull();
    assertThat(userController).isNotNull();
  }
}