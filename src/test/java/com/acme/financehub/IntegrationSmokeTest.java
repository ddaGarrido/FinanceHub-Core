package com.acme.financehub;

import static org.assertj.core.api.Assertions.assertThat;

import com.acme.financehub.security.AuthController.AuthResponse;
import com.acme.financehub.users.UserDTOs.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSmokeTest {

  @LocalServerPort int port;
  @Autowired TestRestTemplate rest;

  @Test
  void register_and_login_flow() {
    var req = new CreateUserRequest();
    req.setName("Tester");
    req.setEmail("tester@example.com");
    req.setPassword("secret123");
    ResponseEntity<AuthResponse> resp = rest.postForEntity(url("/auth/register"), req, AuthResponse.class);
    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(resp.getBody().getAccessToken()).isNotBlank();
  }

  String url(String p){ return "http://localhost:"+port+p; }
}
