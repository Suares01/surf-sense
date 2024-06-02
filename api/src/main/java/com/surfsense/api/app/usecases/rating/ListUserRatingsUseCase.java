package com.surfsense.api.app.usecases.rating;

import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.ListUserRatingsUseCase.ListUserRatingsParams;
import com.surfsense.api.app.utils.RatingsCriteriaMapper;
import com.surfsense.api.app.utils.RatingsCriteriaMapper.RatingWithCriteria;
import com.surfsense.api.app.usecases.UseCase;

public class ListUserRatingsUseCase implements UseCase<ListUserRatingsParams, List<RatingWithCriteria>> {
  private final RatingService ratingService;

  public ListUserRatingsUseCase(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  public List<RatingWithCriteria> execute(ListUserRatingsParams params) throws ApiException {
    List<Rating> ratings = ratingService.findByUser(params.userId);
    List<UUID> ratingIds = ratings.stream()
        .map(rating -> rating.getId())
        .toList();

    List<Criterion> criteria = ratingService.getRatingCriteria(ratingIds);

    List<RatingWithCriteria> ratingsWithCriteria = ratings.stream()
        .map(rating -> {
          List<Criterion> ratingCriteria = criteria.stream()
              .filter(criterion -> criterion.getRating().getId().equals(rating.getId()))
              .toList();

          return RatingsCriteriaMapper.map(rating, ratingCriteria);
        })
        .toList();

    return ratingsWithCriteria;
  }

  public static record ListUserRatingsParams(String userId) {
  }
}
