package com.surfsense.api.app.usecases.users;

import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.services.UserService.RootAttributes;
import com.surfsense.api.app.services.UserService.UserProfile;
import com.surfsense.api.app.usecases.UseCase;
import com.surfsense.api.app.usecases.users.UpdateRootAttributesUseCase.UpdateRootAttributesParams;

public class UpdateRootAttributesUseCase implements UseCase<UpdateRootAttributesParams, UserProfile> {
  private final UserService userService;

  public UpdateRootAttributesUseCase(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserProfile execute(UpdateRootAttributesParams params) throws ApiException {
    return userService.updateUserRootAttributes(params.userId(), params.attributes());
  }

  public static record UpdateRootAttributesParams(String userId, RootAttributes attributes) {
  }
}
