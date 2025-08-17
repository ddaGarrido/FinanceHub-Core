package com.acme.financehub.accounts;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="tx_transaction")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class Transaction {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="bank_account_id", nullable=false) private Long bankAccountId;
  @Column(nullable=false, length=255) private String description;
  @Column(nullable=false, precision=18, scale=2) private BigDecimal amount;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private AccountEnums.TxType type;
  @Column(nullable=false) private LocalDate date;
  @Column(nullable=false, length=80) private String category;
  @Column(name="external_ref", length=120) private String externalRef;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
