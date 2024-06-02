package com.surfsense.api.infra.configuration.http;

import java.io.Serializable;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.surfsense.api.app.services.UserService.RootAttributes;
import com.surfsense.api.app.services.UserService.UserProfile;
import com.surfsense.api.infra.services.Auth0Service.UserInfo;

import feign.Headers;
import lombok.Getter;
import lombok.Setter;

@Configuration
public class Auth0ClientConfig {

  @FeignClient(name = "auth0Client")
  public static interface Auth0Client {
    @GetMapping(value = "/userinfo")
    UserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);

    @PostMapping(value = "/oauth/token")
    @Headers({
      "Content-Type: application/x-www-form-urlencoded",
    })
    ManagementAPIToken getManagementAPIToken(@RequestBody MultiValueMap<String, String> formData);

    @PostMapping(value = "/dbconnections/change_password")
    @Headers({
      "Content-Type: application/json"
    })
    void resetPassword(@RequestBody ChangePasswordRequest body);

    @PostMapping(value = "/api/v2/jobs/verification-email")
    @Headers({
      "Content-Type: application/json"
    })
    void sendEmailVerification(@RequestHeader("Authorization") String bearerToken, @RequestBody SendEmailVerificationRequest body);

    @PatchMapping(value = "/api/v2/users/{id}")
    @Headers({
      "Content-Type: application/json",
      "Cache-Control: no-cache"
    })
    UserProfile updateUserProfile(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") String userId, @RequestBody RootAttributes attributes);

    @PatchMapping(value = "/api/v2/users/{id}")
    @Headers({
      "Content-Type: application/json",
      "Cache-Control: no-cache"
    })
    UserProfile updateUserEmail(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") String userId, @RequestBody UpdateEmailRequest body);
  }

  @Getter
  @Setter
  public static class ManagementAPIToken implements Serializable {
    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
  }

  @Getter
  @Setter
  public static class ChangePasswordRequest implements Serializable {
    private String client_id;
    private String email;
    private String connection;
  }

  @Getter
  @Setter
  public static class SendEmailVerificationRequest implements Serializable {
    private String user_id;
    private String client_id;
  }

  @Getter
  @Setter
  public static class UpdateEmailRequest implements Serializable {
    private String email;
    private boolean email_verified;
    private String connection;
  }
}
