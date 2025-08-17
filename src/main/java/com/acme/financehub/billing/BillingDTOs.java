package com.acme.financehub.billing;

import com.acme.financehub.billing.BillingEnums.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

public class BillingDTOs {
  @Data public static class BillingSourceCreate { @NotBlank String name; @NotNull BillingSourceType type; String website; }
  @Data public static class BillingSourceResponse { Long id; String name; BillingSourceType type; String website; }

  @Data public static class RecurringBillCreate {
    @NotNull Long billingSourceId; @NotBlank String name; @NotBlank String type; @NotBlank String category;
    @NotNull AmountPolicy amountPolicy; BigDecimal fixedAmount; @NotNull BillingCycle billingCycle; @NotNull Short dueDay; String notes;
  }
  @Data public static class RecurringBillResponse {
    Long id; Long billingSourceId; String name; String type; String category; AmountPolicy amountPolicy; BigDecimal fixedAmount; BillingCycle billingCycle; Short dueDay; String notes;
  }

  @Data public static class BillChargeCreate {
    @Pattern(regexp="^\\d{4}-\\d{2}$") String referenceMonth; @NotNull BigDecimal amount; @NotNull BillChargeStatus status;
    @NotNull LocalDate dueDate; LocalDate paidDate;
  }
  @Data public static class BillChargeResponse {
    Long id; String referenceMonth; BigDecimal amount; BillChargeStatus status; LocalDate dueDate; LocalDate paidDate;
  }

  @Data public static class BulkCharges { @Size(min=1) List<BillChargeCreate> items; }
}
