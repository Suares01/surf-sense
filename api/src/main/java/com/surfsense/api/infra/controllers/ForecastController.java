package com.surfsense.api.infra.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase.ProcessForecastsParams;
import com.surfsense.api.app.usecases.forecasts.ProcessForecastsUseCase.TimeForecast;
import com.surfsense.api.infra.services.Auth0Service.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "forecasts", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ForecastController {
  private final ProcessForecastsUseCase processForecastsUseCase;

  @GetMapping
  public ResponseEntity<List<TimeForecast>> getMethodName(@AuthenticationPrincipal UserInfo userInfo) {
    var request = new ProcessForecastsParams(userInfo.getSub(), userInfo.getZoneinfo());
    var forecasts = processForecastsUseCase.execute(request);

    return ResponseEntity.ok(forecasts);
  }
}
