package com.surfsense.api.infra.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateDefaultRatingsDto(@NotBlank(message = "O id do usuário é obrigatório") String userId) {

}
