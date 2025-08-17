package com.acme.financehub.budget;

import com.acme.financehub.budget.BudgetDTOs.*;
import com.acme.financehub.security.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1")
@RequiredArgsConstructor @Tag(name="Budgets & Categories")
public class BudgetController {
  private final BudgetService service;
  private final BudgetMapper mapper;

    @PostMapping("/budgets")
  public BudgetResponse create(@AuthenticationPrincipal UserPrincipal me, @Valid @RequestBody BudgetCreate req){
    return mapper.toResponse(service.create(me.id(), mapper.toEntity(req)));
  }
  @GetMapping("/budgets")
  public List<BudgetResponse> list(@AuthenticationPrincipal UserPrincipal me, @RequestParam(required=false) String period){
    var all = service.list(me.id()).stream().map(mapper::toResponse).toList();
    if (period != null) all = all.stream().filter(b -> period.equals(b.getPeriod())).toList();
    return all;
  }
  @GetMapping("/budgets/{id}")
  public BudgetResponse get(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id){
    return mapper.toResponse(service.getOwned(me.id(), id));
  }
  @PutMapping("/budgets/{id}")
  public BudgetResponse update(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id, @RequestBody BudgetCreate patch){
    return mapper.toResponse(service.update(me.id(), id, mapper.toEntity(patch)));
  }
  @DeleteMapping("/budgets/{id}")
  public void delete(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id){ service.delete(me.id(), id); }

  @PostMapping("/budgets/{id}/categories")
  public List<CategoryResponse> addCategories(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id, @Valid @RequestBody BulkCategories bulk){
    return bulk.getItems().stream().map(mapper::toEntity).map(c -> service.addCategory(me.id(), id, c)).map(mapper::toResponse).toList();
  }
  @GetMapping("/budgets/{id}/categories")
  public List<CategoryResponse> listCategories(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id){
    return service.listCategories(me.id(), id).stream().map(mapper::toResponse).toList();
  }
}
