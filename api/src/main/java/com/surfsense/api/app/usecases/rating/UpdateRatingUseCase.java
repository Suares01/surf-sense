package com.surfsense.api.app.usecases.rating;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateRatingParams;
import com.surfsense.api.app.usecases.UseCase;

public class UpdateRatingUseCase implements UseCase<UpdateRatingParams, Void> {
  private final RatingService ratingService;

  public UpdateRatingUseCase(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @Override
  public Void execute(UpdateRatingParams params) throws ApiException {
    Rating rating = ratingService.findById(params.ratingId);

    if (rating == null)
      throw new NotFoundException("Avaliação não encontrada");

    if (!rating.getUserId().equals(params.userId()))
      throw new BadRequestException("Avaliação não pertence a esse usuário");

    Map<UUID, Criterion> existingCriteria = ratingService.getRatingCriteria(rating.getId()).stream()
        .collect(Collectors.toMap(Criterion::getId, criterion -> criterion));

    Set<UpdateCriterionData> newCriteria = params.data.criteria;
    Set<Criterion> upsertCriteria = new HashSet<>();

    for (UpdateCriterionData newCriterion : newCriteria) {
      if (newCriterion.id() == null) {
        var criterion = new Criterion();
        criterion.setType(newCriterion.type);
        criterion.setWeight(newCriterion.weight);
        criterion.setRating(rating);
        criterion.setCreatedAt(Instant.now());

        upsertCriteria.add(criterion);
      } else if (existingCriteria.containsKey(newCriterion.id())) {
        Criterion existingCriterion = existingCriteria.get(newCriterion.id());
        existingCriterion.setType(newCriterion.type());
        existingCriterion.setWeight(newCriterion.weight());

        upsertCriteria.add(existingCriterion);
        existingCriteria.remove(newCriterion.id());
      }
    }

    Set<UUID> criteriaToDelete = existingCriteria.keySet();

    rating.setName(params.data.name);
    rating.setType(params.data.type);

    ratingService.update(rating, upsertCriteria, criteriaToDelete);

    return null;
  }

  public static record UpdateCriterionData(UUID id, CriterionType type, Float weight) {
  }

  public static record UpdateRatingData(String name, RatingType type, Set<UpdateCriterionData> criteria) {
  }

  public static record UpdateRatingParams(UUID ratingId, String userId, UpdateRatingData data) {
  }
}
