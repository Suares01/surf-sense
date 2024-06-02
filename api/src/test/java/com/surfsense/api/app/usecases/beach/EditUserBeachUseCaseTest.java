package com.surfsense.api.app.usecases.beach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.ConflictException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.repositories.BeachRepository;

@ExtendWith(MockitoExtension.class)
public class EditUserBeachUseCaseTest {
  @Mock
  private BeachRepository beachRepository;

  @InjectMocks
  private EditUserBeachUseCase editUserBeachUseCase;

  @Test
  void shouldUpdateBeachSuccessfully() throws ApiException {
    Beach beach = Factories.buildBeach();
    var dataToUpdate = new EditUserBeachUseCase.EditBeachData(
        "Copacabana",
        8.0,
        10.0,
        beach.getImageUrl(),
        beach.getPosition(),
        beach.getLocation().countryCode(),
        beach.getLocation().city());
    var params = new EditUserBeachUseCase.EditUserBeachParams(beach.getId(), beach.getUserId(), dataToUpdate);

    when(beachRepository.findById(beach.getId())).thenReturn(beach);
    when(beachRepository.existsByName(dataToUpdate.name(), params.userId())).thenReturn(false);
    when(beachRepository.existsByCoordinates(new Coordinates(dataToUpdate.latitude(), dataToUpdate.longitude()),
        params.userId())).thenReturn(false);
    when(beachRepository.update(any(Beach.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Beach updatedBeach = editUserBeachUseCase.execute(params);

    assertNotNull(updatedBeach);
    assertEquals(dataToUpdate.name(), updatedBeach.getName());
    assertEquals(dataToUpdate.latitude(), updatedBeach.getCoordinates().latitude());
    assertEquals(dataToUpdate.longitude(), updatedBeach.getCoordinates().longitude());
    assertEquals(beach.getImageUrl(), updatedBeach.getImageUrl());
    assertEquals(beach.getPosition(), updatedBeach.getPosition());
    assertEquals(beach.getLocation().city(), updatedBeach.getLocation().city());
    assertEquals(beach.getLocation().countryCode(), updatedBeach.getLocation().countryCode());
  }

  @Test
  void shouldThrowNotFoundException_whenBeachNotFound() {
    var params = new EditUserBeachUseCase.EditUserBeachParams(UUID.randomUUID(), null, null);

    when(beachRepository.findById(params.beachId())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> editUserBeachUseCase.execute(params));

    verify(beachRepository, never()).update(any());
  }

  @Test
  void shouldThrowBadRequestException_whenUserUnauthorized() {
    Beach beach = Factories.buildBeach();
    var params = new EditUserBeachUseCase.EditUserBeachParams(beach.getId(), "another-user-id", null);

    when(beachRepository.findById(params.beachId())).thenReturn(beach);

    assertThrows(BadRequestException.class, () -> editUserBeachUseCase.execute(params));

    verify(beachRepository, never()).update(any());
  }

  @Test
  void shouldThrowConflictException_whenBeachNameAlreadyExists() {
    Beach beach = Factories.buildBeach();
    var dataToUpdate = new EditUserBeachUseCase.EditBeachData(
        "Copacabana",
        8.0,
        10.0,
        beach.getImageUrl(),
        beach.getPosition(),
        beach.getLocation().countryCode(),
        beach.getLocation().city());
    var params = new EditUserBeachUseCase.EditUserBeachParams(beach.getId(), beach.getUserId(), dataToUpdate);

    when(beachRepository.findById(beach.getId())).thenReturn(beach);
    when(beachRepository.existsByName(dataToUpdate.name(), params.userId())).thenReturn(true);

    assertThrows(ConflictException.class, () -> editUserBeachUseCase.execute(params));

    verify(beachRepository, never()).update(any());
  }

  @Test
  void shouldThrowConflictException_whenBeachCoordinatesAlreadyExists() {
    Beach beach = Factories.buildBeach();
    var dataToUpdate = new EditUserBeachUseCase.EditBeachData(
        beach.getName(),
        8.0,
        10.0,
        beach.getImageUrl(),
        beach.getPosition(),
        beach.getLocation().countryCode(),
        beach.getLocation().city());
    var params = new EditUserBeachUseCase.EditUserBeachParams(beach.getId(), beach.getUserId(), dataToUpdate);

    when(beachRepository.findById(beach.getId())).thenReturn(beach);
    when(beachRepository.existsByCoordinates(new Coordinates(dataToUpdate.latitude(), dataToUpdate.longitude()),
        params.userId())).thenReturn(true);

    assertThrows(ConflictException.class, () -> editUserBeachUseCase.execute(params));

    verify(beachRepository, never()).update(any());
  }
}
