package com.surfsense.api.infra.persistence.entities;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.surfsense.api.app.entities.rating.criterion.CriterionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criteria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"rating"})
public class CriterionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  CriterionType type;

  @Column(nullable = false)
  Float weight;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "rating_id", nullable = false)
  @JsonIgnore
  private RatingEntity rating;

  @Column(nullable = false, updatable = false, insertable = true)
  Instant createdAt;
}
