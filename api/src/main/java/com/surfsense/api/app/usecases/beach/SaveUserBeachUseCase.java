package com.surfsense.api.app.usecases.beach;

import java.time.Instant;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.entities.beach.CountryCode;
import com.surfsense.api.app.entities.beach.Location;
import com.surfsense.api.app.entities.beach.Position;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.ConflictException;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.usecases.UseCase;
import com.surfsense.api.app.usecases.beach.SaveUserBeachUseCase.SaveUserBeachParams;

public class SaveUserBeachUseCase implements UseCase<SaveUserBeachParams, Beach> {
  private final BeachRepository beachRepository;

  private static final int MAX_BEACHES = 5;

  public SaveUserBeachUseCase(BeachRepository beachRepository) {
    this.beachRepository = beachRepository;
  }

  @Override
  public Beach execute(SaveUserBeachParams params) throws ApiException {
    String userId = params.userId;
    BeachData data = params.data;

    long count = beachRepository.countByUser(userId);
    if (count == MAX_BEACHES)
      throw new ConflictException("Usuário já possui o limite de 5 praias.");

    var coordinates = new Coordinates(data.latitude, data.longitude);
    if (beachRepository.existsByCoordinates(coordinates, userId))
      throw new ConflictException("Usuário já possui uma praia com essas coordenadas.");

    if (beachRepository.existsByName(data.name, userId))
      throw new ConflictException("Usuário já possui uma praia com esse nome.");

    var location = new Location(data.countryCode, data.city);
    var beach = new Beach(null, data.name, data.imageUrl, userId, location, coordinates, data.position, Instant.now());

    return beachRepository.create(beach);
  }

  public static record SaveUserBeachParams(BeachData data, String userId) {
  }

  public static record BeachData(String name, Double latitude, Double longitude, String imageUrl, Position position, CountryCode countryCode, String city) {
  }
}
