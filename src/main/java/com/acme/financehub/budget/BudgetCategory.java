package com.acme.financehub.budget;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="budget_category")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class BudgetCategory {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="budget_id", nullable=false) private Long budgetId;
  @Column(name="category_name", nullable=false, length=80) private String categoryName;
  @Column(name="limit_amount", nullable=false, precision=18, scale=2) private BigDecimal limitAmount;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
