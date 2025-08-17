package com.acme.financehub.billing;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="recurring_bill")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class RecurringBill {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="billing_source_id", nullable=false) private Long billingSourceId;
  @Column(name="payer_user_id", nullable=false) private Long payerUserId;
  @Column(nullable=false, length=160) private String name;
  @Column(nullable=false, length=40) private String type;
  @Column(nullable=false, length=80) private String category;
  @Enumerated(EnumType.STRING) @Column(name="amount_policy", nullable=false, length=20) private BillingEnums.AmountPolicy amountPolicy;
  @Column(name="fixed_amount", precision=18, scale=2) private BigDecimal fixedAmount;
  @Enumerated(EnumType.STRING) @Column(name="billing_cycle", nullable=false, length=40) private BillingEnums.BillingCycle billingCycle;
  @Column(name="due_day", nullable=false) private Short dueDay;
  @Column(length=500) private String notes;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
