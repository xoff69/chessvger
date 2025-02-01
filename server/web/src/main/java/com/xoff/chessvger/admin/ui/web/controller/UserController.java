package com.xoff.chessvger.admin.ui.web.controller;

import com.xoff.chessvger.ResponseList;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.admin.repository.UserEntity;
import com.xoff.chessvger.admin.ui.JwtUtil;
import com.xoff.chessvger.admin.ui.MockUser;
import com.xoff.chessvger.admin.ui.UserDTO;
import com.xoff.chessvger.admin.ui.service.UserService;
import com.xoff.chessvger.admin.ui.web.form.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Slf4j
public class UserController {
  private static final MockUser mockUser =
      new MockUser(1, "John Doe", "john.doe@example.com", "mockToken123",1);

  @Autowired
  private UserService userService;
  @Autowired
  private final JwtUtil jwtUtil;

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
      User user = userService.getUserByUsername(username);

      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      // Transformer l'objet User en DTO (pour ne pas exposer les infos sensibles)
      UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail());

      return ResponseEntity.ok(userDTO);

    } catch (JwtException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
  }
}

  // TODO sortir de la
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public static class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
      super(message);
    }
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  public static class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
      super(message);
    }
  }


