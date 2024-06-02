package com.surfsense.api.app.errors;

import org.springframework.http.HttpStatus;

import com.surfsense.api.app.utils.RatingsCriteriaMapper.CriterionWithoutRating;

public class InvalidCriterionException extends ApiException {

  public InvalidCriterionException(CriterionWithoutRating criterion) {
    super(criterion.getType() + " is invalid", HttpStatus.BAD_REQUEST);
  }

}
