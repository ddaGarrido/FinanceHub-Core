package com.acme.financehub.credentials;

import com.acme.financehub.credentials.Credentials.OwnerType;
import com.acme.financehub.vault.SecretProvider;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class CredentialsHealthJob {
  private final CredentialsRepository repo;
  private final SecretProvider secrets;

  @Value("${financehub.credentials.health.enabled:false}") boolean enabled;

  @Scheduled(cron = "${financehub.credentials.health.cron:0 0 3 * * *}")
  public void run(){
    if (!enabled) return;
    for (var c : repo.findAll()) {
      boolean ok = secrets.validate(c.getUsername(), c.getSecretRef(), c.getTokenRef());
      if (ok) { c.setLastSuccessAt(OffsetDateTime.now()); c.setErrorCount(0); }
      else { c.setErrorCount(c.getErrorCount()+1); }
      repo.save(c);
    }
  }
}
