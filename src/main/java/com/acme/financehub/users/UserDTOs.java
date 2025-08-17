package com.acme.financehub.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserDTOs {
  public UserDTOs() {}

  @Data public static class MeResponse {
    Long id;
    String name;
    String email;
    String role;
  }
  @Data public static class CreateUserRequest {
    @NotBlank String name;
    @Email @NotBlank String email;
    @NotBlank String password;
  }
}
