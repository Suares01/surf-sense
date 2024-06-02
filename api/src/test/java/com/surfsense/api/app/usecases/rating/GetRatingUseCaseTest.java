package com.surfsense.api.app.usecases.rating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.services.RatingService;

@ExtendWith(MockitoExtension.class)
public class GetRatingUseCaseTest {
  @Mock
  private RatingService ratingService;

  @InjectMocks
  private GetRatingUseCase getRatingUseCase;

  @Test
  void shouldGetRatingSuccessfully() {
    var rating = Factories.buildRating(RatingType.SURFER);

    when(ratingService.findById(rating.getId())).thenReturn(rating);

    var result = getRatingUseCase.execute(
        new GetRatingUseCase.GetRatingParams(rating.getId()));

    assertNotNull(result);
    assertEquals(result, rating);
  }

  @Test
  void shouldThrowNotFoundException_whenRatingNotFound() {
    UUID ratingId = UUID.randomUUID();

    when(ratingService.findById(ratingId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> getRatingUseCase.execute(
        new GetRatingUseCase.GetRatingParams(ratingId)));
  }
}
