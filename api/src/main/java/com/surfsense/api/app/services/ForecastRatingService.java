package com.surfsense.api.app.services;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.HourlyForecast;
import com.surfsense.api.app.utils.RatingsCriteriaMapper.RatingWithCriteria;

public interface ForecastRatingService {
  public int evaluateConditions(HourlyForecast hourlyForecast, Beach beach, RatingWithCriteria rating) throws ApiException;
}
