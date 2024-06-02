package com.surfsense.api.app.entities.rating.criterion;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.surfsense.api.app.entities.rating.Rating;

public class Criterion {
  private UUID id;
  private CriterionType type;
  private Float weight;
  private Rating rating;
  private Instant createdAt;

  public Criterion() {
  }

  public Criterion(CriterionType type, Float weight, Rating rating, Instant createdAt) {
    this.type = type;
    this.weight = weight;
    this.rating = rating;
    this.createdAt = createdAt;
  }

  public Criterion(UUID id, CriterionType type, Float weight, Rating rating, Instant createdAt) {
    this.id = id;
    this.type = type;
    this.weight = weight;
    this.rating = rating;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CriterionType getType() {
    return type;
  }

  public void setType(CriterionType type) {
    this.type = type;
  }

  public Float getWeight() {
    return weight;
  }

  public void setWeight(Float weight) {
    this.weight = weight;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Criterion criterion = (Criterion) o;
    return Objects.equals(id, criterion.id) &&
        type == criterion.type &&
        Objects.equals(weight, criterion.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, weight);
  }
}
