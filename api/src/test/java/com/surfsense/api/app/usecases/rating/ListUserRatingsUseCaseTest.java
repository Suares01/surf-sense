package com.surfsense.api.app.usecases.rating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.utils.RatingsCriteriaMapper.RatingWithCriteria;

@ExtendWith(MockitoExtension.class)
public class ListUserRatingsUseCaseTest {
  @Mock
  private RatingService ratingService;

  @InjectMocks
  private ListUserRatingsUseCase listUserRatingsUseCase;

  @Test
  void shouldListUserRatingWithCriteriaSuccessfully() {
    String userId = "user123";

    var rating1 = Factories.buildRating(RatingType.SURFER);
    var rating2 = Factories.buildRating(RatingType.BEACHGOER);
    rating1.setUserId(userId);
    rating2.setUserId(userId);

    Criterion criterion1 = Factories.buildCriterion(CriterionType.SWELL_HEIGHT, 0.5f, rating1);
    Criterion criterion2 = Factories.buildCriterion(CriterionType.SWELL_DIRECTION, 0.5f, rating1);
    Criterion criterion3 = Factories.buildCriterion(CriterionType.SWELL_PERIOD, 0.5f, rating2);

    when(ratingService.findByUser(userId)).thenReturn(List.of(rating1, rating2));
    when(ratingService.getRatingCriteria(List.of(rating1.getId(), rating2.getId())))
        .thenReturn(List.of(criterion1, criterion2, criterion3));

    List<RatingWithCriteria> result = listUserRatingsUseCase.execute(
        new ListUserRatingsUseCase.ListUserRatingsParams(userId));

    assertEquals(2, result.size());

    assertEquals(rating1.getId(), result.get(0).getId());
    assertEquals(2, result.get(0).getCriteria().size());
    assertEquals(criterion1.getId(), result.get(0).getCriteria().get(0).getId());
    assertEquals(criterion2.getId(), result.get(0).getCriteria().get(1).getId());

    assertEquals(rating2.getId(), result.get(1).getId());
    assertEquals(1, result.get(1).getCriteria().size());
    assertEquals(criterion3.getId(), result.get(1).getCriteria().get(0).getId());
  }
}
