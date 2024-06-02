package com.surfsense.api.app.usecases.beach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.repositories.BeachRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteBeachUseCaseTest {
  @Mock
  private BeachRepository beachRepository;

  @InjectMocks
  private DeleteBeachUseCase deleteBeachUseCase;

  @Test
  void shouldDeleteBeachSuccessfully() throws ApiException {
    Beach beach = Factories.buildBeach();
    when(beachRepository.findById(beach.getId())).thenReturn(beach);

    deleteBeachUseCase.execute(new DeleteBeachUseCase.DeleteBeachParams(beach.getId(), beach.getUserId()));

    verify(beachRepository, times(1)).delete(beach.getId());
  }

  @Test
  void shouldThrowNotFoundException_whenBeachNotFound() {
    UUID beachId = UUID.randomUUID();
    String userId = "user123";
    when(beachRepository.findById(beachId)).thenReturn(null);

    assertThrows(NotFoundException.class,
        () -> deleteBeachUseCase.execute(new DeleteBeachUseCase.DeleteBeachParams(beachId, userId)));

    verify(beachRepository, never()).delete(any());
  }

  @Test
  void shouldThrowBadRequestException_whenUserUnauthorized() {
    Beach beach = Factories.buildBeach();
    when(beachRepository.findById(beach.getId())).thenReturn(beach);

    assertThrows(BadRequestException.class,
        () -> deleteBeachUseCase.execute(new DeleteBeachUseCase.DeleteBeachParams(beach.getId(), "another-user-id")));

    verify(beachRepository, never()).delete(any());
  }
}
