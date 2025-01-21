package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ResponseList;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.DbKeyManager;
import com.xoff.chessvger.repository.UserEntity;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.UserDTO;
import com.xoff.chessvger.ui.service.UserService;
import com.xoff.chessvger.ui.web.form.LoginForm;
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
  private UserService userService;

  @PostMapping(path = "/apiadmin/users/login")
  public String login(LoginForm form) {
    UserEntity userEntity = userService.findByLoginAndPassword(form.getLogin(), form.getPassword());
    if (userEntity == null) {
      log.info("not found " + form);
      return "error";
    } else {
          System.out.println("ok "+userEntity);
         // tenantName allows the application to know which database we work with
      return userEntity.getTenant().getName();
    }
  }

  @GetMapping("/apiadmin/users/all")
  public ResponseEntity<ResponseList<UserDTO>> all(){


    return new ResponseEntity<>(new ResponseList(userService.findAll(),userService.count()),
        HttpStatus.OK);
  }




}
