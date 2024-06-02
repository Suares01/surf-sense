package com.surfsense.api.app.usecases.forecasts;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.surfsense.api.Factories;
import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.services.ForecastClient;
import com.surfsense.api.app.services.ForecastRatingService;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.DailyForecast;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.HourlyForecast;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.HourlyRating;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase.Forecast;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase.TimeForecast;

@ExtendWith(MockitoExtension.class)
public class ProcessForecastsUseCaseTest {
  @Mock
  private BeachRepository beachRepository;

  @Mock
  private RatingService ratingService;

  @Mock
  private ForecastClient client;

  @Mock
  private ForecastRatingService forecastRatingService;

  @InjectMocks
  private ProcessForecastsUseCase processForecastsUseCase;

  private static final String USER_ID = "user-id";
  private static final String TIMEZONE = "America/Sao_Paulo";

  @Test
  void shouldReturnEmptyList_whenUserHasNoBeaches() throws ApiException {
    when(beachRepository.listByUser(USER_ID)).thenReturn(List.of());

    List<TimeForecast> result = processForecastsUseCase.execute(buildForecastsParams());

    assertTrue(result.isEmpty());
    verify(beachRepository, times(1)).listByUser(USER_ID);
    verifyNoInteractions(ratingService, client, forecastRatingService);
  }

  @Test
  void shouldReturnForecasts_whenUserHasBeaches() throws ApiException {
    var beach = Factories.buildBeach();
    var rating = Factories.buildRating(RatingType.SURFER);
    var criteria = buildCriteria(rating);
    var forecastPoint = buildForecastPoint(beach);
    rating.setUserId(USER_ID);
    beach.setUserId(USER_ID);

    when(beachRepository.listByUser(USER_ID)).thenReturn(List.of(beach));
    when(ratingService.findByUser(USER_ID)).thenReturn(List.of(rating));
    when(ratingService.getRatingCriteria(List.of(rating.getId()))).thenReturn(criteria);
    when(client.fetchPoints(beach, TIMEZONE)).thenReturn(forecastPoint);
    when(forecastRatingService.evaluateConditions(any(), any(), any())).thenReturn(4);

    List<TimeForecast> result = processForecastsUseCase.execute(buildForecastsParams());

    TimeForecast timeForecast = result.get(0);
    assertEquals(1, timeForecast.getForecasts().size());

    Forecast forecast = timeForecast.getForecasts().get(0);
    assertEquals(beach, forecast.getBeach());
    assertNotNull(forecast.getDaily());
    assertFalse(forecast.getHourly().isEmpty());

    HourlyForecast hourlyForecast = forecast.getHourly().get(0);
    assertFalse(hourlyForecast.getRatings().isEmpty());
    assertEquals(1, hourlyForecast.getRatings().size());

    HourlyRating hourlyRating = hourlyForecast.getRatings().get(0);
    assertEquals(rating.getName(), hourlyRating.getName());
    assertEquals(rating.getType(), hourlyRating.getType());
    assertEquals(4, hourlyRating.getValue());

    verify(beachRepository, times(1)).listByUser(USER_ID);
    verify(ratingService, times(1)).findByUser(USER_ID);
    verify(ratingService, times(1)).getRatingCriteria(anyList());
    verify(client, times(1)).fetchPoints(beach, TIMEZONE);
    verify(forecastRatingService, times(1)).evaluateConditions(any(), any(), any());
  }

  private ProcessForecastsUseCase.ProcessForecastsParams buildForecastsParams() {
    return new ProcessForecastsUseCase.ProcessForecastsParams(USER_ID, TIMEZONE);
  }

  private List<Criterion> buildCriteria(Rating rating) {
    return List.of(
        Factories.buildCriterion(CriterionType.WAVE_HEIGHT, 0.8F, rating),
        Factories.buildCriterion(CriterionType.WAVE_DIRECTION, 0.9F, rating),
        Factories.buildCriterion(CriterionType.WAVE_PERIOD, 0.6F, rating),
        Factories.buildCriterion(CriterionType.SWELL_HEIGHT, 0.7F, rating),
        Factories.buildCriterion(CriterionType.SWELL_DIRECTION, 0.9F, rating),
        Factories.buildCriterion(CriterionType.SWELL_PERIOD, 0.6F, rating),
        Factories.buildCriterion(CriterionType.WIND_DIRECTION, 0.5F, rating));
  }

  private DailyForecast buildDailyForecast() {
    return new DailyForecast(
        "2024-05-28",
        1.68,
        150,
        1.68,
        150,
        30.7,
        20.3,
        100);
  }

  private HourlyForecast buildHourlyForecast() {
    return new HourlyForecast(
        "2024-05-28T00:00",
        1.68,
        153,
        9.35,
        1.68,
        153,
        9.35,
        21.2,
        24120.0,
        0.8,
        207,
        null);
  }

  private ForecastPoint buildForecastPoint(Beach beach) {
    return new ForecastPoint(
        beach.getId(),
        beach.getCoordinates().latitude(),
        beach.getCoordinates().longitude(),
        List.of(buildHourlyForecast()),
        List.of(buildDailyForecast()));
  }
}
