package com.surfsense.api.infra.configuration.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.usecases.beach.DeleteBeachUseCase;
import com.surfsense.api.app.usecases.beach.EditUserBeachUseCase;
import com.surfsense.api.app.usecases.beach.ListUserBeachesUseCase;
import com.surfsense.api.app.usecases.beach.SaveUserBeachUseCase;
import com.surfsense.api.infra.persistence.jparepositories.JPABeachRepository;
import com.surfsense.api.infra.persistence.mappers.BeachEntityMapper;
import com.surfsense.api.infra.persistence.repositories.BeachRepositoryImpl;

@Configuration
public class BeachConfig {
  @Bean
  BeachRepository beachRepository(JPABeachRepository repository, BeachEntityMapper mapper) {
    return new BeachRepositoryImpl(repository, mapper);
  }

  @Bean
  SaveUserBeachUseCase saveUserBeachUseCase(BeachRepository repository) {
    return new SaveUserBeachUseCase(repository);
  }

  @Bean
  ListUserBeachesUseCase listUserBeachesUseCase(BeachRepository repository) {
    return new ListUserBeachesUseCase(repository);
  }

  @Bean
  EditUserBeachUseCase editUserBeachUseCase(BeachRepository repository) {
    return new EditUserBeachUseCase(repository);
  }

  @Bean
  DeleteBeachUseCase deleteBeachUseCase(BeachRepository repository) {
    return new DeleteBeachUseCase(repository);
  }
}
