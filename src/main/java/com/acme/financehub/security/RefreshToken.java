package com.acme.financehub.security;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name = "refresh_token")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RefreshToken {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name="user_id", nullable=false)
  private Long userId;
  @Column(name="token_hash", nullable=false, length=255)
  private String tokenHash;
  @Column(name="expires_at", nullable=false)
  private OffsetDateTime expiresAt;
  @Column(name="revoked", nullable=false)
  private boolean revoked;
  @Column(name="created_at", nullable=false)
  private OffsetDateTime createdAt;
}
