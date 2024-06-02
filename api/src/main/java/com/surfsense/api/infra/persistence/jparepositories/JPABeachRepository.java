package com.surfsense.api.infra.persistence.jparepositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.surfsense.api.infra.persistence.entities.BeachEntity;

import java.util.List;

public interface JPABeachRepository extends JpaRepository<BeachEntity, UUID> {
  List<BeachEntity> findByUserId(String userId);

  long countByUserId(String userId);

  @Query("SELECT b FROM BeachEntity b WHERE b.latitude = :latitude AND b.longitude = :longitude AND b.userId = :userId")
  BeachEntity findByCoordinates(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("userId") String userId);

  @Query("SELECT b FROM BeachEntity b WHERE b.name = :name AND b.userId = :userId")
  BeachEntity findByName(@Param("name") String name, @Param("userId") String userId);
}
