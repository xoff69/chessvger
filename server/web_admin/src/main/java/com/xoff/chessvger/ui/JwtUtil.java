package com.xoff.chessvger.ui;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
  private final String SECRET_KEY = "mySecretKey";
  // TODO À stocker en variable d'environnement en prod

  public String extractUsername(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
