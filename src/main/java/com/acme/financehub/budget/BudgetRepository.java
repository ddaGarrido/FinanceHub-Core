package com.acme.financehub.budget;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
  List<Budget> findByUserId(Long userId);
}
interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
  List<BudgetCategory> findByBudgetId(Long budgetId);
}
