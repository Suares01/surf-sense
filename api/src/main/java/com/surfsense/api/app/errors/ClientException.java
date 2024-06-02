package com.surfsense.api.app.errors;

import org.springframework.http.HttpStatus;

public class ClientException extends ApiException {

  public ClientException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }

}
