package com.surfsense.api.app.usecases.users;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.usecases.users.UpdateEmailUseCase.UpdateEmailParams;

@ExtendWith(MockitoExtension.class)
public class UpdateEmailUseCaseTest {
  @Mock
  private UserService userService;

  @InjectMocks
  private UpdateEmailUseCase updateEmailUseCase;

  private final String userId = "userId";
  private final String newEmail = "newemail@mail.com";

  @Test
  void shouldUpdateEmailSuccessfully() {
    var params = new UpdateEmailParams(userId, newEmail);

    updateEmailUseCase.execute(params);

    verify(userService).updateEmail(userId, newEmail);
  }
}
