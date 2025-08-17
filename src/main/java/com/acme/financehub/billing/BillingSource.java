package com.acme.financehub.billing;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="billing_source")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BillingSource {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, length=160) private String name;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=40) private BillingEnums.BillingSourceType type;
  @Column(length=200) private String website;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
