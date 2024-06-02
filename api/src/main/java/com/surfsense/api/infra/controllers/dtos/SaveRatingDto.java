package com.surfsense.api.infra.controllers.dtos;

import java.util.List;

import com.surfsense.api.app.entities.rating.RatingType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaveRatingDto(
    @NotBlank(message = "O nome da avaliação é obrigatório") String name,
    @NotNull(message = "O tipo da avaliação é obrigatório") RatingType type,
    @Valid @Size(min = 1, max = 7, message = "A avaliação deve ter entre 1 e 7 critérios") List<SaveCriterionDto> criteria) {

}
