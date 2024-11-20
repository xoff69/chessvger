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
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.UserService;
import com.xoff.chessvger.ui.web.mapper.UserMapper;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.view.UserDto;
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
class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService userService;
  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private UserMapper mapper;

  @Test
  @DisplayName("user getDatabaseManager by id")
  public void experct_result_byid() throws Exception {

    User item = new User();
    item.setId(1L);
    item.setName("findUsr");
    item.setIsAdmin(true);
    UserDto rightDto = mapper.entity2Dto(item);


    when(userService.findById(1L)).thenReturn(rightDto);


    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/users/" + 1;

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("findUsr"))).andReturn();
  }

  @Test
  @DisplayName("user post create")
  public void expect_result_rightCreate() throws Exception {
    User form = new User();
    form.setName("test UserDto");

    when(userService.create(Mockito.any())).thenReturn(form);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/users/";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
            post(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
                .content(UtilTest.asJsonString(form))).andExpect(status().isCreated())
        .andExpect(content().string(containsString("test UserDto"))).andReturn();

  }

  @Test
  @DisplayName("right put update")
  public void expect_result_rightPut() throws Exception {
    UserDto form = new UserDto();
    form.setName("test UserDto");
    when(userService.update(Mockito.any(), Mockito.any())).thenReturn(form);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/users/5";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
            put(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
                .content(UtilTest.asJsonString(form))).andExpect(status().isOk())
        .andExpect(content().string(containsString("test UserDto"))).andReturn();

  }

  @Test
  @DisplayName("UserDto post delete")
  public void expect_result_rightdelete() throws Exception {
    UserDto form = new UserDto();
    form.setName("test UserDto");
    when(userService.delete(Mockito.any())).thenReturn(true);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/users/5";

    log.info("url " + url);

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    MvcResult mvcResult = this.mockMvc.perform(
        delete(builder.build().toUri()).contentType(MediaType.APPLICATION_JSON)
            .content(UtilTest.asJsonString(form))).andExpect(status().isNoContent()).andReturn();

  }

  @Test
  @DisplayName("users")
  public void expect_result_forallrights() throws Exception {
    int pageNumber = 1;
    int pageSize = 100;
    Pageable paging = PageRequest.of(pageNumber, pageSize);
    PageView<UserDto> pageView = new PageView<UserDto>();
    List<UserDto> users = new ArrayList<>();
    for (long i = 0; i < 3; i++) {
      User r = new User();
      r.setId(i);
      r.setName("user " + i);
      r.setIsAdmin(true);
      users.add(mapper.entity2Dto(r));
    }

    userService.findAll(paging);

    pageView.setItems(users);
    when(userService.findAll(paging)).thenReturn(pageView);
    String url = ConstantsTest.URL_SERVER + serverProperties.getPort() + "/users";
    log.info("url " + url);
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("page", String.valueOf(pageNumber));
    requestParams.add("size", String.valueOf(pageSize));
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParams(requestParams);
    MvcResult mvcResult =
        this.mockMvc.perform(get(builder.build().toUri())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("user "))).andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}