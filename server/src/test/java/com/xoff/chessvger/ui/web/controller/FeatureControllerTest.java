package com.xoff.chessvger.ui.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xoff.chessvger.ConstantsTest;
import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.service.FeatureService;
import com.xoff.chessvger.ui.web.mapper.FeatureMapper;
import com.xoff.chessvger.ui.web.view.FeatureDto;
import com.xoff.chessvger.ui.web.view.PageView;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Tag("IT")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Slf4j
class FeatureControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private FeatureService featureService;
  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private FeatureMapper mapper;

  @Test
  @DisplayName("feature getDatabaseManager by id")
  public void experct_result_byid() throws Exception {

    Feature item = new Feature();
    item.setId(1L);
    item.setName("findFeature");
    FeatureDto dto = mapper.entity2Dto(item);


    when(featureService.findById(1L)).thenReturn(dto);


    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/features/" + 1;

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("findFeature"))).andReturn();
  }

  @Test
  @DisplayName("feature post create")
  public void expect_result_rightCreate() throws Exception {
    FeatureDto form = new FeatureDto();
    form.setName("test FeatureDto");
    when(featureService.create(Mockito.any())).thenReturn(form);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/features/featureCreate";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
            post(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
                .content(UtilTest.asJsonString(form))).andExpect(status().isCreated())
        .andExpect(content().string(containsString("test FeatureDto"))).andReturn();

  }

  @Test
  @DisplayName("feature put update")
  public void expect_result_rightPut() throws Exception {
    FeatureDto form = new FeatureDto();
    form.setName("test FeatureDto");
    when(featureService.update(Mockito.any(), Mockito.any())).thenReturn(form);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/features/5";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
            put(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
                .content(UtilTest.asJsonString(form))).andExpect(status().isOk())
        .andExpect(content().string(containsString("test FeatureDto"))).andReturn();

  }

  @Test
  @DisplayName("feature post delete")
  public void expect_result_feautredelete() throws Exception {
    FeatureDto form = new FeatureDto();
    form.setName("test FeatureDto");
    when(featureService.delete(Mockito.any())).thenReturn(true);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/features/delete/5";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
        delete(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
            .content(UtilTest.asJsonString(form))).andExpect(status().isNoContent()).andReturn();

  }

  @Test
  @DisplayName("features")
  public void expect_result_forallrights() throws Exception {
    int pageNumber = 1;
    int pageSize = 100;
    Pageable paging = PageRequest.of(pageNumber, pageSize);
    PageView<FeatureDto> pageView = new PageView<FeatureDto>();
    List<FeatureDto> features = new ArrayList<>();
    for (long i = 0; i < 3; i++) {
      Feature r = new Feature();
      r.setId(i);
      r.setName("features " + i);
      features.add(mapper.entity2Dto(r));
    }

    featureService.findAll(paging);

    pageView.setItems(features);
    when(featureService.findAll(paging)).thenReturn(pageView);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/features";
    log.info("url " + url);
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("page", String.valueOf(pageNumber));
    requestParams.add("size", String.valueOf(pageSize));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("features "))).andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}