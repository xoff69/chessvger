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
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.service.IBrowseService;
import com.xoff.chessvger.view.StatBrowserView;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
class BrowseControllerTest {
  private static final String DBNAME = "BrowseControllerTest";
  private static DatabaseManager databaseManager;

  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IBrowseService iBrowseService;

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
  @DisplayName("all browse data test")
  public void expect_result_forallbrowse() throws Exception {

    List<StatBrowserView> browserViewList = new ArrayList<>();
    StatBrowserView sb = new StatBrowserView();
    sb.setCoup("g4");
    browserViewList.add(sb);
    when(iBrowseService.loadAll(databaseManager.getDatabaseId())).thenReturn(browserViewList);


    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/browses";
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("bdId", String.valueOf(databaseManager.getDatabaseId()));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("g4"))).andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}