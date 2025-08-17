package com.acme.financehub.accounts;

import com.acme.financehub.accounts.AccountDTOs.CreateTransaction;
import com.acme.financehub.accounts.AccountDTOs.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
  Transaction toEntity(CreateTransaction req);
  TransactionResponse toResponse(Transaction entity);
}
