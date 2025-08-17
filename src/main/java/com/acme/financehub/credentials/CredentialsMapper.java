package com.acme.financehub.credentials;

import com.acme.financehub.credentials.CredentialsDTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CredentialsMapper {
  Credentials toEntity(Create req);

  @Mapping(target="secretRef", expression="java(mask(entity.getSecretRef()))")
  @Mapping(target="tokenRef", expression="java(mask(entity.getTokenRef()))")
  Response toResponse(Credentials entity);

  default String mask(String ref){
    if (ref == null) return null;
    int n = Math.min(4, ref.length());
    return "*".repeat(Math.max(0, ref.length()-n)) + ref.substring(ref.length()-n);
  }
}
