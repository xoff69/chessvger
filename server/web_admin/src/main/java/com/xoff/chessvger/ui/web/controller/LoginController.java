package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ui.LoginRequest;
import com.xoff.chessvger.ui.MockUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
  private static final MockUser mockUser =
      new MockUser(1, "John Doe", "john.doe@example.com", "mockToken123",1);

  @PostMapping("/apiadmin/login")
  public MockUser login(@RequestBody LoginRequest loginRequest) {
    if ("max@doe".equals(loginRequest.getEmail()) && "pwd".equals(loginRequest.getPassword())) {
      return mockUser;
    } else {
      throw new UnauthorizedException("Invalid credentials");
    }
  }

  // Endpoint pour récupérer l'utilisateur (GET /user)
  @GetMapping("/apiadmin/user")
  public MockUser getUser(@RequestHeader("Authorization") String token) {
    if ("mockToken123".equals(token)) {
      return mockUser;
    } else {
      throw new ForbiddenException("Invalid token");
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
}
