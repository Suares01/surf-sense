package com.surfsense.api.app.entities.beach;

import java.time.Instant;
import java.util.UUID;

public class Beach {
  private UUID id;
  private String name;
  private String imageUrl;
  private String userId;
  private Location location;
  private Coordinates coordinates;
  private Position position;
  private Instant createdAt;

  public Beach() {
  }

  public Beach(String name, String imageUrl, String userId, Location location, Coordinates coordinates,
      Position position, Instant createdAt) {
    this.name = name;
    this.imageUrl = imageUrl;
    this.userId = userId;
    this.location = location;
    this.coordinates = coordinates;
    this.position = position;
    this.createdAt = createdAt;
  }

  public Beach(UUID id, String name, String imageUrl, String userId, Location location, Coordinates coordinates,
      Position position, Instant createdAt) {
    this.id = id;
    this.name = name;
    this.imageUrl = imageUrl;
    this.userId = userId;
    this.location = location;
    this.coordinates = coordinates;
    this.position = position;
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

  public String getImageUrl() {
    return imageUrl;
  }
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Location getLocation() {
    return location;
  }
  public void setLocation(Location location) {
    this.location = location;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Position getPosition() {
    return position;
  }
  public void setPosition(Position position) {
    this.position = position;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
