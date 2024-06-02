package com.surfsense.api.infra.configuration.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.services.ForecastClient;
import com.surfsense.api.app.services.ForecastRatingService;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase;
import com.surfsense.api.infra.services.ForecastRatingServiceImpl;
import com.surfsense.api.infra.services.OpenMeteoForecastClient;

@Configuration
public class ForecastsConfig {
  @Bean
  ForecastClient forecastClient() {
    return new OpenMeteoForecastClient();
  }

  @Bean
  ForecastRatingService forecastRatingService() {
    return new ForecastRatingServiceImpl();
  }

  @Bean
  ProcessForecastsUseCase processForecastsUseCase(BeachRepository beachRepository, RatingService ratingService, ForecastClient forecastClient, ForecastRatingService forecastRatingService) {
    return new ProcessForecastsUseCase(beachRepository, ratingService, forecastClient, forecastRatingService);
  }
}
