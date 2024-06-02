package com.surfsense.api.infra.services;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.client.RestClientException;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.ClientException;
import com.surfsense.api.app.services.ForecastClient;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.DailyForecast;
import com.surfsense.api.app.services.ForecastClient.ForecastPoint.HourlyForecast;
import com.surfsense.api.infra.configuration.http.OpenMeteoClients.MarineClient;
import com.surfsense.api.infra.configuration.http.OpenMeteoClients.WeatherClient;

import jakarta.annotation.Resource;

public class OpenMeteoForecastClient implements ForecastClient {
  private static Logger logger = LoggerFactory.getLogger(OpenMeteoForecastClient.class);

  @Resource(name = "redisTemplate")
  private ValueOperations<UUID, ForecastPoint> cache;

  @Autowired
  private MarineClient marineClient;

  @Autowired
  private WeatherClient weatherClient;

  private static final String MARINE_HOURLY = "wave_height,wave_direction,wave_period,swell_wave_height,swell_wave_direction,swell_wave_period";
  private static final String MARINE_DAILY = "wave_height_max,wave_direction_dominant,swell_wave_height_max,swell_wave_direction_dominant";

  private static final String WEATHER_HOURLY = "temperature_2m,visibility,windspeed_10m,winddirection_10m";
  private static final String WEATHER_DAILY = "temperature_2m_max,temperature_2m_min,precipitation_probability_max";

  @Override
  public ForecastPoint fetchPoints(Beach beach, String timezone) throws ApiException {
    ForecastPoint forecastPoint = getForecastsFromCache(beach.getId());

    if (forecastPoint == null) {
      ForecastPoint forecastsFromApi = getForecastsFromApi(beach, timezone);
      cacheForecast(beach.getId(), forecastsFromApi);
      return forecastsFromApi;
    }

    return forecastPoint;
  }

  private ForecastPoint getForecastsFromCache(UUID key) {
    ForecastPoint point = cache.get(key);

    if (point != null)
      logger.info("Fetching data from cahce with key {}", key);

    return point;
  }

  private ForecastPoint getForecastsFromApi(Beach beach, String timezone) {
    try {
      logger.info("Fetching data from OpenMeteo API");

      Coordinates coordinates = beach.getCoordinates();

      CompletableFuture<MarineResponse> marineFuture = CompletableFuture.supplyAsync(
          () -> marineClient.getMarineData(coordinates.latitude(), coordinates.longitude(), MARINE_HOURLY, MARINE_DAILY, timezone));

      CompletableFuture<WeatherResponse> weatherFuture = CompletableFuture.supplyAsync(
          () -> weatherClient.getWeatherData(coordinates.latitude(), coordinates.longitude(), WEATHER_HOURLY, WEATHER_DAILY, timezone));

      MarineResponse marineResponse = marineFuture.get();
      WeatherResponse weatherResponse = weatherFuture.get();

      return mapToForecastPoint(beach.getId(), marineResponse, weatherResponse);
    } catch (RestClientException | InterruptedException | ExecutionException exception) {
      ClientException clientException = new ClientException(exception.getMessage());
      logger.error("Error fetching data from OpenMeteo API", clientException);
      throw clientException;
    }
  }

  private ForecastPoint mapToForecastPoint(UUID beachId, MarineResponse marineResponse, WeatherResponse weatherResponse) {
    return new ForecastPoint(
        beachId,
        marineResponse.latitude,
        marineResponse.longitude,
        mapToHourlyForecasts(marineResponse.hourly, weatherResponse.hourly),
        mapToDailyForecasts(marineResponse.daily, weatherResponse.daily));
  }

  private List<DailyForecast> mapToDailyForecasts(MarineDaily marineDaily, WeatherDaily weatherDaily) {
    return IntStream.range(0, marineDaily.time.length)
        .parallel()
        .mapToObj(index -> new DailyForecast(
            marineDaily.time[index],
            marineDaily.wave_height_max[index],
            marineDaily.wave_direction_dominant[index],
            marineDaily.swell_wave_height_max[index],
            marineDaily.swell_wave_direction_dominant[index],
            weatherDaily.temperature_2m_max[index],
            weatherDaily.temperature_2m_min[index],
            weatherDaily.precipitation_probability_max[index]))
        .toList();
  }

  private List<HourlyForecast> mapToHourlyForecasts(MarineHourly marineHourly, WeatherHourly weatherHourly) {
    return IntStream.range(0, marineHourly.time.length)
        .parallel()
        .mapToObj(index -> new HourlyForecast(
            marineHourly.time[index],
            marineHourly.wave_height[index],
            marineHourly.wave_direction[index],
            marineHourly.wave_period[index],
            marineHourly.swell_wave_height[index],
            marineHourly.swell_wave_direction[index],
            marineHourly.swell_wave_period[index],
            weatherHourly.temperature_2m[index],
            weatherHourly.visibility[index],
            weatherHourly.windspeed_10m[index],
            weatherHourly.winddirection_10m[index],
            null))
        .toList();
  }

  private void cacheForecast(UUID beachId, ForecastPoint forecast) {
    logger.info("Save forecast point in cahce with key {}", beachId);
    cache.set(beachId, forecast, Duration.ofHours(1));
  }



  public static class MarineResponse {
    public double latitude;
    public double longitude;
    public String timezone;
    public MarineHourly hourly;
    public MarineDaily daily;
  }

  public static class MarineHourly {
    public String[] time;
    public double[] wave_height;
    public int[] wave_direction;
    public double[] wave_period;
    public double[] swell_wave_height;
    public int[] swell_wave_direction;
    public double[] swell_wave_period;
  }

  public static class MarineDaily {
    public String[] time;
    public double[] wave_height_max;
    public int[] wave_direction_dominant;
    public double[] swell_wave_height_max;
    public int[] swell_wave_direction_dominant;
  }

  public static class WeatherResponse {
    public double latitude;
    public double longitude;
    public String timezone;
    public WeatherHourly hourly;
    public WeatherDaily daily;
  }

  public static class WeatherHourly {
    public String[] time;
    public double[] temperature_2m;
    public double[] visibility;
    public double[] windspeed_10m;
    public int[] winddirection_10m;
  }

  public static class WeatherDaily {
    public String[] time;
    public double[] temperature_2m_max;
    public double[] temperature_2m_min;
    public int[] precipitation_probability_max;
  }
}
