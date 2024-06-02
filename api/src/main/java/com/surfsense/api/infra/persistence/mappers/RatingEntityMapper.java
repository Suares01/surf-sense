package com.surfsense.api.infra.persistence.mappers;

import org.springframework.stereotype.Component;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.infra.persistence.entities.RatingEntity;

import jakarta.validation.constraints.NotNull;

@Component
public class RatingEntityMapper implements Mapper<RatingEntity, Rating> {

  @Override
  public @NotNull RatingEntity toEntity(@NotNull Rating domain) {
    return new RatingEntity(
        domain.getId(),
        domain.getName(),
        domain.getType(),
        domain.getUserId(),
        domain.getCreatedAt());
  }

  @Override
  public @NotNull Rating toDomain(@NotNull RatingEntity entity) {
    return new Rating(
        entity.getId(),
        entity.getName(),
        entity.getType(),
        entity.getUserId(),
        entity.getCreatedAt());
  }

}
