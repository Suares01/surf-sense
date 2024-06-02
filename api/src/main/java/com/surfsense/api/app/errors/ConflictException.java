package com.surfsense.api.app.errors;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {

  public ConflictException(String message) {
    super(message, HttpStatus.CONFLICT);
  }

}
