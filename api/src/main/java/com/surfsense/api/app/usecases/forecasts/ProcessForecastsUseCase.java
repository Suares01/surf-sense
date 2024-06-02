package com.surfsense.api.app.usecases.forecasts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.InternalException;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.DailyForecast;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.HourlyForecast;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.HourlyRating;
import com.surfsense.api.app.services.ForecastClient;
import com.surfsense.api.app.services.ForecastRatingService;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.UseCase;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase.ProcessForecastsParams;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase.TimeForecast;
import com.surfsense.api.app.utils.RatingsCriteriaMapper;
import com.surfsense.api.app.utils.RatingsCriteriaMapper.RatingWithCriteria;

public class ProcessForecastsUseCase implements UseCase<ProcessForecastsParams, List<TimeForecast>> {
  private final BeachRepository beachRepository;
  private final RatingService ratingService;
  private final ForecastClient client;
  private final ForecastRatingService forecastRatingService;

  public ProcessForecastsUseCase(BeachRepository beachRepository, RatingService ratingService, ForecastClient client, ForecastRatingService forecastRatingService) {
    this.beachRepository = beachRepository;
    this.ratingService = ratingService;
    this.client = client;
    this.forecastRatingService = forecastRatingService;
  }

  @Override
  public List<TimeForecast> execute(ProcessForecastsParams params) throws ApiException {
    List<Beach> beaches = getUserBeaches(params.userId());

    if (beaches.isEmpty())
      return List.of();

    List<RatingWithCriteria> ratings = getUseRatings(params.userId());
    List<ForecastPoint> forecastPoints = getForecastPoints(beaches, params.timezone());
    List<Forecast> forecasts = processForecastPoints(forecastPoints, beaches, ratings);
    return mapForecastByDate(forecasts);
  }

  private List<Beach> getUserBeaches(String userId) {
    return beachRepository.listByUser(userId);
  }

  private List<RatingWithCriteria> getUseRatings(String userId) {
    List<Rating> ratings = ratingService.findByUser(userId);
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

  private List<ForecastPoint> getForecastPoints(List<Beach> beaches, String timezone) {
    try (final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
      List<Callable<ForecastPoint>> tasks = beaches.stream()
          .map(beach -> (Callable<ForecastPoint>) () -> client.fetchPoints(beach, timezone))
          .toList();

      List<Future<ForecastPoint>> futures = executorService.invokeAll(tasks);

      List<ForecastPoint> points = futures.stream()
          .map(future -> {
            try {
              return future.get();
            } catch (InterruptedException | ExecutionException exception) {
              throw new InternalException(exception.getMessage());
            }
          })
          .toList();

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

      return points;
    } catch (InterruptedException exception) {
      throw new InternalException(exception.getMessage());
    }
  }

  private List<Forecast> processForecastPoints(List<ForecastPoint> points, List<Beach> beaches, List<RatingWithCriteria> ratings) {
    return points.stream()
        .flatMap(point -> {
          Optional<Beach> beach = beaches.stream()
              .filter(b -> b.getId().equals(point.getBeachId()))
              .findFirst();

          if (beach.isPresent()) {
            return point.getDaily().stream()
                .map(daily -> {
                  LocalDate dailyDate = LocalDate.parse(daily.getDate());

                  List<HourlyForecast> hourlyForecasts = filterHourlyByDate(point.getHourly(), dailyDate)
                      .map(hourly -> calculateHourlyForecastRating(hourly, beach.get(), ratings))
                      .toList();

                  return buildForecast(beach.get(), daily, dailyDate, hourlyForecasts);
                });
          } else {
            throw new InternalException("An unexpected error occurred");
          }
        })
        .toList();
  }

  private Forecast buildForecast(Beach beach, DailyForecast daily, LocalDate date, List<HourlyForecast> hourly) {
    return new Forecast(date, beach, daily, hourly);
  }

  private Stream<HourlyForecast> filterHourlyByDate(List<HourlyForecast> hourlyForecasts, LocalDate date) {
    return hourlyForecasts.stream()
        .filter(hourly -> {
          LocalDate hourlyDate = LocalDateTime.parse(hourly.getTime()).toLocalDate();

          return hourlyDate.equals(date);
        });
  }

  private HourlyForecast calculateHourlyForecastRating(HourlyForecast hourly, Beach beach, List<RatingWithCriteria> ratings) {
    List<HourlyRating> hourlyRatings = ratings.stream()
        .map(rating -> {
          int result = forecastRatingService.evaluateConditions(hourly, beach, rating);
          return new HourlyRating(rating.getName(), rating.getType(), result);
        })
        .toList();

    hourly.setRatings(hourlyRatings);
    return hourly;
  }

  private List<TimeForecast> mapForecastByDate(List<Forecast> beachForecasts) {
    Map<LocalDate, List<Forecast>> forecastMap = beachForecasts.stream()
        .collect(Collectors.groupingBy(Forecast::getDate));

    TreeMap<LocalDate, List<Forecast>> sortedForecastMap = new TreeMap<>(forecastMap);

    return sortedForecastMap.entrySet().stream()
        .map(entry -> new TimeForecast(entry.getKey(), entry.getValue()))
        .toList();
  }

  public static record ProcessForecastsParams(String userId, String timezone) {
  }

  public static class Forecast {
    private LocalDate date;
    private Beach beach;
    private DailyForecast daily;
    private List<HourlyForecast> hourly;

    public Forecast(LocalDate date, Beach beach, DailyForecast daily, List<HourlyForecast> hourly) {
      this.date = date;
      this.beach = beach;
      this.daily = daily;
      this.hourly = hourly;
    }

    public LocalDate getDate() {
      return date;
    }

    public Beach getBeach() {
      return beach;
    }

    public DailyForecast getDaily() {
      return daily;
    }

    public List<HourlyForecast> getHourly() {
      return hourly;
    }
  }

  public static class TimeForecast {
    private LocalDate date;
    private List<Forecast> forecasts;

    public TimeForecast(LocalDate date, List<Forecast> forecasts) {
      this.date = date;
      this.forecasts = forecasts;
    }

    public LocalDate getDate() {
      return date;
    }

    public List<Forecast> getForecasts() {
      return forecasts;
    }
  }
}
