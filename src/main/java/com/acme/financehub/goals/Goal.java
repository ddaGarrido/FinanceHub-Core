package com.acme.financehub.goals;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="goal")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class Goal {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="user_id", nullable=false) private Long userId;
  @Column(nullable=false, length=160) private String title;
  @Column(name="target_amount", nullable=false, precision=18, scale=2) private BigDecimal targetAmount;
  @Column(name="deadline_date", nullable=false) private LocalDate deadlineDate;
  @Column(name="current_progress", precision=18, scale=2) private BigDecimal currentProgress;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
