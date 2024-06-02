package com.surfsense.api.infra.persistence.repositories;

import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.infra.persistence.entities.BeachEntity;
import com.surfsense.api.infra.persistence.jparepositories.JPABeachRepository;
import com.surfsense.api.infra.persistence.mappers.BeachEntityMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeachRepositoryImpl implements BeachRepository {
  private final JPABeachRepository beachRepository;
  private final BeachEntityMapper mapper;

  @Override
  public Beach create(Beach beach) {
    BeachEntity beacheEntity = beachRepository.save(mapper.toEntity(beach));
    return mapper.toDomain(beacheEntity);
  }

  @Override
  public Beach update(Beach beach) {
    BeachEntity beacheEntity = beachRepository.save(mapper.toEntity(beach));
    return mapper.toDomain(beacheEntity);
  }

  @Override
  public void delete(UUID id) {
    beachRepository.deleteById(id);
  }

  @Override
  public List<Beach> listByUser(String userId) {
    List<BeachEntity> beaches = beachRepository.findByUserId(userId);
    return beaches.stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Beach findById(UUID id) {
    BeachEntity beachEntity = beachRepository.findById(id)
        .orElse(null);

    return beachEntity == null ? null : mapper.toDomain(beachEntity);
  }

  @Override
  public long countByUser(String userId) {
    return beachRepository.countByUserId(userId);
  }

  @Override
  public boolean existsByCoordinates(Coordinates coordinates, String userId) {
    BeachEntity beachEntity = beachRepository.findByCoordinates(coordinates.latitude(), coordinates.longitude(), userId);
    return beachEntity == null ? false : true;
  }

  @Override
  public boolean existsByName(String name, String userId) {
    BeachEntity beachEntity = beachRepository.findByName(name, userId);
    return beachEntity == null ? false : true;
  }

}
