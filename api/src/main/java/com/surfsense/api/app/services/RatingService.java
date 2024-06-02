package com.surfsense.api.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.criterion.Criterion;

public interface RatingService {
  Rating findById(UUID id);
  List<Rating> findByUser(String userId);
  List<Criterion> getRatingCriteria(UUID ratingId);
  List<Criterion> getRatingCriteria(Collection<UUID> ratingIds);
  Rating create(Rating rating, Collection<Criterion> criteria);
  void createAll(Map<Rating, Collection<Criterion>> ratingMap);
  void delete(UUID ratingId, Collection<Criterion> criteria);
  void update(Rating rating, Collection<Criterion> upsertCriteria, Collection<UUID> deleteCriteriaIds);
}
