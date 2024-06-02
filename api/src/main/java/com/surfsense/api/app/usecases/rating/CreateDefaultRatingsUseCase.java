package com.surfsense.api.app.usecases.rating;

import com.surfsense.api.app.usecases.rating.CreateDefaultRatingsUseCase.CreateDefaultRatingsParams;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.UseCase;

public class CreateDefaultRatingsUseCase implements UseCase<CreateDefaultRatingsParams, Void> {
  private final RatingService ratingService;

  public CreateDefaultRatingsUseCase(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  public Void execute(CreateDefaultRatingsParams params) throws ApiException {
    Map<Rating, Collection<Criterion>> defaultRatings = Map.of(
        generateRatingForSurfer(params.userId),
        generateCriteriaForSurfer(),
        generateRatingForBeachgoer(params.userId),
        generateCriteriaForBeachgoer());

    ratingService.createAll(defaultRatings);

    return null;
  }

  private Rating generateRatingForSurfer(String userId) {
    var rating = new Rating();

    rating.setName("Avaliação de surf");
    rating.setType(RatingType.SURFER);
    rating.setUserId(userId);
    rating.setCreatedAt(Instant.now());

    return rating;
  }

  private Rating generateRatingForBeachgoer(String userId) {
    var rating = new Rating();

    rating.setName("Avaliação de banhista");
    rating.setType(RatingType.BEACHGOER);
    rating.setUserId(userId);
    rating.setCreatedAt(Instant.now());

    return rating;
  }

  private List<Criterion> generateCriteriaForSurfer() {
    return List.of(
        createCriterionInstance(CriterionType.WAVE_HEIGHT, 0.8F),
        createCriterionInstance(CriterionType.WAVE_DIRECTION, 0.9F),
        createCriterionInstance(CriterionType.WAVE_PERIOD, 0.6F),
        createCriterionInstance(CriterionType.SWELL_HEIGHT, 0.7F),
        createCriterionInstance(CriterionType.SWELL_DIRECTION, 0.9F),
        createCriterionInstance(CriterionType.SWELL_PERIOD, 0.6F),
        createCriterionInstance(CriterionType.WIND_DIRECTION, 0.5F));
  }

  private List<Criterion> generateCriteriaForBeachgoer() {
    return List.of(
        createCriterionInstance(CriterionType.WAVE_HEIGHT, 0.6F),
        createCriterionInstance(CriterionType.WAVE_DIRECTION, 0.7F),
        createCriterionInstance(CriterionType.WAVE_PERIOD, 0.4F),
        createCriterionInstance(CriterionType.SWELL_HEIGHT, 0.5F),
        createCriterionInstance(CriterionType.SWELL_DIRECTION, 0.6F),
        createCriterionInstance(CriterionType.SWELL_PERIOD, 0.4F),
        createCriterionInstance(CriterionType.WIND_DIRECTION, 0.3F));
  }

  private Criterion createCriterionInstance(CriterionType type, Float weight) {
    var criterion = new Criterion(
        type,
        weight,
        null,
        Instant.now());

    return criterion;
  }

  public static record CreateDefaultRatingsParams(String userId) {
  }
}
