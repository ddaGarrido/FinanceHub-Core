package com.acme.financehub.users;

import com.acme.financehub.security.Role;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name = "app_user")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false, length=120) private String name;
  @Column(nullable=false, unique=true, length=190) private String email;
  @Column(name="password_hash", nullable=false, length=255) private String passwordHash;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Role role;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;

  @PrePersist
  void pre() {
    if (createdAt == null) {
      createdAt = OffsetDateTime.now();
    }

    if (role == null) {
      role = Role.USER;
    }
  }
}
