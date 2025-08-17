package com.acme.financehub.accounts;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepository repo;
  private final BankAccountRepository accountRepo;

    public Page<Transaction> list(Long userId, Long accountId, LocalDate from, LocalDate to, Pageable pageable) {
    accountRepo.findByIdAndUserId(accountId, userId).orElseThrow();
    if (from != null && to != null) return repo.findByBankAccountIdAndDateBetween(accountId, from, to, pageable);
    return repo.findByBankAccountId(accountId, pageable);
  }

  @Transactional
  public Transaction add(Long userId, Long accountId, Transaction t) {
    accountRepo.findByIdAndUserId(accountId, userId).orElseThrow();
    t.setId(null); t.setBankAccountId(accountId);
    return repo.save(t);
  }

  @Transactional
  public List<Transaction> addBulk(Long userId, Long accountId, List<Transaction> items) {
    accountRepo.findByIdAndUserId(accountId, userId).orElseThrow();
    items.forEach(t -> { t.setId(null); t.setBankAccountId(accountId); });
    return repo.saveAll(items);
  }
}
