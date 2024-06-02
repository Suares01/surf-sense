package com.surfsense.api.infra.persistence.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.surfsense.api.app.entities.beach.CountryCode;
import com.surfsense.api.app.entities.beach.Position;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "beaches")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeachEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private Position position;

  @Column(nullable = false)
  private Double latitude;

  @Column(nullable = false)
  private Double longitude;

  @Column(length = 3, nullable = true)
  private CountryCode countryCode;

  String city;
  String imageUrl;

  @Column(nullable = false, insertable = true, updatable = false)
  private Instant createdAt;
}
