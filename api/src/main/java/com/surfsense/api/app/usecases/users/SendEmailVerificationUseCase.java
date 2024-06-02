package com.surfsense.api.app.usecases.users;

import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.usecases.users.SendEmailVerificationUseCase.SendEmailVerificationParams;
import com.surfsense.api.app.usecases.UseCase;

public class SendEmailVerificationUseCase implements UseCase<SendEmailVerificationParams, Void> {
  private final UserService userService;

  public SendEmailVerificationUseCase(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Void execute(SendEmailVerificationParams params) throws ApiException {
    if (params.isEmailVerified())
      throw new BadRequestException("E-mail já verificado.");

    userService.sendEmailVerification(params.userId());

    return null;
  }

  public static record SendEmailVerificationParams(String userId, boolean isEmailVerified) {
  }
}
