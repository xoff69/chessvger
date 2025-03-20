package com.xoff.chessvger.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

  private final Key secretKey;
  private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30; // 30 jours

  public JwtUtil(@Value("${jwt.secret:}") String secret) {
    if (secret == null || secret.isEmpty()) {
      System.out.println("Clé JWT non définie. Génération d'une nouvelle clé...");
      this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
      System.out.println("Nouvelle clé (Base64) : " + getEncodedSecretKey());
    } else {
      byte[] decodedKey = Base64.getDecoder().decode(secret);
      this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }
    log.info("GENERATION DU TOKEN POUR DEMO "+
    generateToken("demo"));
  }

  public String generateToken(String username) {
    return Jwts.builder()
            .setClaims(Map.of())
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(secretKey)
            .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(secretKey)
              .build()
              .parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public String getEncodedSecretKey() {
    return Base64.getEncoder().encodeToString(secretKey.getEncoded());
  }

  // Méthode main pour tester
  public static void main(String[] args) {
    JwtUtil jwtUtil = new JwtUtil("Y8Rvca/uGFExf3/Bh+Y4h/Ijwwo31UiOwM3LAz4B5C8=");
    String token = jwtUtil.generateToken("john");
    System.out.println("Token généré : " + token);
    System.out.println("Validation du token : " + jwtUtil.validateToken(token));
    System.out.println("extraction user : " + jwtUtil.extractUsername(token));
    System.out.println("Clé secrète à stocker (Base64) : " + jwtUtil.getEncodedSecretKey());
  }
}
