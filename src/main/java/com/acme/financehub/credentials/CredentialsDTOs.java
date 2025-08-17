package com.acme.financehub.credentials;

import com.acme.financehub.credentials.Credentials.OwnerType;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.*;

public class CredentialsDTOs {
  @Data public static class Create { @NotNull OwnerType ownerType; @NotNull Long ownerId; @NotBlank String username; String secretRef; String tokenRef; String providerNotes; }
  @Data public static class Response { Long id; OwnerType ownerType; Long ownerId; String username; String secretRef; String tokenRef; String providerNotes; OffsetDateTime rotatedAt; OffsetDateTime lastSuccessAt; int errorCount; }
  @Data public static class ValidateResponse { boolean ok; String message; OffsetDateTime lastSuccessAt; int errorCount; }
}
