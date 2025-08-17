package com.acme.financehub.budget;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity @Table(name="budget")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class Budget {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name="user_id", nullable=false) private Long userId;
  @Column(nullable=false, length=20) private String period;
  @Column(length=400) private String notes;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  @PrePersist void pre(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
}
