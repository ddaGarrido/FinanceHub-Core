package com.acme.financehub.credentials;

import static com.acme.financehub.common.Exceptions.*;

import com.acme.financehub.vault.SecretProvider;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class CredentialsService {
  private final CredentialsRepository repo;
  private final SecretProvider secrets;

    @Transactional public Credentials create(Credentials c){
    c.setId(null);
    c.setErrorCount(0);
    c.setRotatedAt(OffsetDateTime.now());
    return repo.save(c);
  }

  public List<Credentials> find(Credentials.OwnerType type, Long id){ return repo.findByOwnerTypeAndOwnerId(type, id); }

  @Transactional
  public boolean validate(Long id) {
    Credentials c = repo.findById(id).orElseThrow(() -> new NotFoundException("Credentials not found"));
    boolean ok = secrets.validate(c.getUsername(), c.getSecretRef(), c.getTokenRef());
    if (ok) { c.setLastSuccessAt(OffsetDateTime.now()); c.setErrorCount(0); }
    else { c.setErrorCount(c.getErrorCount()+1); }
    repo.save(c);
    return ok;
  }
}
