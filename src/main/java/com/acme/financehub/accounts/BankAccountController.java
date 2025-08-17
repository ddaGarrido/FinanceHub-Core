package com.acme.financehub.accounts;

import com.acme.financehub.accounts.AccountDTOs.*;
import com.acme.financehub.security.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/bank-accounts")
@RequiredArgsConstructor @Tag(name="Bank Accounts")
public class BankAccountController {
  private final BankAccountService service;
  private final TransactionService txService;
  private final AccountMapper mapper;
  private final TransactionMapper txMapper;

    @PostMapping
  public BankAccountResponse create(@AuthenticationPrincipal UserPrincipal me, @Valid @RequestBody CreateBankAccount req) {
    var saved = service.create(me.id(), mapper.toEntity(req));
    return mapper.toResponse(saved);
  }

  @GetMapping
  public List<BankAccountResponse> list(@AuthenticationPrincipal UserPrincipal me) {
    return service.listMine(me.id()).stream().map(mapper::toResponse).toList();
  }

  @GetMapping("/{id}")
  public BankAccountResponse get(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id) {
    return mapper.toResponse(service.getMine(me.id(), id));
  }

  @PutMapping("/{id}")
  public BankAccountResponse update(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id, @RequestBody CreateBankAccount patch) {
    return mapper.toResponse(service.updateMine(me.id(), id, mapper.toEntity(patch)));
  }

  @DeleteMapping("/{id}")
  public void delete(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id) {
    service.deleteMine(me.id(), id);
  }

  @GetMapping("/{id}/transactions")
  public Page<TransactionResponse> listTx(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id,
                                          @RequestParam(required=false) LocalDate from,
                                          @RequestParam(required=false) LocalDate to,
                                          Pageable pageable) {
    return txService.list(me.id(), id, from, to, pageable).map(txMapper::toResponse);
  }

  @PostMapping("/{id}/transactions")
  public List<TransactionResponse> addTx(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id,
                                         @Valid @RequestBody BulkTransactions bulk) {
    var saved = txService.addBulk(me.id(), id, bulk.getItems().stream().map(txMapper::toEntity).toList());
    return saved.stream().map(txMapper::toResponse).toList();
  }
}
