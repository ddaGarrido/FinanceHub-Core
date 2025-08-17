package com.acme.financehub.credentials;

import com.acme.financehub.credentials.CredentialsDTOs.*;
import com.acme.financehub.credentials.Credentials.OwnerType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor @Tag(name="Credentials")
public class CredentialsController {
  private final CredentialsService service;
  private final CredentialsMapper mapper;

    @PostMapping
  public Response create(@Valid @RequestBody Create req){
    return mapper.toResponse(service.create(mapper.toEntity(req)));
  }

  @GetMapping
  public List<Response> list(@RequestParam OwnerType ownerType, @RequestParam Long ownerId){
    return service.find(ownerType, ownerId).stream().map(mapper::toResponse).toList();
  }

  @PostMapping("/{id}/validate")
  public ValidateResponse validate(@PathVariable Long id){
    boolean ok = service.validate(id);
    ValidateResponse vr = new ValidateResponse();
    vr.setOk(ok);
    vr.setMessage(ok ? "OK" : "Failed");
    vr.setLastSuccessAt(ok ? OffsetDateTime.now() : null);
    return vr;
  }
}
