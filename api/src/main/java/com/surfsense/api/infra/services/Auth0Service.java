package com.surfsense.api.infra.services;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.services.UserService;
import com.surfsense.api.infra.configuration.http.Auth0ClientConfig.Auth0Client;
import com.surfsense.api.infra.configuration.http.Auth0ClientConfig.ChangePasswordRequest;
import com.surfsense.api.infra.configuration.http.Auth0ClientConfig.ManagementAPIToken;
import com.surfsense.api.infra.configuration.http.Auth0ClientConfig.SendEmailVerificationRequest;
import com.surfsense.api.infra.configuration.http.Auth0ClientConfig.UpdateEmailRequest;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;

@Service
public class Auth0Service implements UserService {
  @Value("${api-env.auth0.domain}")
  private String domain;

  @Value("${api-env.auth0.client_id}")
  private String clientId;

  @Value("${api-env.auth0.client_secret}")
  private String clientSecret;

  @Value("${api-env.auth0.audience}")
  private String audience;

  @Resource(name = "redisTemplate")
  private ValueOperations<String, String> cacheToken;

  @Resource(name = "redisTemplate")
  private RedisTemplate<String, UserInfo> cacheUserInfo;

  @Autowired
  private Auth0Client auth0Client;

  private final String MANAGEMENT_API_TOKEN_CACHE_KEY = "management_api_token";

  private static final Logger logger = LoggerFactory.getLogger(Auth0Service.class);

  public void sendEmailVerification(String userId) {
    String managementAPIToken = getManagementAPIToken();
    sendEmailVerification(userId, managementAPIToken);
  }

  private void sendEmailVerification(String userId, String managementAPIToken) {
    var body = new SendEmailVerificationRequest();
    body.setUser_id(userId);
    body.setClient_id(clientId);

    try {
      auth0Client.sendEmailVerification("Bearer " + managementAPIToken, body);
      resetCachedUserInfo(userId);
    } catch (Exception e) {
      logger.error("Error on sending email verification.", e);
      throw new BadRequestException("Ocorreu um erro inesperado.");
    }
  }

  public void updateEmail(String userId, String email) {
    String managementApiAccessToken = getManagementAPIToken();

    var body = new UpdateEmailRequest();
    body.setEmail(email);
    body.setEmail_verified(false);
    body.setConnection("Username-Password-Authentication");

    try {
      auth0Client.updateUserEmail("Bearer " + managementApiAccessToken, userId, body);
      resetCachedUserInfo(userId);
    } catch (Exception e) {
      logger.error("Error updating user email.", e);
      throw new BadRequestException("Ocorreu um erro inesperado.");
    }

    sendEmailVerification(userId, managementApiAccessToken);
  }

  public void resetPassword(String email) {
    var body = new ChangePasswordRequest();
    body.setClient_id(clientId);
    body.setEmail(email);
    body.setConnection("Username-Password-Authentication");

    try {
      auth0Client.resetPassword(body);
    } catch (Exception e) {
      logger.error("Error when resetting user password.", e);
      throw new BadRequestException("Ocorreu um erro inesperado.");
    }
  }

  public UserInfo getUserInfo(String userAccessToken, String userId) {
    UserInfo cachedUserInfo = getUserInfoFromCache(userId);

    if (cachedUserInfo == null) {
      UserInfo userInfoFromAuth0 = getUserInfoFromAuht0(userAccessToken);
      cacheUserInfo.opsForValue().set(userInfoFromAuth0.getSub(), userInfoFromAuth0, Duration.ofHours(1));
      return userInfoFromAuth0;
    }

    return cachedUserInfo;
  }

  private UserInfo getUserInfoFromCache(String userId) {
    return cacheUserInfo.opsForValue().get(userId);
  }

  private UserInfo getUserInfoFromAuht0(String userAccessToken) {
    try {
      UserInfo userInfo = auth0Client.getUserInfo("Bearer " + userAccessToken);

      return userInfo;
    } catch (Exception e) {
      logger.error("Error fetching user info.", e);
      throw new BadRequestException("Ocorreu um erro inesperado.");
    }
  }

  public UserProfile updateUserRootAttributes(String userId, RootAttributes attributes) {
    String managementApiAccessToken = "Bearer " + getManagementAPIToken();

    try {
      UserProfile profile = auth0Client.updateUserProfile(managementApiAccessToken, userId, attributes);
      resetCachedUserInfo(userId);

      return profile;
    } catch (Exception e) {
      logger.error("Error updating user root attributes.", e);
      throw new BadRequestException("Ocorreu um erro inesperado.");
    }
  }

  private void resetCachedUserInfo(String userId) {
    cacheUserInfo.delete(userId);
  }

  private String getManagementAPIToken() {
    String tokenFromCache = getManagementAPITokenFromCache();

    if (tokenFromCache == null) {
      String tokenFromApi = getManagementAPITokenFromAuth0();
      cacheToken.set(MANAGEMENT_API_TOKEN_CACHE_KEY, tokenFromApi, Duration.ofHours(20));
      return tokenFromApi;
    }

    return tokenFromCache;
  }

  private String getManagementAPITokenFromCache() {
    return cacheToken.get(MANAGEMENT_API_TOKEN_CACHE_KEY);
  }

  private String getManagementAPITokenFromAuth0() {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("grant_type", "client_credentials");
    formData.add("client_id", clientId);
    formData.add("client_secret", clientSecret);
    formData.add("audience", audience);

    try {
      ManagementAPIToken response = auth0Client.getManagementAPIToken(formData);
      return response != null ? response.getAccess_token() : null;
    } catch (Exception e) {
      logger.error("Error fetching Management API token.", e);
      throw new BadRequestException("Ocorreu um erro inesperado.");
    }
  }

  @Getter
  @Setter
  public static class UserInfo implements Serializable {
    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String middle_name;
    private String nickname;
    private String preferred_username;
    private String profile;
    private String picture;
    private String website;
    private String email;
    private boolean email_verified;
    private String gender;
    private String birthdate;
    @JsonProperty("https://surf-sense.com/timezone")
    private String zoneinfo;
    private String locale;
    private String phone_number;
    private boolean phone_number_verified;
    private String updated_at;
    @JsonProperty("https://surf-sense.com/roles")
    private List<String> roles;

    public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles.stream()
          .map(role -> {
            switch (role) {
              case "admin":
                return new SimpleGrantedAuthority("ROLE_ADMIN");

              default:
                return new SimpleGrantedAuthority("ROLE_USER");
            }
          })
          .toList();
    }
  }
}
