package com.surfsense.api.app.usecases.beach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.repositories.BeachRepository;

@ExtendWith(MockitoExtension.class)
public class ListUserBeachesUseCaseTest {
  @Mock
  private BeachRepository beachRepository;

  @InjectMocks
  private ListUserBeachesUseCase listUserBeachesUseCase;

  private final String USER_ID = UUID.randomUUID().toString();

  @Test
  void shouldReturnUserBeaches() throws ApiException {
    var beach1 = Factories.buildBeach();
    var beach2 = Factories.buildBeach();
    beach1.setUserId(USER_ID);
    beach2.setUserId(USER_ID);
    List<Beach> beaches = List.of(beach1, beach2);

    when(beachRepository.listByUser(USER_ID)).thenReturn(beaches);

    ListUserBeachesUseCase.ListUserBeachesParams params = new ListUserBeachesUseCase.ListUserBeachesParams(USER_ID);
    List<Beach> result = listUserBeachesUseCase.execute(params);

    assertEquals(beaches.size(), result.size());
    verify(beachRepository, times(1)).listByUser(USER_ID);
  }

  @Test
  void shouldReturnEmptyList_whenUserHasNoBeaches() throws ApiException {
    when(beachRepository.listByUser(USER_ID)).thenReturn(Collections.emptyList());

    ListUserBeachesUseCase.ListUserBeachesParams params = new ListUserBeachesUseCase.ListUserBeachesParams(USER_ID);
    List<Beach> result = listUserBeachesUseCase.execute(params);

    assertTrue(result.isEmpty());
    verify(beachRepository, times(1)).listByUser(USER_ID);
  }
}
