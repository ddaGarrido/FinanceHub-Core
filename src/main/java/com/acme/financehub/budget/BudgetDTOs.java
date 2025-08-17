package com.acme.financehub.budget;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

public class BudgetDTOs {
  @Data public static class BudgetCreate { @NotBlank String period; String notes; }
  @Data public static class BudgetResponse { Long id; String period; String notes; }

  @Data public static class CategoryCreate { @NotBlank String categoryName; @NotNull BigDecimal limitAmount; }
  @Data public static class CategoryResponse { Long id; String categoryName; BigDecimal limitAmount; }

  @Data public static class BulkCategories { @Size(min=1) List<CategoryCreate> items; }
}
