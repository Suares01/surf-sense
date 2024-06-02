package com.surfsense.api.app.usecases.users;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.services.UserService.RootAttributes;
import com.surfsense.api.app.usecases.users.UpdateRootAttributesUseCase.UpdateRootAttributesParams;

@ExtendWith(MockitoExtension.class)
public class UpdateRootAttributesUseCaseTest {
  @Mock
  private UserService userService;

  @InjectMocks
  private UpdateRootAttributesUseCase updateRootAttributesUseCase;

  private final String userId = "userId";

  @Test
  void shouldUpdateRootAttributesSuccessfully() {
    var attributes = new RootAttributes();
    attributes.setGiven_name("John");
    attributes.setFamily_name("Doe");
    attributes.setName("John Doe");
    attributes.setNickname("john_doe");
    attributes.setPicture("picture-url");

    var params = new UpdateRootAttributesParams(userId, attributes);

    updateRootAttributesUseCase.execute(params);

    verify(userService).updateUserRootAttributes(userId, attributes);
  }
}
