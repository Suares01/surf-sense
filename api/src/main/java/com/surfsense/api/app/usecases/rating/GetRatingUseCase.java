package com.surfsense.api.app.usecases.rating;

import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.GetRatingUseCase.GetRatingParams;
import com.surfsense.api.app.usecases.UseCase;

public class GetRatingUseCase implements UseCase<GetRatingParams, Rating> {
  private final RatingService ratingService;

  public GetRatingUseCase(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  public Rating execute(GetRatingParams params) throws ApiException {
    Rating rating = ratingService.findById(params.ratingId);

    if (rating == null)
      throw new NotFoundException("Avaliação não encontrada");

    return rating;
  }

  public static record GetRatingParams(UUID ratingId) {
  }
}
