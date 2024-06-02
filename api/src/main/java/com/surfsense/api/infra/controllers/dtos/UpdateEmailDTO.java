package com.surfsense.api.infra.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailDTO(
    @NotBlank(message = "O novo e-mail precisa ser fornecido.") @Email(message = "O e-mail precisa ter um formato válido.") String email) {

}
