package com.xoff.chessvger.ui.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.service.IDatabaseService;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.ui.web.mapper.DatabaseMapper;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
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
import org.springframework.http.MediaType;
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
class BDControllerTest {
  private static final String DBNAME = "BDControllerTest";
  private static DatabaseManager databaseManager;
  @Autowired
  DatabaseMapper databaseMapper;
  @Autowired
  private ServerProperties serverProperties;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IDatabaseService iDatabaseService;

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
  public void expect_result_testfiliationmap() throws Exception {
    GlobalManager.getInstance().getFiliationMap().add(1L,2L);
    GlobalManager.getInstance().getFiliationMap().add(1L,3L);
    GlobalManager.getInstance().getFiliationMap().add(1L,4L);
    assertEquals(3,GlobalManager.getInstance().getFiliationMap().getValuesForKey(1L).size());

    GlobalManager.getInstance().getFiliationMap().add(2L,2L);
    GlobalManager.getInstance().getFiliationMap().add(2L,5L);
    assertEquals(2,GlobalManager.getInstance().getFiliationMap().getValuesForKey(2L).size());
  }
  @Test
  public void expect_result_forallbds() throws Exception {

    List<Database> databases = GlobalManager.getInstance().getDatabaseMap().list();
    int pageNumber = 1;
    int pageSize = 100;
    Pageable paging = PageRequest.of(pageNumber, pageSize);
    PageView pageView = Page.compute(paging, databaseMapper.mapListEntity2Dto(databases));
    when(iDatabaseService.managePage(paging,any())).thenReturn(pageView);


    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/bds";
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("page", String.valueOf(pageNumber));
    requestParams.add("size", String.valueOf(pageSize));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(DBNAME))).andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("bdCreate")
  public void expect_result_bdCreate() throws Exception {
    DBForm dbForm = new DBForm();
    dbForm.setDescription("test dbform");

    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/bdCreate";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
            post(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
                .content(UtilTest.asJsonString(dbForm))).andExpect(status().isOk())
        .andExpect(content().string(containsString("test dbform"))).andReturn();

  }

  @Test
  @DisplayName("bdClose")
  public void expect_result_bdClose() throws Exception {

    iDatabaseService.dbOpen(databaseManager.getDatabaseId());
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/bd/close/" +
        databaseManager.getDatabaseId();

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult =
        this.mockMvc.perform(patch(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())

            .andReturn();

  }
}