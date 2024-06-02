package com.surfsense.api.app.usecases.beach;

import java.util.UUID;

import com.surfsense.api.app.entities.beach.Beach;
import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.BadRequestException;
import com.surfsense.api.app.errors.NotFoundException;
import com.surfsense.api.app.repositories.BeachRepository;
import com.surfsense.api.app.usecases.beach.DeleteBeachUseCase.DeleteBeachParams;
import com.surfsense.api.app.usecases.UseCase;

public class DeleteBeachUseCase implements UseCase<DeleteBeachParams, Void> {
  private final BeachRepository beachRepository;

  public DeleteBeachUseCase(BeachRepository beachRepository) {
    this.beachRepository = beachRepository;
  }

  @Override
  public Void execute(DeleteBeachParams params) throws ApiException {
    Beach beach = beachRepository.findById(params.beachId);

    if (beach == null)
      throw new NotFoundException("Praia não encontrada");

    if (!beach.getUserId().equals(params.userId))
      throw new BadRequestException("Praia não pertence a esse usuário");

    beachRepository.delete(beach.getId());

    return null;
  }

  public static record DeleteBeachParams(UUID beachId, String userId) {
  }
}
