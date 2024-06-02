package com.surfsense.api.infra.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surfsense.api.app.services.UserService.RootAttributes;
import com.surfsense.api.app.services.UserService.UserProfile;
import com.surfsense.api.app.usecases.users.ResetPasswordUseCase;
import com.surfsense.api.app.usecases.users.ResetPasswordUseCase.ResetPasswordParams;
import com.surfsense.api.app.usecases.users.SendEmailVerificationUseCase;
import com.surfsense.api.app.usecases.users.SendEmailVerificationUseCase.SendEmailVerificationParams;
import com.surfsense.api.app.usecases.users.UpdateEmailUseCase;
import com.surfsense.api.app.usecases.users.UpdateEmailUseCase.UpdateEmailParams;
import com.surfsense.api.app.usecases.users.UpdateRootAttributesUseCase;
import com.surfsense.api.app.usecases.users.UpdateRootAttributesUseCase.UpdateRootAttributesParams;
import com.surfsense.api.infra.controllers.dtos.UpdateEmailDTO;
import com.surfsense.api.infra.services.Auth0Service.UserInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
  private final UpdateRootAttributesUseCase updateRootAttributesUseCase;
  private final ResetPasswordUseCase resetPasswordUseCase;
  private final UpdateEmailUseCase updateEmailUseCase;
  private final SendEmailVerificationUseCase sendEmailVerificationUseCase;

  @PatchMapping("profile")
  public ResponseEntity<UserProfile> updateRootAttributes(@RequestBody RootAttributes body, @AuthenticationPrincipal UserInfo userInfo) {
    var request = new UpdateRootAttributesParams(userInfo.getSub(), body);
    UserProfile profile = updateRootAttributesUseCase.execute(request);

    return ResponseEntity.ok(profile);
  }

  @PatchMapping("update_email")
  public ResponseEntity<Void> updateEmail(@RequestBody @Valid UpdateEmailDTO body, @AuthenticationPrincipal UserInfo userInfo) {
    var request = new UpdateEmailParams(userInfo.getSub(), body.email());
    updateEmailUseCase.execute(request);

    return ResponseEntity.ok().build();
  }

  @PostMapping("send_email_verification")
  public ResponseEntity<Void> sendEmailVerification(@AuthenticationPrincipal UserInfo userInfo) {
    var request = new SendEmailVerificationParams(userInfo.getSub(), userInfo.isEmail_verified());
    sendEmailVerificationUseCase.execute(request);

    return ResponseEntity.ok().build();
  }

  @PostMapping("reset_password")
  public ResponseEntity<Void> resetPassword(@AuthenticationPrincipal UserInfo userInfo) {
    var request = new ResetPasswordParams(userInfo.getEmail(), userInfo.isEmail_verified());
    resetPasswordUseCase.execute(request);

    return ResponseEntity.noContent().build();
  }
}
