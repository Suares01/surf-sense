package com.surfsense.api.infra.persistence.mappers;

public interface Mapper<P, D> {
  public P toEntity(D domain);

  public D toDomain(P entity);
}
