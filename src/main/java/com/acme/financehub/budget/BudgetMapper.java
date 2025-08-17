package com.acme.financehub.budget;

import com.acme.financehub.budget.BudgetDTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {
  Budget toEntity(BudgetCreate req);
  BudgetResponse toResponse(Budget entity);

  BudgetCategory toEntity(CategoryCreate req);
  CategoryResponse toResponse(BudgetCategory entity);
}
