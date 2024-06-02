package com.surfsense.api.infra.configuration.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surfsense.api.infra.services.OpenMeteoForecastClient.MarineResponse;
import com.surfsense.api.infra.services.OpenMeteoForecastClient.WeatherResponse;

@Configuration
public class OpenMeteoClients {
  @FeignClient(name = "openMeteoMarine", url = "https://marine-api.open-meteo.com")
  public interface MarineClient {
    @GetMapping("/v1/marine")
    MarineResponse getMarineData(@RequestParam("latitude") double latitude,
        @RequestParam("longitude") double longitude,
        @RequestParam("hourly") String hourly,
        @RequestParam("daily") String daily,
        @RequestParam("timezone") String timezone);
  }

  @FeignClient(name = "openMeteoWeather", url = "https://api.open-meteo.com")
  public interface WeatherClient {
    @GetMapping("/v1/forecast")
    WeatherResponse getWeatherData(@RequestParam("latitude") double latitude,
        @RequestParam("longitude") double longitude,
        @RequestParam("hourly") String hourly,
        @RequestParam("daily") String daily,
        @RequestParam("timezone") String timezone);
  }
}
