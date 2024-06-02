package com.surfsense.api.app.repositories;

import java.util.List;
import java.util.UUID;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.entities.beach.Coordinates;

public interface BeachRepository {
  Beach create(Beach beach);
  Beach update(Beach beach);
  void delete(UUID id);
  List<Beach> listByUser(String userId);
  Beach findById(UUID id);
  long countByUser(String userId);
  boolean existsByCoordinates(Coordinates coordinates, String userId);
  boolean existsByName(String name, String userId);
}
