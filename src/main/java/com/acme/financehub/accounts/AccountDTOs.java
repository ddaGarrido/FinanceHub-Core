package com.acme.financehub.accounts;

import com.acme.financehub.accounts.AccountEnums.AccountType;
import com.acme.financehub.accounts.AccountEnums.TxType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

public class AccountDTOs {
  @Data public static class CreateBankAccount { @NotBlank String name; @NotNull AccountType type; @NotBlank String institution; BigDecimal balance; }
  @Data public static class BankAccountResponse { Long id; String name; AccountType type; String institution; BigDecimal balance; }
  @Data public static class CreateTransaction { @NotBlank String description; @NotNull BigDecimal amount; @NotNull TxType type; @NotNull LocalDate date; @NotBlank String category; String externalRef; }
  @Data public static class TransactionResponse { Long id; String description; BigDecimal amount; TxType type; LocalDate date; String category; String externalRef; }
  @Data public static class BulkTransactions { @Size(min=1) List<CreateTransaction> items; }
  @Data public static class AccountSummary { Long accountId; String name; BigDecimal balance; List<TransactionResponse> lastTransactions; boolean hasUnbudgetedCategories; }
}
