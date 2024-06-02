package com.surfsense.api.infra.persistence.mappers;

import org.springframework.stereotype.Component;

import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.infra.persistence.entities.CriterionEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CriterionEntityMapper implements Mapper<CriterionEntity, Criterion> {

  private final RatingEntityMapper ratingMapper;

  @Override
  public CriterionEntity toEntity(Criterion domain) {
    return new CriterionEntity(
        domain.getId(),
        domain.getType(),
        domain.getWeight(),
        ratingMapper.toEntity(domain.getRating()),
        domain.getCreatedAt());
  }

  @Override
  public Criterion toDomain(CriterionEntity entity) {
    return new Criterion(
        entity.getId(),
        entity.getType(),
        entity.getWeight(),
        ratingMapper.toDomain(entity.getRating()),
        entity.getCreatedAt());
  }

}
