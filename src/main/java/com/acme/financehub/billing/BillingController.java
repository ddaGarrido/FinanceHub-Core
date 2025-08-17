package com.acme.financehub.billing;

import com.acme.financehub.billing.BillingDTOs.*;
import com.acme.financehub.security.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1")
@RequiredArgsConstructor @Tag(name="Billing Sources & Recurring Bills")
public class BillingController {
  private final BillingService service;
  private final BillingMapper mapper;

    // Billing Sources
  @PostMapping("/billing-sources")
  public BillingSourceResponse createSource(@Valid @RequestBody BillingSourceCreate req){
    return mapper.toResponse(service.createSource(mapper.toEntity(req)));
  }
  @GetMapping("/billing-sources")
  public List<BillingSourceResponse> listSources(){
    return service.listSources().stream().map(mapper::toResponse).toList();
  }
  @GetMapping("/billing-sources/{id}")
  public BillingSourceResponse getSource(@PathVariable Long id){
    return mapper.toResponse(service.getSource(id));
  }

  // Recurring Bills
  @PostMapping("/recurring-bills")
  public RecurringBillResponse createBill(@AuthenticationPrincipal UserPrincipal me, @Valid @RequestBody RecurringBillCreate req){
    var saved = service.createBill(me.id(), mapper.toEntity(req));
    return mapper.toResponse(saved);
  }
  @GetMapping("/recurring-bills")
  public List<RecurringBillResponse> listBills(@AuthenticationPrincipal UserPrincipal me){
    return service.listBills(me.id()).stream().map(mapper::toResponse).toList();
  }
  @GetMapping("/recurring-bills/{id}")
  public RecurringBillResponse getBill(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id){
    return mapper.toResponse(service.getBillOwned(me.id(), id));
  }

  // Charges
  @PostMapping("/recurring-bills/{id}/charges")
  public List<BillChargeResponse> addCharges(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id,
                                             @Valid @RequestBody BulkCharges bulk){
    return bulk.getItems().stream().map(mapper::toEntity).map(c -> service.addCharge(me.id(), id, c)).map(mapper::toResponse).toList();
  }
  @GetMapping("/recurring-bills/{id}/charges")
  public List<BillChargeResponse> listCharges(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id,
                                              @RequestParam(required=false) String month){
    return service.listCharges(me.id(), id, month).stream().map(mapper::toResponse).toList();
  }
}
