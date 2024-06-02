package com.surfsense.api.app.repositories;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;

public interface RatingRepository {
  Rating create(Rating rating);
  List<Rating> createAll(Collection<Rating> ratings);
  Rating update(Rating rating);
  void delete(UUID id);
  List<Rating> findByUser(String userId);
  Rating findById(UUID id);
}
