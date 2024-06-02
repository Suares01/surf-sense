package com.surfsense.api.infra.controllers.dtos.custom.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.surfsense.api.infra.controllers.dtos.custom.CloudflareR2Domain;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CloudflareR2DomainValidator implements ConstraintValidator<CloudflareR2Domain, String> {
  private static Pattern pattern;

  @Override
  public void initialize(CloudflareR2Domain constraintAnnotation) {
    final String URL_PATTERN = "^https://pub-[0-9a-f]{32}.r2.dev/.+$";
    pattern = Pattern.compile(URL_PATTERN);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return true;

    Matcher matcher = pattern.matcher(value);

    return matcher.matches();
  }

}
