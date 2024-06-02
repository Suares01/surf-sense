package com.surfsense.api.infra.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.surfsense.api.infra.services.Auth0Service.UserInfo;

@Service
public class AuthorizationService {
  public String getUserId() {
    var userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userInfo.getSub();
  }

  public boolean isAuthenticated() {
    var context = SecurityContextHolder.getContext().getAuthentication();

    if (context.getName().equals("anonymousUser"))
      return false;

    return context.isAuthenticated();
  }

  public boolean isUnauthenticated() {
    return !isAuthenticated();
  }
}
