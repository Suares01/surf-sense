package com.surfsense.api.app.utils;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;

public class RatingsCriteriaMapper {
  public static RatingWithCriteria map(Rating rating, Collection<Criterion> criteria) {
    List<CriterionWithoutRating> ratingCriteria = criteria.stream()
        .map(criterion -> new CriterionWithoutRating(
            criterion.getId(),
            criterion.getType(),
            criterion.getWeight(),
            criterion.getCreatedAt()))
        .toList();

    return new RatingWithCriteria(
        rating.getId(),
        rating.getName(),
        rating.getType(),
        rating.getUserId(),
        rating.getCreatedAt(),
        ratingCriteria);
  }

  public static class CriterionWithoutRating {
    private UUID id;
    private CriterionType type;
    private Float weight;
    private Instant createdAt;

    public CriterionWithoutRating(UUID id, CriterionType type, Float weight, Instant createdAt) {
      this.id = id;
      this.type = type;
      this.weight = weight;
      this.createdAt = createdAt;
    }

    public UUID getId() {
      return id;
    }

    public CriterionType getType() {
      return type;
    }

    public Float getWeight() {
      return weight;
    }

    public Instant getCreatedAt() {
      return createdAt;
    }

  }

  public static class RatingWithCriteria extends Rating {
    private List<CriterionWithoutRating> criteria;

    public RatingWithCriteria(UUID id, String name, RatingType type, String userId, Instant createdAt,
        List<CriterionWithoutRating> criteria) {
      super(id, name, type, userId, createdAt);
      this.criteria = criteria;
    }

    public List<CriterionWithoutRating> getCriteria() {
      return criteria;
    }
  }
}
