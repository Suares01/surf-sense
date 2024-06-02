package com.surfsense.api.app.usecases.users;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.services.UserService;
import com.surfsense.api.app.usecases.users.ResetPasswordUseCase.ResetPasswordParams;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordUseCaseTest {
  @Mock
  private UserService userService;

  @InjectMocks
  private ResetPasswordUseCase resetPasswordUseCase;

  @Test
  void shouldResetPasswordSuccessfully() {
    String email = "user@mail.com";
    var params = new ResetPasswordParams(email, true);

    resetPasswordUseCase.execute(params);

    verify(userService).resetPassword(email);
  }

  @Test
  void shouldThrowBadRequestException_whenEmailIsNotVerified() {
    String email = "user@mail.com";
    var params = new ResetPasswordParams(email, false);

    assertThrows(BadRequestException.class, () -> resetPasswordUseCase.execute(params));
  }
}
