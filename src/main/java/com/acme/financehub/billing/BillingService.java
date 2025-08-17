package com.acme.financehub.billing;

import static com.acme.financehub.common.Exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class BillingService {
  private final BillingSourceRepository srcRepo;
  private final RecurringBillRepository billRepo;
  private final BillChargeRepository chargeRepo;

  public List<BillingSource> listSources() { return srcRepo.findAll(); }
  @Transactional public BillingSource createSource(BillingSource s) { s.setId(null); return srcRepo.save(s); }
  public BillingSource getSource(Long id){ return srcRepo.findById(id).orElseThrow(() -> new NotFoundException("Billing source not found")); }

  @Transactional public RecurringBill createBill(Long userId, RecurringBill b) {
    getSource(b.getBillingSourceId());
    b.setId(null); b.setPayerUserId(userId); return billRepo.save(b);
  }
  public List<RecurringBill> listBills(Long userId){ return billRepo.findByPayerUserId(userId); }
  public RecurringBill getBillOwned(Long userId, Long id){
    RecurringBill rb = billRepo.findById(id).orElseThrow(() -> new NotFoundException("Recurring bill not found"));
    if(!rb.getPayerUserId().equals(userId)) throw new ForbiddenException("Not your bill");
    return rb;
  }

  @Transactional public BillCharge addCharge(Long userId, Long billId, BillCharge c) {
    RecurringBill rb = getBillOwned(userId, billId);
    c.setId(null); c.setRecurringBillId(rb.getId());
    return chargeRepo.save(c);
  }
  public List<BillCharge> listCharges(Long userId, Long billId, String month) {
    getBillOwned(userId, billId);
    var list = chargeRepo.findByRecurringBillId(billId);
    if (month != null) {
      DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
      // simple filter
      list = list.stream().filter(c -> month.equals(c.getReferenceMonth())).toList();
    }
    return list;
  }
}
