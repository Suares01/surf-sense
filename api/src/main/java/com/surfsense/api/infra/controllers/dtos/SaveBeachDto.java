package com.surfsense.api.infra.controllers.dtos;

import com.surfsense.api.app.entities.beach.CountryCode;
import com.surfsense.api.app.entities.beach.Position;
import com.surfsense.api.infra.controllers.dtos.custom.CloudflareR2Domain;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveBeachDto(
    @NotBlank(message = "O nome da praia é obrigatório") String name,
    @NotNull(message = "A latitude é obrigatória") @DecimalMin(value = "-90.0", message = "A latitude mínima é -90.0") @DecimalMax(value = "90.0", message = "A latitude máxima é 90.0") Double lat,
    @NotNull(message = "A longitude é obrigatória") @DecimalMin(value = "-180.0", message = "A longitude mínima é -180.0") @DecimalMax(value = "180.0", message = "A longitude máxima é 180.0") Double lng,
    @NotNull(message = "A posição é obrigatória") Position position,
    @CloudflareR2Domain(message = "Url da imagem possui um formato inválido") String imageUrl,
    CountryCode countryCode,
    String city) {

}
