package com.acme.financehub.vault;

public interface SecretProvider {
  boolean validate(String username, String secretRef, String tokenRef);
  String resolve(String ref);
}
