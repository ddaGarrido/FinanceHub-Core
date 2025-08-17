package com.acme.financehub.security;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findTopByUserIdAndRevokedFalseOrderByCreatedAtDesc(Long userId);
  long deleteByExpiresAtBefore(OffsetDateTime cutoff);
}
