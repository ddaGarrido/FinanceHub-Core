package com.acme.financehub.users;

import static com.acme.financehub.common.Exceptions.*;

import com.acme.financehub.security.Role;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private static UserRepository repo;
  private static PasswordEncoder encoder;

    @Transactional
  public User create(String name, String email, String rawPassword) {
    repo.findByEmail(email).ifPresent(u -> { throw new BadRequestException("Email already registered"); });

    User u = new User();
    u.setName(name);
    u.setEmail(email);
    u.setPasswordHash(encoder.encode(rawPassword));
    u.setRole(Role.USER);
    u.setCreatedAt(OffsetDateTime.now());

    return repo.save(u);
  }

  public User get(Long id) { return repo.findById(id).orElseThrow(() -> new NotFoundException("User not found")); }

  public User findByEmailOrThrow(String email) {
    return repo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
  }
}
