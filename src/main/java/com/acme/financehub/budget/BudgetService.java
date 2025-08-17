package com.acme.financehub.budget;

import static com.acme.financehub.common.Exceptions.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class BudgetService {
  private final BudgetRepository budgetRepo;
  private final BudgetCategoryRepository catRepo;

    @Transactional public Budget create(Long userId, Budget b){ b.setId(null); b.setUserId(userId); return budgetRepo.save(b); }
  public List<Budget> list(Long userId) { return budgetRepo.findByUserId(userId); }
  public Budget getOwned(Long userId, Long id){
    Budget b = budgetRepo.findById(id).orElseThrow(() -> new NotFoundException("Budget not found"));
    if (!b.getUserId().equals(userId)) throw new ForbiddenException("Not your budget");
    return b;
  }
  @Transactional public Budget update(Long userId, Long id, Budget patch){
    Budget b = getOwned(userId, id);
    if (patch.getNotes()!=null) b.setNotes(patch.getNotes());
    if (patch.getPeriod()!=null) b.setPeriod(patch.getPeriod());
    return budgetRepo.save(b);
  }
  @Transactional public void delete(Long userId, Long id){
    Budget b = getOwned(userId,id); budgetRepo.delete(b);
  }

  @Transactional public BudgetCategory addCategory(Long userId, Long budgetId, BudgetCategory c){
    getOwned(userId, budgetId);
    c.setId(null);
    c.setBudgetId(budgetId);
    return catRepo.save(c);
  }
  public List<BudgetCategory> listCategories(Long userId, Long budgetId){
    getOwned(userId,budgetId); return catRepo.findByBudgetId(budgetId);
  }
}
