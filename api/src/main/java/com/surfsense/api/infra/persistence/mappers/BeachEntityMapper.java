package com.surfsense.api.infra.persistence.mappers;

import org.springframework.stereotype.Component;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.entities.beach.Location;
import com.surfsense.api.infra.persistence.entities.BeachEntity;

import jakarta.validation.constraints.NotNull;

@Component
public class BeachEntityMapper implements Mapper<BeachEntity, Beach> {
  @Override
  public BeachEntity toEntity(@NotNull Beach domain) {
    return new BeachEntity(
        domain.getId(),
        domain.getName(),
        domain.getUserId(),
        domain.getPosition(),
        domain.getCoordinates().latitude(),
        domain.getCoordinates().longitude(),
        domain.getLocation().countryCode(),
        domain.getLocation().city(),
        domain.getImageUrl(),
        domain.getCreatedAt());
  }

  @Override
  public Beach toDomain(@NotNull BeachEntity entity) {
    var location = new Location(entity.getCountryCode(), entity.getCity());
    var coordinates = new Coordinates(entity.getLatitude(), entity.getLongitude());
    return new Beach(
        entity.getId(),
        entity.getName(),
        entity.getImageUrl(),
        entity.getUserId(),
        location,
        coordinates,
        entity.getPosition(),
        entity.getCreatedAt());
  }
}
