package com.surfsense.api.infra.configuration.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.usecases.users.ResetPasswordUseCase;
import com.surfsense.api.app.usecases.users.SendEmailVerificationUseCase;
import com.surfsense.api.app.usecases.users.UpdateEmailUseCase;
import com.surfsense.api.app.usecases.users.UpdateRootAttributesUseCase;
import com.surfsense.api.infra.services.Auth0Service;

@Configuration
public class UserConfig {
  @Bean
  UserService userService(Auth0Service auth0Service) {
    return auth0Service;
  }

  @Bean
  ResetPasswordUseCase resetPasswordUseCase(UserService userService) {
    return new ResetPasswordUseCase(userService);
  }

  @Bean
  SendEmailVerificationUseCase sendEmailVerificationUseCase(UserService userService) {
    return new SendEmailVerificationUseCase(userService);
  }

  @Bean
  UpdateEmailUseCase updateEmailUseCase(UserService userService) {
    return new UpdateEmailUseCase(userService);
  }

  @Bean
  UpdateRootAttributesUseCase updateRootAttributesUseCase(UserService userService) {
    return new UpdateRootAttributesUseCase(userService);
  }
}
