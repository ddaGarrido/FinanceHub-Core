package com.acme.financehub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final Key accessKey;
  private final Key refreshKey;
  private final long accessTtlMinutes;
  private final long refreshTtlDays;

  public JwtService(
      @Value("${security.jwt.access-secret}") String accessSecret,
      @Value("${security.jwt.refresh-secret}") String refreshSecret,
      @Value("${security.jwt.access-ttl-minutes}") long accessTtlMinutes,
      @Value("${security.jwt.refresh-ttl-days}") long refreshTtlDays) {
    this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64(accessSecret)));
    this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64(refreshSecret)));
    this.accessTtlMinutes = accessTtlMinutes;
    this.refreshTtlDays = refreshTtlDays;
  }

  private String base64(String s) {
    // allow plain secrets in dev; if not base64, encode bytes to base64
    try { Decoders.BASE64.decode(s); return s; } catch (Exception e) {
      return java.util.Base64.getEncoder().encodeToString(s.getBytes());
    }
  }

  public String generateAccessToken(Long userId, String email, Role role) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(accessTtlMinutes, ChronoUnit.MINUTES)))
        .addClaims(Map.of("email", email, "role", role.name(), "typ", "access"))
        .signWith(accessKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(Long userId) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(refreshTtlDays, ChronoUnit.DAYS)))
        .addClaims(Map.of("typ", "refresh"))
        .signWith(refreshKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims parseAccess(String token) {
    return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token).getBody();
  }
  public Claims parseRefresh(String token) {
    return Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token).getBody();
  }
}
