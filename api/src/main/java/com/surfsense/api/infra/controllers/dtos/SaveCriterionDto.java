package com.surfsense.api.infra.controllers.dtos;

import com.surfsense.api.app.entities.rating.criterion.CriterionType;

import jakarta.validation.constraints.NotNull;

public record SaveCriterionDto(
    @NotNull(message = "O tipo do critério é obrigatório") CriterionType type,
    @NotNull(message = "O peso do critério é obrigatório") Float weight) {

}
