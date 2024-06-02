package com.surfsense.api.app.services;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.errors.ApiException;

public interface ForecastClient {
  public ForecastPoint fetchPoints(Beach beach, String timezone) throws ApiException;

  public static class ForecastPoint implements Serializable {
    public static class HourlyRating {
      private String name;
      private RatingType type;
      private int value;

      public HourlyRating(String name, RatingType type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
      }

      public String getName() {
        return name;
      }

      public RatingType getType() {
        return type;
      }

      public int getValue() {
        return value;
      }
    }

    public static class HourlyForecast implements Serializable {
      private String time;
      private double waveHeight;
      private int waveDirection;
      private double wavePeriod;
      private double swellWaveHeight;
      private int swellWaveDirection;
      private double swellWavePeriod;
      private double temperature;
      private double visibility;
      private double windSpeed;
      private int windDirection;
      private List<HourlyRating> ratings;

      public HourlyForecast(String time, double waveHeight, int waveDirection, double wavePeriod,
          double swellWaveHeight, int swellWaveDirection, double swellWavePeriod, double temperature, double visibility,
          double windSpeed, int windDirection, List<HourlyRating> ratings) {
        this.time = time;
        this.waveHeight = waveHeight;
        this.waveDirection = waveDirection;
        this.wavePeriod = wavePeriod;
        this.swellWaveHeight = swellWaveHeight;
        this.swellWaveDirection = swellWaveDirection;
        this.swellWavePeriod = swellWavePeriod;
        this.temperature = temperature;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.ratings = ratings;
      }

      public String getTime() {
        return time;
      }

      public double getWaveHeight() {
        return waveHeight;
      }

      public int getWaveDirection() {
        return waveDirection;
      }

      public double getWavePeriod() {
        return wavePeriod;
      }

      public double getSwellWaveHeight() {
        return swellWaveHeight;
      }

      public int getSwellWaveDirection() {
        return swellWaveDirection;
      }

      public double getSwellWavePeriod() {
        return swellWavePeriod;
      }

      public double getTemperature() {
        return temperature;
      }

      public double getVisibility() {
        return visibility;
      }

      public double getWindSpeed() {
        return windSpeed;
      }

      public int getWindDirection() {
        return windDirection;
      }

      public List<HourlyRating> getRatings() {
        return ratings;
      }

      public void setRatings(List<HourlyRating> ratings) {
        this.ratings = ratings;
      }
    }

    public static class DailyForecast implements Serializable {
      private String date;
      private double waveHeightMax;
      private int waveDirectionDominant;
      private double swellWaveHeightMax;
      private int swellWaveDirectionDominant;
      private double temperatureMax;
      private double temperatureMin;
      private int precipitationProbability;

      public DailyForecast(String date, double waveHeightMax, int waveDirectionDominant, double swellWaveHeightMax,
          int swellWaveDirectionDominant, double temperatureMax, double temperatureMin, int precipitationProbability) {
        this.date = date;
        this.waveHeightMax = waveHeightMax;
        this.waveDirectionDominant = waveDirectionDominant;
        this.swellWaveHeightMax = swellWaveHeightMax;
        this.swellWaveDirectionDominant = swellWaveDirectionDominant;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.precipitationProbability = precipitationProbability;
      }

      public String getDate() {
        return date;
      }

      public double getWaveHeightMax() {
        return waveHeightMax;
      }

      public int getWaveDirectionDominant() {
        return waveDirectionDominant;
      }

      public double getSwellWaveHeightMax() {
        return swellWaveHeightMax;
      }

      public int getSwellWaveDirectionDominant() {
        return swellWaveDirectionDominant;
      }

      public double getTemperatureMax() {
        return temperatureMax;
      }

      public double getTemperatureMin() {
        return temperatureMin;
      }

      public int getPrecipitationProbability() {
        return precipitationProbability;
      }
    }

    private UUID beachId;
    private double latitude;
    private double longitude;
    private List<HourlyForecast> hourly;
    private List<DailyForecast> daily;

    public ForecastPoint(UUID beachId, double latitude, double longitude, List<HourlyForecast> hourly,
        List<DailyForecast> daily) {
      this.beachId = beachId;
      this.latitude = latitude;
      this.longitude = longitude;
      this.hourly = hourly;
      this.daily = daily;
    }

    public UUID getBeachId() {
      return beachId;
    }

    public double getLatitude() {
      return latitude;
    }

    public double getLongitude() {
      return longitude;
    }

    public List<HourlyForecast> getHourly() {
      return hourly;
    }

    public List<DailyForecast> getDaily() {
      return daily;
    }
  }
}
