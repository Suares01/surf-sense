package com.surfsense.api.infra.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.usecases.beach.DeleteBeachUseCase;
import com.surfsense.api.app.usecases.beach.DeleteBeachUseCase.DeleteBeachParams;
import com.surfsense.api.app.usecases.beach.EditUserBeachUseCase;
import com.surfsense.api.app.usecases.beach.EditUserBeachUseCase.EditBeachData;
import com.surfsense.api.app.usecases.beach.EditUserBeachUseCase.EditUserBeachParams;
import com.surfsense.api.app.usecases.beach.ListUserBeachesUseCase;
import com.surfsense.api.app.usecases.beach.ListUserBeachesUseCase.ListUserBeachesParams;
import com.surfsense.api.app.usecases.beach.SaveUserBeachUseCase;
import com.surfsense.api.app.usecases.beach.SaveUserBeachUseCase.BeachData;
import com.surfsense.api.app.usecases.beach.SaveUserBeachUseCase.SaveUserBeachParams;
import com.surfsense.api.infra.controllers.dtos.SaveBeachDto;
import com.surfsense.api.infra.services.Auth0Service.UserInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "beaches", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeachController extends BaseController {
  private final SaveUserBeachUseCase saveUserBeachUseCase;
  private final ListUserBeachesUseCase listUserBeachesUseCase;
  private final EditUserBeachUseCase editUserBeachUseCase;
  private final DeleteBeachUseCase deleteBeachUseCase;

  @GetMapping
  public ResponseEntity<List<Beach>> listUserBeaches(@AuthenticationPrincipal UserInfo userInfo) {
    var params = new ListUserBeachesParams(userInfo.getSub());
    List<Beach> beaches = listUserBeachesUseCase.execute(params);

    return ResponseEntity.ok(beaches);
  }

  @PostMapping
  public ResponseEntity<Void> saveBeach(@RequestBody @Valid SaveBeachDto body, @AuthenticationPrincipal UserInfo userInfo) {
    var beachData = new BeachData(body.name(), body.lat(), body.lng(), body.imageUrl(), body.position(), body.countryCode(), body.city());
    var params = new SaveUserBeachParams(beachData, userInfo.getSub());

    Beach beach = saveUserBeachUseCase.execute(params);

    return ResponseEntity.created(locationUri(beach.getId())).build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Void> editUserBeach(@PathVariable("id") UUID beachId, @RequestBody @Valid SaveBeachDto body, @AuthenticationPrincipal UserInfo userInfo) {
    var data = new EditBeachData(body.name(), body.lat(), body.lng(), body.imageUrl(), body.position(), body.countryCode(), body.city());
    var params = new EditUserBeachParams(beachId, userInfo.getSub(), data);
    editUserBeachUseCase.execute(params);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteUserBeach(@PathVariable("id") UUID beachId, @AuthenticationPrincipal UserInfo userInfo) {
    var params = new DeleteBeachParams(beachId, userInfo.getSub());
    deleteBeachUseCase.execute(params);

    return ResponseEntity.noContent().build();
  }
}
