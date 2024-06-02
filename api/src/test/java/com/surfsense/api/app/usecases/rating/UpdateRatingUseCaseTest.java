package com.surfsense.api.app.usecases.rating;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateCriterionData;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateRatingData;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateRatingParams;

@ExtendWith(MockitoExtension.class)
public class UpdateRatingUseCaseTest {
  @Mock
  private RatingService ratingService;

  @InjectMocks
  private UpdateRatingUseCase updateRatingUseCase;

  @Test
  void shouldUpdateRatingSuccessfully() {
    var rating = Factories.buildRating(RatingType.SURFER);
    var criterion1 = Factories.buildCriterion(CriterionType.SWELL_HEIGHT, 0.5f, rating);
    var criterion2 = Factories.buildCriterion(CriterionType.SWELL_DIRECTION, 0.6f, rating);
    var criteria = List.of(criterion1, criterion2);

    var criterion1UpdateData = new UpdateCriterionData(criterion1.getId(), criterion1.getType(), criterion1.getWeight());
    var newCriterionData = new UpdateCriterionData(null, CriterionType.SWELL_DIRECTION, 0.7f);
    var criteriaToUpdate = Set.of(criterion1UpdateData, newCriterionData);
    var updateRatingData = new UpdateRatingData(rating.getName(), rating.getType(), criteriaToUpdate);
    var params = new UpdateRatingParams(rating.getId(), rating.getUserId(), updateRatingData);

    when(ratingService.findById(rating.getId())).thenReturn(rating);
    when(ratingService.getRatingCriteria(rating.getId())).thenReturn(criteria);

    updateRatingUseCase.execute(params);

    Set<Criterion> upsertCriteria = new HashSet<>();
    var expectedNewCriterion = Factories.buildCriterion(newCriterionData.type(), newCriterionData.weight(), rating);
    expectedNewCriterion.setId(null);
    upsertCriteria.add(expectedNewCriterion);
    upsertCriteria.add(criterion1);

    Set<UUID> criteriaToDelete = Set.of(criterion2.getId());

    verify(ratingService).update(rating, upsertCriteria, criteriaToDelete);
  }

  @Test
  void shouldThrowNotFoundException_whenRatingNotFound() {
    UUID ratingId = UUID.randomUUID();
    String userId = "user123";

    when(ratingService.findById(ratingId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> updateRatingUseCase.execute(
        new UpdateRatingUseCase.UpdateRatingParams(ratingId, userId, null)));
  }

  @Test
  void shouldThrowBadRequestException_whenUserUnauthorized() {
    var rating = Factories.buildRating(RatingType.SURFER);

    when(ratingService.findById(rating.getId())).thenReturn(rating);

    assertThrows(BadRequestException.class, () -> updateRatingUseCase.execute(
        new UpdateRatingUseCase.UpdateRatingParams(rating.getId(), "another-user-id", null)));
  }
}
