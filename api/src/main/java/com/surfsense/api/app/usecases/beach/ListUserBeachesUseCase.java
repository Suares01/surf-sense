package com.surfsense.api.app.usecases.beach;

import java.util.List;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.usecases.beach.ListUserBeachesUseCase.ListUserBeachesParams;
import com.surfsense.api.app.usecases.UseCase;

public class ListUserBeachesUseCase implements UseCase<ListUserBeachesParams, List<Beach>> {
  private final BeachRepository beachRepository;

  public ListUserBeachesUseCase(BeachRepository beachRepository) {
    this.beachRepository = beachRepository;
  }

  @Override
  public List<Beach> execute(ListUserBeachesParams params) throws ApiException {
    List<Beach> beaches = beachRepository.listByUser(params.userId);

    return beaches;
  }

  public record ListUserBeachesParams(String userId) {
  }
}
