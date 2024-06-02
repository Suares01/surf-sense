package com.surfsense.api.infra.persistence.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.repositories.CriterionRepository;
import com.surfsense.api.infra.persistence.entities.CriterionEntity;
import com.surfsense.api.infra.persistence.jparepositories.JPACriterionRepository;
import com.surfsense.api.infra.persistence.mappers.CriterionEntityMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriterionRepositoryImpl implements CriterionRepository {

  private final JPACriterionRepository criterionRepository;
  private final CriterionEntityMapper mapper;

  @Override
  public Set<Criterion> createAll(Collection<Criterion> criteria) {
    Collection<CriterionEntity> entities = criteria.stream()
        .map(mapper::toEntity)
        .toList();

    List<CriterionEntity> createdEntities = criterionRepository.saveAll(entities);

    return createdEntities.stream().map(mapper::toDomain).collect(Collectors.toSet());
  }

  @Override
  public List<Criterion> findByRating(UUID ratingId) {
    return criterionRepository.findByRating(ratingId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Criterion> findByRating(Collection<UUID> ratingIds) {
    return criterionRepository.findByRating(ratingIds).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public void upsertAll(Collection<Criterion> criteria) {
    Collection<CriterionEntity> entities = criteria.stream()
        .map(mapper::toEntity)
        .toList();

    criterionRepository.saveAll(entities);
  }

  @Override
  public void deleteAll(Collection<Criterion> criteria) {
    Collection<CriterionEntity> entities = criteria.stream()
        .map(mapper::toEntity)
        .toList();

    criterionRepository.deleteAll(entities);
  }

  @Override
  public void deleteAllByIds(Collection<UUID> ids) {
    criterionRepository.deleteAllById(ids);
  }

}
