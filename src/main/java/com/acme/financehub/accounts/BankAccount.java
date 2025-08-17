package com.acme.financehub.accounts;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="bank_account")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class BankAccount {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="user_id", nullable=false) private Long userId;
  @Column(nullable=false, length=120) private String name;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=30) private AccountEnums.AccountType type;
  @Column(nullable=false, precision=18, scale=2) private BigDecimal balance;
  @Column(nullable=false, length=120) private String institution;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); if(balance==null) balance=BigDecimal.ZERO; }
}
