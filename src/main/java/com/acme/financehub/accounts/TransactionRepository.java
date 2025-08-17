package com.acme.financehub.accounts;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Page<Transaction> findByBankAccountIdAndDateBetween(Long bankAccountId, LocalDate from, LocalDate to, Pageable pageable);
  Page<Transaction> findByBankAccountId(Long bankAccountId, Pageable pageable);
  List<Transaction> findTop10ByBankAccountIdOrderByDateDesc(Long bankAccountId);
}
