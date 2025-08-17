package com.acme.financehub.vault;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class LocalInMemorySecretProvider implements SecretProvider {
  private final Map<String,String> store = new ConcurrentHashMap<>();

  public LocalInMemorySecretProvider(){
    store.put("secret/dev/alice", "password123");
    store.put("token/dev/alice", "token-abc");
  }

  @Override public boolean validate(String username, String secretRef, String tokenRef) {
    // trivial validation: check
    return (secretRef == null || store.containsKey(secretRef)) &&
            (tokenRef == null || store.containsKey(tokenRef));
  }

  @Override public String resolve(String ref) {
    return store.get(ref);
  }
}
