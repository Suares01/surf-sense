package com.surfsense.api.app.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.criterion.Criterion;

public interface CriterionRepository {
  List<Criterion> findByRating(UUID ratingId);
  List<Criterion> findByRating(Collection<UUID> ratingIds);
  Set<Criterion> createAll(Collection<Criterion> criteria);
  void upsertAll(Collection<Criterion> criteria);
  void deleteAllByIds(Collection<UUID> ids);
  void deleteAll(Collection<Criterion> criteria);
}
