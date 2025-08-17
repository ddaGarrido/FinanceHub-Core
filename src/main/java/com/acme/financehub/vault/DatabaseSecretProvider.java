package com.acme.financehub.vault;

import com.acme.financehub.common.CryptoService;
import com.acme.financehub.secrets.SecretRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class DatabaseSecretProvider implements SecretProvider {
    private final SecretRepository repo;
    private final CryptoService crypto;

    public DatabaseSecretProvider(SecretRepository repo, CryptoService crypto) {
        this.repo = repo; this.crypto = crypto;
    }

    // Example method to validate credentials
    @Override
    public boolean validate(String username, String secretRef, String tokenRef) {
        boolean a = (secretRef == null) || repo.findByRef(secretRef).isPresent();
        boolean b = (tokenRef  == null) || repo.findByRef(tokenRef).isPresent();
        return a && b;
    }

    @Override
    public String resolve(String ref) {
        return repo.findByRef(ref)
                .map(r -> crypto.decrypt(r.getCipherText()))
                .orElse(null);
    }
}
