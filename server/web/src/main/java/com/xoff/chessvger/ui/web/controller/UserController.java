package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ResponseList;
import com.xoff.chessvger.repository.UserEntity;
import com.xoff.chessvger.service.UserService;
import com.xoff.chessvger.ui.JwtUtil;
import com.xoff.chessvger.ui.MockUser;
import com.xoff.chessvger.ui.UserDTO;
import com.xoff.chessvger.ui.form.LoginForm;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@Slf4j
public class UserController {
  private static final MockUser mockUser =
      new MockUser(1, "John Doe", "john.doe@example.com", "mockToken123",1);

  @Autowired
  private UserService userService;
  @Autowired
  private  JwtUtil jwtUtil;

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
  @GetMapping("/apiadmin/users/user")
  public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String token) {
    try {
      // Vérifier et extraire l'ID utilisateur du token
      String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));

      // Récupérer l'utilisateur en base
      UserDTO user = userService.getUserByUsername(username);

      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }



      return ResponseEntity.ok(user);

    } catch (JwtException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
  }
}




