package com.surfsense.api.app.usecases.rating;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.CreateDefaultRatingsUseCase.CreateDefaultRatingsParams;

@ExtendWith(MockitoExtension.class)
public class CreateDefaultRatingsUseCaseTest {
  @Mock
  private RatingService ratingService;

  @InjectMocks
  private CreateDefaultRatingsUseCase createDefaultRatingsUseCase;

  private final String USER_ID = "user-id";

  @Test
  void shouldCreateDefaultRatingsSuccessfully() {
    var params = new CreateDefaultRatingsParams(USER_ID);

    createDefaultRatingsUseCase.execute(params);

    verify(ratingService, times(1)).createAll(generateExpectedRatings());
  }

  private Map<Rating, Collection<Criterion>> generateExpectedRatings() {
    var ratingForSurfer = Factories.buildRating(RatingType.SURFER);
    ratingForSurfer.setId(null);
    ratingForSurfer.setUserId(USER_ID);
    ratingForSurfer.setName("Avaliação de surf");

    var criteriaForSurfer = List.of(
        buildCriterion(CriterionType.WAVE_HEIGHT, 0.8F),
        buildCriterion(CriterionType.WAVE_DIRECTION, 0.9F),
        buildCriterion(CriterionType.WAVE_PERIOD, 0.6F),
        buildCriterion(CriterionType.SWELL_HEIGHT, 0.7F),
        buildCriterion(CriterionType.SWELL_DIRECTION, 0.9F),
        buildCriterion(CriterionType.SWELL_PERIOD, 0.6F),
        buildCriterion(CriterionType.WIND_DIRECTION, 0.5F));

    var ratingForBeachgoer = Factories.buildRating(RatingType.BEACHGOER);
    ratingForBeachgoer.setId(null);
    ratingForBeachgoer.setUserId(USER_ID);
    ratingForBeachgoer.setName("Avaliação de banhista");

    var criteriaForBeachgoer = List.of(
        buildCriterion(CriterionType.WAVE_HEIGHT, 0.6F),
        buildCriterion(CriterionType.WAVE_DIRECTION, 0.7F),
        buildCriterion(CriterionType.WAVE_PERIOD, 0.4F),
        buildCriterion(CriterionType.SWELL_HEIGHT, 0.5F),
        buildCriterion(CriterionType.SWELL_DIRECTION, 0.6F),
        buildCriterion(CriterionType.SWELL_PERIOD, 0.4F),
        buildCriterion(CriterionType.WIND_DIRECTION, 0.3F));

    return Map.of(
        ratingForSurfer,
        criteriaForSurfer,
        ratingForBeachgoer,
        criteriaForBeachgoer);
  }

  private Criterion buildCriterion(CriterionType type, Float weight) {
    var criterion = Factories.buildCriterion(type, weight, null);
    criterion.setId(null);
    return criterion;
  }
}
