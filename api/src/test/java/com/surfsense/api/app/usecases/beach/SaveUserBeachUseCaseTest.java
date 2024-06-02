package com.surfsense.api.app.usecases.beach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.CountryCode;
import com.surfsense.api.app.entities.beach.Position;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.ConflictException;
import com.surfsense.api.app.repositories.BeachRepository;

@ExtendWith(MockitoExtension.class)
public class SaveUserBeachUseCaseTest {
  @Mock
  private BeachRepository beachRepository;

  @InjectMocks
  private SaveUserBeachUseCase saveUserBeachUseCase;

  private final String USER_ID = "user123";
  private final String BEACH_NAME = "Copacabana";
  private final double LATITUDE = 22.9707;
  private final double LONGITUDE = -43.1824;
  private final String IMAGE_URL = "https://example.com/image.jpg";
  private final Position POSITION = Position.SOUTH;
  private final CountryCode COUNTRY_CODE = CountryCode.BR;
  private final String CITY = "Rio de Janeiro";

  @Test
  void shouldCreateBeachSuccessfully() throws ApiException {
    SaveUserBeachUseCase.BeachData data = new SaveUserBeachUseCase.BeachData(
        BEACH_NAME, LATITUDE, LONGITUDE, IMAGE_URL, POSITION, COUNTRY_CODE, CITY);
    SaveUserBeachUseCase.SaveUserBeachParams params = new SaveUserBeachUseCase.SaveUserBeachParams(data, USER_ID);

    when(beachRepository.countByUser(USER_ID)).thenReturn(0L);
    when(beachRepository.existsByCoordinates(any(), any())).thenReturn(false);
    when(beachRepository.existsByName(any(), any())).thenReturn(false);
    when(beachRepository.create(any())).thenAnswer(invocation -> {
      Beach beach = invocation.getArgument(0);
      beach = new Beach(
          UUID.randomUUID(),
          beach.getName(),
          beach.getImageUrl(),
          beach.getUserId(),
          beach.getLocation(),
          beach.getCoordinates(),
          beach.getPosition(),
          beach.getCreatedAt());
      return beach;
    });

    Beach createdBeach = saveUserBeachUseCase.execute(params);

    assertNotNull(createdBeach.getId());
    assertEquals(BEACH_NAME, createdBeach.getName());
    assertEquals(LATITUDE, createdBeach.getCoordinates().latitude());
    assertEquals(LONGITUDE, createdBeach.getCoordinates().longitude());
    verify(beachRepository, times(1)).create(any(Beach.class));
  }

  @Test
  void shouldThrowConflictException_whenUserReachesBeachLimit() {
    SaveUserBeachUseCase.BeachData data = new SaveUserBeachUseCase.BeachData(
        BEACH_NAME, LATITUDE, LONGITUDE, IMAGE_URL, POSITION, COUNTRY_CODE, CITY);
    SaveUserBeachUseCase.SaveUserBeachParams params = new SaveUserBeachUseCase.SaveUserBeachParams(data, USER_ID);

    when(beachRepository.countByUser(USER_ID)).thenReturn(5L);

    assertThrows(ConflictException.class, () -> saveUserBeachUseCase.execute(params));
    verify(beachRepository, never()).create(any(Beach.class));
  }

  @Test
  void shouldThrowConflictException_whenBeachCoordinatesAlreadyExists() {
    SaveUserBeachUseCase.BeachData data = new SaveUserBeachUseCase.BeachData(
        BEACH_NAME, LATITUDE, LONGITUDE, IMAGE_URL, POSITION, COUNTRY_CODE, CITY);
    SaveUserBeachUseCase.SaveUserBeachParams params = new SaveUserBeachUseCase.SaveUserBeachParams(data, USER_ID);

    when(beachRepository.countByUser(USER_ID)).thenReturn(0L);
    when(beachRepository.existsByCoordinates(any(), any())).thenReturn(true);

    assertThrows(ConflictException.class, () -> saveUserBeachUseCase.execute(params));
    verify(beachRepository, never()).create(any(Beach.class));
  }

  @Test
  void shouldThrowConflictException_whenBeachNameAlreadyExists() {
    SaveUserBeachUseCase.BeachData data = new SaveUserBeachUseCase.BeachData(
        BEACH_NAME, LATITUDE, LONGITUDE, IMAGE_URL, POSITION, COUNTRY_CODE, CITY);
    SaveUserBeachUseCase.SaveUserBeachParams params = new SaveUserBeachUseCase.SaveUserBeachParams(data, USER_ID);

    when(beachRepository.countByUser(USER_ID)).thenReturn(0L);
    when(beachRepository.existsByCoordinates(any(), any())).thenReturn(false);
    when(beachRepository.existsByName(any(), any())).thenReturn(true);

    assertThrows(ConflictException.class, () -> saveUserBeachUseCase.execute(params));
    verify(beachRepository, never()).create(any(Beach.class));
  }
}
