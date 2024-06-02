package com.surfsense.api.app.entities.rating;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Rating {
  private UUID id;
  private String name;
  private RatingType type;
  private String userId;
  private Instant createdAt;

  public Rating() {
  }

  public Rating(String name, RatingType type, String userId, Instant createdAt) {
    this.name = name;
    this.type = type;
    this.userId = userId;
    this.createdAt = createdAt;
  }

  public Rating(UUID id, String name, RatingType type, String userId, Instant createdAt) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.userId = userId;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RatingType getType() {
    return type;
  }

  public void setType(RatingType type) {
    this.type = type;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
    Rating rating = (Rating) o;
    return Objects.equals(id, rating.id) &&
        Objects.equals(name, rating.name) &&
        type == rating.type &&
        Objects.equals(userId, rating.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, type, userId);
  }
}
