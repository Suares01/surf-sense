package com.surfsense.api.infra.configuration.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.surfsense.api.app.repositories.CriterionRepository;
import com.surfsense.api.infra.persistence.jparepositories.JPACriterionRepository;
import com.surfsense.api.infra.persistence.mappers.CriterionEntityMapper;
import com.surfsense.api.infra.persistence.repositories.CriterionRepositoryImpl;

@Configuration
public class CriterionConfig {
  @Bean
  CriterionRepository criterionRepository(JPACriterionRepository criterionRepository, CriterionEntityMapper mapper) {
    return new CriterionRepositoryImpl(criterionRepository, mapper);
  }
}
