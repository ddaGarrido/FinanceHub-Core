package com.acme.financehub.credentials;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="credentials")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class Credentials {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Enumerated(EnumType.STRING) @Column(name="owner_type", nullable=false, length=40) private OwnerType ownerType;
  @Column(name="owner_id", nullable=false) private Long ownerId;
  @Column(nullable=false, length=160) private String username;
  @Column(name="secret_ref", length=160) private String secretRef;
  @Column(name="token_ref", length=160) private String tokenRef;
  @Column(name="provider_notes", length=400) private String providerNotes;
  @Column(name="rotated_at") private OffsetDateTime rotatedAt;
  @Column(name="last_success_at") private OffsetDateTime lastSuccessAt;
  @Column(name="error_count", nullable=false) private int errorCount;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;

  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
  public enum OwnerType { BankAccount, BillingSource, RecurringBill }
}
