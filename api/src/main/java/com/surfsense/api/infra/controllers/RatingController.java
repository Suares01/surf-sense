package com.surfsense.api.infra.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.usecases.rating.CreateDefaultRatingsUseCase;
import com.surfsense.api.app.usecases.rating.CreateDefaultRatingsUseCase.CreateDefaultRatingsParams;
import com.surfsense.api.app.usecases.rating.CreateUserRatingUseCase;
import com.surfsense.api.app.usecases.rating.CreateUserRatingUseCase.CreateUserRatingParams;
import com.surfsense.api.app.usecases.rating.CreateUserRatingUseCase.CriterionData;
import com.surfsense.api.app.usecases.rating.CreateUserRatingUseCase.RatingData;
import com.surfsense.api.app.usecases.rating.DeleteUserRatingUseCase;
import com.surfsense.api.app.usecases.rating.DeleteUserRatingUseCase.DeleteUserRatingParams;
import com.surfsense.api.app.usecases.rating.GetRatingUseCase;
import com.surfsense.api.app.usecases.rating.GetRatingUseCase.GetRatingParams;
import com.surfsense.api.app.usecases.rating.ListUserRatingsUseCase;
import com.surfsense.api.app.usecases.rating.ListUserRatingsUseCase.ListUserRatingsParams;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateRatingParams;
import com.surfsense.api.app.utils.RatingsCriteriaMapper.RatingWithCriteria;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateRatingData;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase.UpdateCriterionData;
import com.surfsense.api.infra.controllers.dtos.CreateDefaultRatingsDto;
import com.surfsense.api.infra.controllers.dtos.SaveRatingDto;
import com.surfsense.api.infra.controllers.dtos.UpdateRatingDto;
import com.surfsense.api.infra.services.Auth0Service.UserInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "ratings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController extends BaseController {
  private final CreateDefaultRatingsUseCase createDefaultRatingsUseCase;
  private final CreateUserRatingUseCase createUserRatingUseCase;
  private final DeleteUserRatingUseCase deleteUserRatingUseCase;
  private final GetRatingUseCase getRatingUseCase;
  private final ListUserRatingsUseCase listUserRatingsUseCase;
  private final UpdateRatingUseCase updateRatingUseCase;

  @GetMapping
  public ResponseEntity<List<RatingWithCriteria>> listUserRatings(@AuthenticationPrincipal UserInfo userInfo) {
    var params = new ListUserRatingsParams(userInfo.getSub());
    List<RatingWithCriteria> ratings = listUserRatingsUseCase.execute(params);

    return ResponseEntity.ok(ratings);
  }

  @GetMapping("{id}")
  public ResponseEntity<Rating> getRatingById(@PathVariable("id") UUID id) {
    var params = new GetRatingParams(id);
    Rating rating = getRatingUseCase.execute(params);

    return ResponseEntity.ok(rating);
  }

  @PostMapping("default")
  public ResponseEntity<Void> createDefaultRatings(@RequestBody @Valid CreateDefaultRatingsDto body) {
    var params = new CreateDefaultRatingsParams(body.userId());
    createDefaultRatingsUseCase.execute(params);

    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> createRating(@RequestBody @Valid SaveRatingDto body, @AuthenticationPrincipal UserInfo userInfo) {
    var criterionData = body.criteria().stream()
        .map(criterion -> new CriterionData(criterion.type(), criterion.weight()))
        .collect(Collectors.toSet());
    var ratingData = new RatingData(body.name(), body.type(), criterionData);
    var params = new CreateUserRatingParams(userInfo.getSub(), ratingData);

    Rating rating = createUserRatingUseCase.execute(params);

    return ResponseEntity.created(locationUri(rating.getId())).build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Void> updateRating(@PathVariable("id") UUID id, @RequestBody @Valid UpdateRatingDto body, @AuthenticationPrincipal UserInfo userInfo) {
    var criterionData = body.criteria().stream()
        .map(criterion -> new UpdateCriterionData(criterion.id(), criterion.type(), criterion.weight()))
        .collect(Collectors.toSet());
    var updateRatingData = new UpdateRatingData(body.name(), body.type(), criterionData);
    var params = new UpdateRatingParams(id, userInfo.getSub(), updateRatingData);

    updateRatingUseCase.execute(params);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteRating(@PathVariable("id") UUID ratingId, @AuthenticationPrincipal UserInfo userInfo) {
    var params = new DeleteUserRatingParams(ratingId, userInfo.getSub());
    deleteUserRatingUseCase.execute(params);

    return ResponseEntity.noContent().build();
  }
}
