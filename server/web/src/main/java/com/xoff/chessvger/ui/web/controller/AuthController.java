package com.xoff.chessvger.ui.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    // Vérifier les identifiants utilisateur
    if ("admin".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
      String token = JwtUtil.generateToken(loginRequest.getUsername(), "USER_ROLE", 1L);
      return ResponseEntity.ok(new AuthResponse(token, 1L, "admin", "USER_ROLE"));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
  }
}