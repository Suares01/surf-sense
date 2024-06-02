package com.surfsense.api.app.usecases.rating;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.services.RatingService;

@ExtendWith(MockitoExtension.class)
public class DeleteUserRatingUseCaseTest {
  @Mock
  private RatingService ratingService;

  @InjectMocks
  private DeleteUserRatingUseCase deleteUserRatingUseCase;

  @Test
  void shouldDeleteRatingSuccessfully() {
    var rating = Factories.buildRating(RatingType.SURFER);

    when(ratingService.findById(rating.getId())).thenReturn(rating);
    when(ratingService.getRatingCriteria(rating.getId())).thenReturn(List.of());

    deleteUserRatingUseCase
        .execute(new DeleteUserRatingUseCase.DeleteUserRatingParams(rating.getId(), rating.getUserId()));

    verify(ratingService).delete(rating.getId(), List.of());
  }

  @Test
  void shouldThrowNotFoundException_whenRatingNotFound() {
    UUID ratingId = UUID.randomUUID();
    String userId = "user123";

    when(ratingService.findById(ratingId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> deleteUserRatingUseCase.execute(
        new DeleteUserRatingUseCase.DeleteUserRatingParams(ratingId, userId)));
  }

  @Test
  void shouldThrowBadRequestException_whenUserUnauthorized() {
    var rating = Factories.buildRating(RatingType.SURFER);

    when(ratingService.findById(rating.getId())).thenReturn(rating);

    assertThrows(BadRequestException.class, () -> deleteUserRatingUseCase.execute(
        new DeleteUserRatingUseCase.DeleteUserRatingParams(rating.getId(), "another-user-id")));
  }
}
