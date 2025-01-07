package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.service.UserService;
import com.xoff.chessvger.ui.web.form.LoginForm;
import com.xoff.chessvger.ui.web.mapper.UserMapper;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.common.UserTenant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {
  @Autowired
  Navigation navigation;
  @Autowired
  private UserService userService;
  @Autowired
  private UserMapper userMapper;


  @PostMapping(path = "/login")
  public String login(LoginForm form) {
    UserTenant userDto = userService.findByLoginAndPassword(form.getLogin(), form.getPassword());
    if (userDto == null) {
      log.info("not found " + form);
      return "error";
    } else {
      navigation.setUserDto(userDto);
      navigation.initDbs();
      if (userDto.getIsAdmin()) {

        navigation.appendTabAdmin();
      } else {
        navigation.appendTabSimpleUser();
      }


      log.info("connexion ok " + form.getLogin());
      return "redirect:/";
    }
  }

  @GetMapping("/disconnect")
  public String disconnect() {
    log.info("disconnect");
    navigation.setUserDto(null);
    navigation.removeTabAdmin();
    return "redirect:/";
  }

  @GetMapping("/users")
  public ResponseEntity<PageView> getAll(@RequestParam(defaultValue = "0", name = "page") int page,
                                         @RequestParam(defaultValue = "10", name = "size")
                                         int size) {

    Pageable paging = PageRequest.of(page, size);
    return new ResponseEntity<>(userService.findAll(paging), HttpStatus.OK);

  }

  @PostMapping("/users/")
  public ResponseEntity<UserTenant> create(@RequestBody UserTenant dto) {

    // does not exist
    dto.setId(Long.valueOf(DbKeyManager.getInstance().getDbKeyGenerator().getNext()));
    UserTenant dtoSaved = userMapper.entity2Dto(userService.create(dto));
    log.info("dto saved " + dtoSaved);
    return new ResponseEntity<UserTenant>(dtoSaved, HttpStatus.CREATED);

  }

  @PutMapping(value = "/users/{id}")
  public ResponseEntity<UserTenant> update(@RequestBody UserTenant dto,
                                           @PathVariable(name = "id") Long id) {
    UserTenant userDtoUpdated = userService.update(id, dto);
    if (userDtoUpdated != null) {
      return new ResponseEntity<>(userDtoUpdated, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new UserTenant(), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/users/{id}")
  public ResponseEntity<UserTenant> findById(@PathVariable(value = "id", name = "id") Long id) {

    UserTenant dtoFound = userService.findById(id);
    if (dtoFound != null) {
      return new ResponseEntity<UserTenant>(dtoFound, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(value = "/users/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {

    if (userService.delete(id)) {
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/userall")
  public ResponseEntity<List<User>> userAll() {

    return ResponseEntity.ok(userService.list());

  }
}
