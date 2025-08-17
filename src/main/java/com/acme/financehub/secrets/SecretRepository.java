package com.acme.financehub.secrets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretRepository extends JpaRepository<Secret, Long> {
    Optional<Secret> findByRef(String ref);
}
