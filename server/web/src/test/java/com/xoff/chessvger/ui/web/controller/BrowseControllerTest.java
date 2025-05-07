package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.builder.DatabaseBuilder;
import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.service.IBrowseService;
import com.xoff.chessvger.view.StatBrowserView;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


}