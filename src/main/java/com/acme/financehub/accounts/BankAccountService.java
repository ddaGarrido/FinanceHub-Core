package com.acme.financehub.accounts;

import static com.acme.financehub.common.Exceptions.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class BankAccountService {
  private final BankAccountRepository repo;

    @Transactional
  public BankAccount create(Long userId, BankAccount acc) {
    acc.setId(null); acc.setUserId(userId);
    return repo.save(acc);
  }

  public List<BankAccount> listMine(Long userId) {
    return repo.findByUserId(userId);
  }

  public BankAccount getMine(Long userId, Long id) {
    return repo.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException("Bank account not found"));
  }

  @Transactional
  public BankAccount updateMine(Long userId, Long id, BankAccount patch) {
    BankAccount cur = getMine(userId, id);
    if (patch.getName()!=null) cur.setName(patch.getName());
    if (patch.getType()!=null) cur.setType(patch.getType());
    if (patch.getInstitution()!=null) cur.setInstitution(patch.getInstitution());
    if (patch.getBalance()!=null) cur.setBalance(patch.getBalance());
    return repo.save(cur);
  }

  @Transactional
  public void deleteMine(Long userId, Long id) {
    BankAccount cur = getMine(userId, id);
    // Guard: (ideally check transactions first) For simplicity, allow delete; controllers may expose a force flag later.
    repo.delete(cur);
  }
}
