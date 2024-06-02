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
import com.surfsense.api.app.usecases.users.SendEmailVerificationUseCase.SendEmailVerificationParams;

@ExtendWith(MockitoExtension.class)
public class SendEmailVerificationUseCaseTest {
  @Mock
  private UserService userService;

  @InjectMocks
  private SendEmailVerificationUseCase sendEmailVerificationUseCase;

  @Test
  void shouldSendEmailVerificationSuccessfully() {
    String userId = "userId";
    var params = new SendEmailVerificationParams(userId, false);

    sendEmailVerificationUseCase.execute(params);

    verify(userService).sendEmailVerification(userId);
  }

  @Test
  void shouldThrowBadRequestException_whenEmailIsVerified() {
    String userId = "userId";
    var params = new SendEmailVerificationParams(userId, true);

    assertThrows(BadRequestException.class, () -> sendEmailVerificationUseCase.execute(params));
  }
}
