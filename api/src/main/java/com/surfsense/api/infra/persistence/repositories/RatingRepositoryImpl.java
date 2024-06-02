package com.surfsense.api.infra.persistence.repositories;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.repositories.RatingRepository;
import com.surfsense.api.infra.persistence.entities.RatingEntity;
import com.surfsense.api.infra.persistence.jparepositories.JPARatingRepository;
import com.surfsense.api.infra.persistence.mappers.RatingEntityMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatingRepositoryImpl implements RatingRepository {
  private final JPARatingRepository ratingRepository;
  private final RatingEntityMapper mapper;

  @Override
  public Rating create(Rating rating) {
    RatingEntity ratingEntity = ratingRepository.save(mapper.toEntity(rating));
    return mapper.toDomain(ratingEntity);
  }

  @Override
  public Rating update(Rating rating) {
    RatingEntity ratingEntity = ratingRepository.save(mapper.toEntity(rating));
    return mapper.toDomain(ratingEntity);
  }

  @Override
  public void delete(UUID id) {
    ratingRepository.deleteById(id);
  }

  @Override
  public List<Rating> findByUser(String userId) {
    List<RatingEntity> ratingEntities = ratingRepository.findByUserId(userId);
    return ratingEntities.stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Rating findById(UUID id) {
    RatingEntity ratingEntity = ratingRepository.findById(id)
        .orElse(null);

    return ratingEntity == null ? null : mapper.toDomain(ratingEntity);
  }

  @Override
  public List<Rating> createAll(Collection<Rating> ratings) {
    Collection<RatingEntity> ratingEntities = ratings.stream()
        .map(mapper::toEntity)
        .toList();

    return ratingRepository.saveAll(ratingEntities)
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

}
