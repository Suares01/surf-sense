package com.surfsense.api;

import java.time.Instant;
import java.util.UUID;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;
import com.surfsense.api.app.entities.beach.Location;
import com.surfsense.api.app.entities.beach.Position;
import com.surfsense.api.app.entities.rating.Rating;
import com.surfsense.api.app.entities.rating.RatingType;
import com.surfsense.api.app.entities.rating.criterion.Criterion;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;

public class Factories {
  public static Beach buildBeach() {
    var location = new Location(null, null);
    var coordinates = new Coordinates(2.0, 2.0);
    return new Beach(
        UUID.randomUUID(),
        "Beach Test Name",
        null,
        "user-test-id",
        location,
        coordinates,
        Position.SOUTH,
        Instant.now());
  }

  public static Rating buildRating(RatingType type) {
    return new Rating(
        UUID.randomUUID(),
        "Test Rating",
        type,
        "user-id",
        Instant.now());
  }

  public static Criterion buildCriterion(CriterionType type, Float weight, Rating rating) {
    return new Criterion(
        UUID.randomUUID(),
        type,
        weight,
        rating,
        Instant.now());
  }
}
