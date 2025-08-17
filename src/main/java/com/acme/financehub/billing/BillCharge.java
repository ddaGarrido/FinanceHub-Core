package com.acme.financehub.billing;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="bill_charge")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class BillCharge {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="recurring_bill_id", nullable=false) private Long recurringBillId;
  @Column(name="reference_month", nullable=false, length=7) private String referenceMonth; // YYYY-MM
  @Column(nullable=false, precision=18, scale=2) private BigDecimal amount;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private BillingEnums.BillChargeStatus status;
  @Column(name="due_date", nullable=false) private LocalDate dueDate;
  @Column(name="paid_date") private LocalDate paidDate;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
