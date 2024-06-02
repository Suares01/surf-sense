package com.surfsense.api.infra.configuration.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.surfsense.api.infra.services.Auth0Service;
import com.surfsense.api.infra.services.Auth0Service.UserInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
  private final Auth0Service auth0Service;

  @Override
  @SuppressWarnings("null")
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (isAuthenticationValid(authentication)) {
      Jwt jwt = (Jwt) authentication.getPrincipal();
      String token = jwt.getTokenValue();
      String userId = jwt.getSubject();

      UserInfo userInfo = auth0Service.getUserInfo(token, userId);

      if (userInfo != null) {
        Authentication newAuthentication = createAuthentication(userInfo);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
      }
    }

    filterChain.doFilter(request, response);
  }

  private boolean isAuthenticationValid(Authentication authentication) {
    return authentication != null && authentication.isAuthenticated();
  }

  private Authentication createAuthentication(UserInfo userInfo) {
    return new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
  }

}
