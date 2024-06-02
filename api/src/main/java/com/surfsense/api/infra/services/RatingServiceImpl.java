package com.surfsense.api.infra.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.repositories.CriterionRepository;
import com.surfsense.api.app.repositories.RatingRepository;
import com.surfsense.api.app.services.RatingService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
  private final RatingRepository ratingRepository;
  private final CriterionRepository criterionRepository;

  @Override
  public void createAll(Map<Rating, Collection<Criterion>> ratingMap) {
    var createdRatings = ratingRepository.createAll(ratingMap.keySet());

    Map<String, Rating> createdRatingsMap = createdRatings.stream()
        .collect(Collectors.toMap(Rating::getName, rating -> rating));

    List<Criterion> criteria = ratingMap.entrySet().stream()
        .flatMap(entry -> {
          Rating createdRating = createdRatingsMap.get(entry.getKey().getName());
          return entry.getValue().stream().map(criterion -> {
            criterion.setRating(createdRating);
            return criterion;
          });
        })
        .collect(Collectors.toList());

    criterionRepository.createAll(criteria);
  }

  @Override
  public Rating create(Rating rating, Collection<Criterion> criteria) {
    Rating createdRating = ratingRepository.create(rating);
    criterionRepository.createAll(criteria.stream()
        .map(criterion -> {
          criterion.setRating(createdRating);
          return criterion;
        })
        .toList());
    return createdRating;
  }

  @Override
  public void delete(UUID ratingId, Collection<Criterion> criteria) {
    criterionRepository.deleteAll(criteria);
    ratingRepository.delete(ratingId);
  }

  @Override
  public void update(Rating rating, Collection<Criterion> upsertCriteria, Collection<UUID> deleteCriteriaIds) {
    criterionRepository.deleteAllByIds(deleteCriteriaIds);
    ratingRepository.update(rating);
    criterionRepository.upsertAll(upsertCriteria);
  }

  @Override
  public Rating findById(UUID id) {
    return ratingRepository.findById(id);
  }

  @Override
  public List<Criterion> getRatingCriteria(UUID ratingId) {
    return criterionRepository.findByRating(ratingId);
  }

  @Override
  public List<Rating> findByUser(String userId) {
    return ratingRepository.findByUser(userId);
  }

  @Override
  public List<Criterion> getRatingCriteria(Collection<UUID> ratingIds) {
    return criterionRepository.findByRating(ratingIds);
  }

}
