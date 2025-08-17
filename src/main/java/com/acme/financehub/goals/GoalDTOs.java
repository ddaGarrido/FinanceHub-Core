package com.acme.financehub.goals;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

public class GoalDTOs {
  @Data public static class GoalCreate { @NotBlank String title; @NotNull BigDecimal targetAmount; @NotNull LocalDate deadlineDate; BigDecimal currentProgress; }
  @Data public static class GoalResponse { Long id; String title; BigDecimal targetAmount; LocalDate deadlineDate; BigDecimal currentProgress; }
}
