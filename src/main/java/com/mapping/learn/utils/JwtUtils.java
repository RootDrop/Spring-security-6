package com.mapping.learn.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /*private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }*/

  /*public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }*/

  public Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
      return true;

    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT Token : {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT Token is Expired : {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT :{}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT Payload is Empty: {}", e.getMessage());
    }
    return false;
  }

  public String getUsernameFromJwtToken(String authtoken){
    return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authtoken).getBody().getSubject();
  }

  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

