package com.acme.financehub.accounts;

import com.acme.financehub.accounts.AccountDTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
  BankAccount toEntity(CreateBankAccount req);
  @Mapping(target="id", source="id")
  BankAccountResponse toResponse(BankAccount entity);

  Transaction toEntity(CreateTransaction req);
  TransactionResponse toResponse(Transaction entity);
}
