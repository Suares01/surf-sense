package com.surfsense.api.app.usecases.users;

import com.surfsense.api.app.usecases.users.ResetPasswordUseCase.ResetPasswordParams;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.usecases.UseCase;

public class ResetPasswordUseCase implements UseCase<ResetPasswordParams, Void> {
  private final UserService userService;

  public ResetPasswordUseCase(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Void execute(ResetPasswordParams params) throws ApiException {
    if (!params.isEmailVerified())
      throw new BadRequestException("Usuário precisa verificar o e-mail para resetar a senha.");

    userService.resetPassword(params.email());

    return null;
  }

  public static record ResetPasswordParams(String email, boolean isEmailVerified) {
  }
}
