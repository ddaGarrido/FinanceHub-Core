package com.acme.financehub.users;

import com.acme.financehub.users.UserDTOs.MeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  @Mapping(target = "role", expression = "java(user.getRole().name())", ignore = true)
  MeResponse toMe(User user);
}
