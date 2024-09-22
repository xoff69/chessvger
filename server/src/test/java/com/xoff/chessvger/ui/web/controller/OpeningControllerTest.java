package com.xoff.chessvger.ui.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.chess.opening.Opening;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.service.IOpeningService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.util.UriComponentsBuilder;

@Tag("IT")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("it")
class OpeningControllerTest {


  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IOpeningService iOpeningService;


  @Test
  public void expect_result_forallaopenings() throws Exception {

    List<Opening> openings = GlobalManager.getInstance().getOpeningManager().list();

    when(iOpeningService.getAll()).thenReturn(openings);


    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/openings";
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Lopez"))).andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

}