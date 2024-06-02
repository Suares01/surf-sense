package com.surfsense.api.app.errors;

import org.springframework.http.HttpStatus;

public class InternalException extends ApiException {

  public InternalException(String message) {
    super(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
