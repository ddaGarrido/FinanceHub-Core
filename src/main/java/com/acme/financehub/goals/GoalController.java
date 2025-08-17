package com.acme.financehub.goals;

import com.acme.financehub.goals.GoalDTOs.*;
import com.acme.financehub.security.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/goals")
@RequiredArgsConstructor @Tag(name="Goals")
public class GoalController {
  private final GoalService service;
  private final GoalMapper mapper;

    @PostMapping public GoalResponse create(@AuthenticationPrincipal UserPrincipal me, @Valid @RequestBody GoalCreate req){
    return mapper.toResponse(service.create(me.id(), mapper.toEntity(req)));
  }
  @GetMapping public List<GoalResponse> list(@AuthenticationPrincipal UserPrincipal me){ return service.list(me.id()).stream().map(mapper::toResponse).toList(); }
  @GetMapping("/{id}") public GoalResponse get(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id){ return mapper.toResponse(service.getOwned(me.id(), id)); }
  @PutMapping("/{id}") public GoalResponse update(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id, @RequestBody GoalCreate patch){
    return mapper.toResponse(service.update(me.id(), id, mapper.toEntity(patch))); }
  @DeleteMapping("/{id}") public void delete(@AuthenticationPrincipal UserPrincipal me, @PathVariable Long id){ service.delete(me.id(), id); }
}
