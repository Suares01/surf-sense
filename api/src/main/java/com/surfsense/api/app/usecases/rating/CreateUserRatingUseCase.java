package com.surfsense.api.app.usecases.rating;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.CreateUserRatingUseCase.CreateUserRatingParams;
import com.surfsense.api.app.usecases.UseCase;

public class CreateUserRatingUseCase implements UseCase<CreateUserRatingParams, Rating> {

  private final RatingService ratingService;

  public CreateUserRatingUseCase(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  public Rating execute(CreateUserRatingParams params) throws ApiException {
    RatingData data = params.data;

    var rating = new Rating(
        data.name,
        data.type,
        params.userId,
        Instant.now());

    var createdRating = ratingService.create(rating, mapCriterion(data.criteria()));

    return createdRating;
  }

  private Set<Criterion> mapCriterion(Set<CriterionData> criteria) {
    return criteria.stream()
        .map((data) -> {
          var criterion = new Criterion(
              data.type,
              data.weight,
              null,
              Instant.now());

          return criterion;
        })
        .collect(Collectors.toSet());
  }

  public static record CriterionData(CriterionType type, Float weight) {
  }

  public static record RatingData(String name, RatingType type, Set<CriterionData> criteria) {
  }

  public static record CreateUserRatingParams(String userId, RatingData data) {
  }
}
