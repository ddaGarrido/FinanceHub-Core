package com.acme.financehub.credentials;

import com.acme.financehub.credentials.Credentials.OwnerType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
  List<Credentials> findByOwnerTypeAndOwnerId(OwnerType type, Long id);
}
