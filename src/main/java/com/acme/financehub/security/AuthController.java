package com.acme.financehub.security;

import com.acme.financehub.users.User;
import com.acme.financehub.users.UserDTOs.CreateUserRequest;
import com.acme.financehub.users.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.Base64;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth")
@Tag(name="Users & Auth")
public class AuthController {
  private UserService userService;
  private JwtService jwt;
  private PasswordEncoder encoder;
  private AuthenticationManager authManager;
  private RefreshTokenRepository refreshRepo;

    @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(@Valid @RequestBody CreateUserRequest req) {
    System.out.println("Registering user: " + req.getEmail());
    System.out.println("Password before encoding: " + req.getPassword());
    System.out.println("Encoded password: " + encoder.encode(req.getPassword()));
    System.out.println("Email : " + req.getEmail());

    User u = userService.create(req.getName(), req.getEmail(), req.getPassword());
    return issue(u);
  }

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest req) {
    Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
    User u = userService.findByEmailOrThrow(req.getEmail());
    return issue(u);
  }

  @PostMapping("/refresh")
  public AuthResponse refresh(@RequestBody RefreshRequest req) {
    Claims claims = jwt.parseRefresh(req.refreshToken);
    Long userId = Long.valueOf(claims.getSubject());
    User u = userService.get(userId);

    // rotation: revoke previous and store new hash
    refreshRepo.findTopByUserIdAndRevokedFalseOrderByCreatedAtDesc(userId)
        .ifPresent(rt -> { rt.setRevoked(true); refreshRepo.save(rt); });

    return issue(u);
  }

  private AuthResponse issue(User u) {
    String access = jwt.generateAccessToken(u.getId(), u.getEmail(), u.getRole());
    String refresh = jwt.generateRefreshToken(u.getId());
    RefreshToken rt = RefreshToken.builder()
        .userId(u.getId())
        .tokenHash(hash(refresh))
        .expiresAt(OffsetDateTime.now().plusDays(7))
        .revoked(false)
        .createdAt(OffsetDateTime.now())
        .build();
    refreshRepo.save(rt);
    AuthResponse resp = new AuthResponse();
    resp.accessToken = access;
    resp.refreshToken = refresh;
    return resp;
  }

  private String hash(String token) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      return Base64.getEncoder().encodeToString(md.digest(token.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) { throw new RuntimeException(e); }
  }

  @Data public static class LoginRequest { String email; String password; }
  @Data public static class RefreshRequest { String refreshToken; }
  @Data public static class AuthResponse { public String accessToken; public String refreshToken; }
}
