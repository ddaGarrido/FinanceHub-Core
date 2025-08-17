package com.acme.financehub.users;

import com.acme.financehub.security.UserPrincipal;
import com.acme.financehub.users.UserDTOs.MeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/me")
@RequiredArgsConstructor @Tag(name="Users & Auth")
public class UserController {
  private final UserMapper mapper;

    @GetMapping
  public MeResponse me(@AuthenticationPrincipal UserPrincipal principal) {
    return mapper.toMe(principal.user());
  }
}
