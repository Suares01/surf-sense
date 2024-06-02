package com.surfsense.api.app.usecases.beach;

import java.util.UUID;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.entities.beach.CountryCode;
import com.surfsense.api.app.entities.beach.Location;
import com.surfsense.api.app.entities.beach.Position;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.ConflictException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.usecases.beach.EditUserBeachUseCase.EditUserBeachParams;
import com.surfsense.api.app.usecases.UseCase;

public class EditUserBeachUseCase implements UseCase<EditUserBeachParams, Beach> {
  private final BeachRepository beachRepository;

  public EditUserBeachUseCase(BeachRepository beachRepository) {
    this.beachRepository = beachRepository;
  }

  @Override
  public Beach execute(EditUserBeachParams params) throws ApiException {
    Beach beach = beachRepository.findById(params.beachId);

    if (beach == null)
      throw new NotFoundException("Praia não encontrada");

    String userId = params.userId();

    if (!beach.getUserId().equals(userId))
      throw new BadRequestException("Praia não pertence a esse usuário");

    EditBeachData data = params.data();

    if (!beach.getName().equals(data.name()) && beachRepository.existsByName(data.name(), userId))
      throw new ConflictException("Usuário já tem uma praia com esse nome");

    var newCoordinates = new Coordinates(data.latitude, data.longitude);
    var currentCoordinates = beach.getCoordinates();

    if (!currentCoordinates.latitude().equals(data.latitude) && !currentCoordinates.longitude().equals(data.longitude) && beachRepository.existsByCoordinates(newCoordinates, userId))
      throw new ConflictException("Usuário já tem uma praia com essa latitude e longitude.");

    var newLocation = new Location(data.countryCode, data.city);
    var updatedBeach = new Beach(beach.getId(), data.name, data.imageUrl, userId, newLocation, newCoordinates, data.position, beach.getCreatedAt());

    return beachRepository.update(updatedBeach);
  }

  public static record EditUserBeachParams(UUID beachId, String userId, EditBeachData data) {
  }

  public static record EditBeachData(String name, Double latitude, Double longitude, String imageUrl, Position position, CountryCode countryCode, String city) {
  }
}
