package com.surfsense.api.infra.persistence.jparepositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surfsense.api.infra.persistence.entities.RatingEntity;
import java.util.List;


public interface JPARatingRepository extends JpaRepository<RatingEntity, UUID> {
  List<RatingEntity> findByUserId(String userId);
}
