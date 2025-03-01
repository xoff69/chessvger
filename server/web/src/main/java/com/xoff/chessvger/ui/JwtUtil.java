package com.xoff.chessvger.ui;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtUtil {
  private  final Key SECRET_KEY ;
  private final long EXPIRATION_TIME = 1000 * 60 * 60*24*7;  /// TODO pour dev

  // TODO À stocker en variable d'environnement en prod

  public JwtUtil() {
    String secret = "sG2h23SeoaDDYHtuU1+iJQVvaiLvTOTqR4sr4zMyVrg="; // ⚠️ À définir en prod // cf main below
    if (secret == null || secret.isEmpty()) {
      throw new IllegalStateException("La clé secrète JWT n'est pas définie !");
    }
    byte[] decodedKey = Base64.getDecoder().decode(secret);
    this.SECRET_KEY = Keys.hmacShaKeyFor(decodedKey);
  }


  public String extractUsername(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact();
  }
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
  public static void main(String[] args) {
    String key = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
    System.out.println("Generated SECRET_KEY: " + key);
  }
}
