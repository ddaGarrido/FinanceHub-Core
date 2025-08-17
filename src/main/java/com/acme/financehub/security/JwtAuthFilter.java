package com.acme.financehub.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends org.springframework.web.filter.OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

//  public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//    this.jwtService = jwtService;
//    this.userDetailsService = userDetailsService;
//  }
    @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String auth = request.getHeader("Authorization");
    if (StringUtils.hasText(auth) && auth.startsWith("Bearer ")) {
      String token = auth.substring(7);
      try {
        Claims claims = jwtService.parseAccess(token);
        String email = (String) claims.get("email");
        var userDetails = userDetailsService.loadUserByUsername(email);
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } catch (Exception ignored) { }
    }
    chain.doFilter(request, response);
  }
}
