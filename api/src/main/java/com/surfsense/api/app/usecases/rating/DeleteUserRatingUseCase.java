package com.surfsense.api.app.usecases.rating;

import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.DeleteUserRatingUseCase.DeleteUserRatingParams;
import com.surfsense.api.app.usecases.UseCase;

public class DeleteUserRatingUseCase implements UseCase<DeleteUserRatingParams, Void> {
  private final RatingService ratingService;

  public DeleteUserRatingUseCase(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  public Void execute(DeleteUserRatingParams params) throws ApiException {
    Rating rating = ratingService.findById(params.ratingId);

    if (rating == null)
      throw new NotFoundException("Avaliação não encontrada");

    if (!rating.getUserId().equals(params.userId()))
      throw new BadRequestException("Avaliação não pertence a esse usuário");

    List<Criterion> criteria = ratingService.getRatingCriteria(rating.getId());

    ratingService.delete(rating.getId(), criteria);

    return null;
  }

  public static record DeleteUserRatingParams(UUID ratingId, String userId) {
  }
}
