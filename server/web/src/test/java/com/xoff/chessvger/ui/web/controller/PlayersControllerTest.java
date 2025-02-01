package com.xoff.chessvger.ui.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Tag("IT")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("it")
class PlayersControllerTest {
  private static final String DBNAME = "PlayersControllerTest";
  private static DatabaseManager databaseManager;

  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IPlayerService iPlayerService;


  @BeforeAll
  public static void beforeAll() {
    Database database = DatabaseBuilder.buildDatabase(DBNAME);
    databaseManager = new DatabaseManager(database);

    GlobalManager.getInstance().addDatabaseManager(databaseManager);
  }

  @AfterAll
  public static void afterAll() {
    databaseManager.finish();
  }

  @BeforeEach
  public void beforeEach() {
    databaseManager.clear();
    DatabaseBuilder.feedDatabase(databaseManager);
  }

  @Test
  @DisplayName("all players test")
  public void expect_result_forallplayers() throws Exception {


    int pageNumber = 1;
    int pageSize = 100;
    Pageable paging = PageRequest.of(pageNumber, pageSize);
    GlobalManager.getInstance().getCommonPlayerManager().clear();
    CommonPlayer player1 =
        GlobalManager.getInstance().getCommonPlayerManager().findOrAdd("toto", 0L);
    CommonPlayer player2 =
        GlobalManager.getInstance().getCommonPlayerManager().findOrAdd("titi", 0L);
    List<CommonGame> games = databaseManager.getGlobalGameManager().getAllGamesReadOnly();
    for (CommonGame game : games) {
      game.setWhiteFideId(player1.getIdnumber());
      game.setBlackFideId(player2.getIdnumber());
      databaseManager.upsert(game, DBOperation.UPDATE);
    }
    databaseManager.postUpdateGameAndStat();

    PageView pageView =
        Page.compute(paging, databaseManager.getPlayersWithGames(StringUtils.EMPTY,new Pageable(2,2)));


    when(iPlayerService.managePage(paging, databaseManager.getDatabaseId())).thenReturn(pageView);


    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/players";
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("bdId", String.valueOf(databaseManager.getDatabaseId()));
    requestParams.add("page", String.valueOf(pageNumber));
    requestParams.add("size", String.valueOf(pageSize));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("toto"))).andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}