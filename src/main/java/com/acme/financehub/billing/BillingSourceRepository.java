package com.acme.financehub.billing;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingSourceRepository extends JpaRepository<BillingSource, Long> {}
interface RecurringBillRepository extends JpaRepository<RecurringBill, Long> {
  List<RecurringBill> findByPayerUserId(Long userId);
}
interface BillChargeRepository extends JpaRepository<BillCharge, Long> {
  List<BillCharge> findByRecurringBillId(Long recurringBillId);
}
