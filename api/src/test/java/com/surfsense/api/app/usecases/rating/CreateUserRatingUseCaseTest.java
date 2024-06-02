package com.surfsense.api.app.usecases.rating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.services.RatingService;

@ExtendWith(MockitoExtension.class)
public class CreateUserRatingUseCaseTest {
  @Mock
  private RatingService ratingService;

  @InjectMocks
  private CreateUserRatingUseCase createUserRatingUseCase;

  @SuppressWarnings("unchecked")
  @Test
  void shouldCreateRatingSuccessfully() {
    var rating = Factories.buildRating(RatingType.SURFER);
    var criteiron = Factories.buildCriterion(CriterionType.SWELL_HEIGHT, 0.5f, rating);

    var criterionData = new CreateUserRatingUseCase.CriterionData(criteiron.getType(), criteiron.getWeight());
    var ratingData = new CreateUserRatingUseCase.RatingData(rating.getName(), rating.getType(), Set.of(criterionData));
    var params = new CreateUserRatingUseCase.CreateUserRatingParams(rating.getUserId(), ratingData);

    when(ratingService.create(any(Rating.class), any(Set.class))).thenReturn(rating);

    var createdRating = createUserRatingUseCase.execute(params);

    assertNotNull(createdRating);
    assertEquals(rating, createdRating);
  }
}
