package com.surfsense.api.infra.persistence.jparepositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.surfsense.api.infra.persistence.entities.CriterionEntity;

import java.util.Collection;
import java.util.List;

public interface JPACriterionRepository extends JpaRepository<CriterionEntity, UUID> {
  @Query("SELECT c FROM CriterionEntity c WHERE c.rating.id = :ratingId")
  List<CriterionEntity> findByRating(@Param("ratingId") UUID ratingId);

  @Query("SELECT c FROM CriterionEntity c WHERE c.rating.id IN :ratingIds")
  List<CriterionEntity> findByRating(@Param("ratingIds") Collection<UUID> ratingIds);
}
