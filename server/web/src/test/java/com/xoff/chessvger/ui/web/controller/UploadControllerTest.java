package com.xoff.chessvger.ui.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.service.IUploadService;
import com.xoff.chessvger.ui.web.mapper.DatabaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
class UploadControllerTest {
  private static final String DBNAME = "UploadControllerTest";
  private static DatabaseManager databaseManager;
  @Autowired
  DatabaseMapper databaseMapper;
  @Autowired
  private ServerProperties serverProperties;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IUploadService uploadService;
  private MockMultipartFile file =
      new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
          "Hello, World!".getBytes());

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
  @DisplayName("uploadPgn")
  public void expect_result_uploadPgn() throws Exception {


    when(uploadService.uploadPlayer(Mockito.any())).thenReturn(10);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/uploadPgn";

    log.info("url " + url);

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("bdId", String.valueOf(databaseManager.getDatabaseId()));
    // requestParams.addDatabaseManager("file", String.valueOf(databaseManager.getDatabaseId()));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult = this.mockMvc.perform(
            multipart(builder.build().toUri()).file(file).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();


  }

  @Test
  @DisplayName("uploadFidePlayer")
  public void expect_result_uploadFidePlayer() throws Exception {


    when(uploadService.uploadPlayer(Mockito.any())).thenReturn(10);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/uploadFidePlayer";

    log.info("url " + url);

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    // requestParams.addDatabaseManager("file", String.valueOf(databaseManager.getDatabaseId()));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult = this.mockMvc.perform(
            multipart(builder.build().toUri()).file(file).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

  }
}