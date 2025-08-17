package com.acme.financehub.billing;

import com.acme.financehub.billing.BillingDTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillingMapper {
  BillingSource toEntity(BillingSourceCreate req);
  BillingSourceResponse toResponse(BillingSource entity);

  RecurringBill toEntity(RecurringBillCreate req);
  RecurringBillResponse toResponse(RecurringBill entity);

  BillCharge toEntity(BillChargeCreate req);
  BillChargeResponse toResponse(BillCharge entity);
}
