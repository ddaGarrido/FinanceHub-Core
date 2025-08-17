package com.acme.financehub.secrets;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity @Table(name = "secret_store")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Secret {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=160) private String ref;
    @Column(name="cipher_text", nullable=false, columnDefinition="text") private String cipherText;
    @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
    @Column(name="rotated_at") private OffsetDateTime rotatedAt;

    @PrePersist void pre(){ if(createdAt==null) createdAt = OffsetDateTime.now(); }
}
