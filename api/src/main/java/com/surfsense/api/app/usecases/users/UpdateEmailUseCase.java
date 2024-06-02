package com.surfsense.api.app.usecases.users;

import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.usecases.UseCase;
import com.surfsense.api.app.usecases.users.UpdateEmailUseCase.UpdateEmailParams;;

public class UpdateEmailUseCase implements UseCase<UpdateEmailParams, Void> {
  private final UserService userService;

  public UpdateEmailUseCase(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Void execute(UpdateEmailParams params) throws ApiException {
    userService.updateEmail(params.userId(), params.newEmail());

    return null;
  }

  public static record UpdateEmailParams(String userId, String newEmail) {
  }
}
