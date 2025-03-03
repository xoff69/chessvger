package com.xoff.chessvger.ui.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.xoff.chessvger.ui.web.controller.admin.PlayersController;
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
  private BrowseController browseController;

  @Autowired
  private PlayersController playersController;



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