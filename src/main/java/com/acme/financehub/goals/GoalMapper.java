package com.acme.financehub.goals;

import com.acme.financehub.goals.GoalDTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoalMapper {
  Goal toEntity(GoalCreate req);
  GoalResponse toResponse(Goal entity);
}
