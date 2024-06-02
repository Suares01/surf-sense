package com.surfsense.api.infra.controllers.dtos.custom;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.surfsense.api.infra.controllers.dtos.custom.validator.CloudflareR2DomainValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CloudflareR2DomainValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CloudflareR2Domain {
  String message() default "Invalid url.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
