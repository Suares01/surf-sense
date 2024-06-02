package com.surfsense.api.infra.configuration.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.surfsense.api.app.repositories.CriterionRepository;
import com.surfsense.api.app.repositories.RatingRepository;
import com.surfsense.api.app.services.RatingService;
import com.surfsense.api.app.usecases.rating.CreateDefaultRatingsUseCase;
import com.surfsense.api.app.usecases.rating.CreateUserRatingUseCase;
import com.surfsense.api.app.usecases.rating.DeleteUserRatingUseCase;
import com.surfsense.api.app.usecases.rating.GetRatingUseCase;
import com.surfsense.api.app.usecases.rating.ListUserRatingsUseCase;
import com.surfsense.api.app.usecases.rating.UpdateRatingUseCase;
import com.surfsense.api.infra.persistence.jparepositories.JPARatingRepository;
import com.surfsense.api.infra.persistence.mappers.RatingEntityMapper;
import com.surfsense.api.infra.persistence.repositories.RatingRepositoryImpl;
import com.surfsense.api.infra.services.RatingServiceImpl;

@Configuration
public class RatingConfig {
  @Bean
  RatingRepository ratingRepository(JPARatingRepository ratingRepository, RatingEntityMapper mapper) {
    return new RatingRepositoryImpl(ratingRepository, mapper);
  }

  @Bean
  RatingService ratingService(RatingRepository ratingRepository, CriterionRepository criterionRepository) {
    return new RatingServiceImpl(ratingRepository, criterionRepository);
  }

  @Bean
  CreateDefaultRatingsUseCase createDefaultRatingsUseCase(RatingService ratingService) {
    return new CreateDefaultRatingsUseCase(ratingService);
  }

  @Bean
  CreateUserRatingUseCase createUserRatingUseCase(RatingService ratingService) {
    return new CreateUserRatingUseCase(ratingService);
  }

  @Bean
  DeleteUserRatingUseCase deleteUserRatingUseCase(RatingService ratingService) {
    return new DeleteUserRatingUseCase(ratingService);
  }

  @Bean
  GetRatingUseCase getRatingUseCase(RatingService ratingService) {
    return new GetRatingUseCase(ratingService);
  }

  @Bean
  ListUserRatingsUseCase listUserRatingsUseCase(RatingService ratingService) {
    return new ListUserRatingsUseCase(ratingService);
  }

  @Bean
  UpdateRatingUseCase updateRatingUseCase(RatingService ratingService) {
    return new UpdateRatingUseCase(ratingService);
  }
}
