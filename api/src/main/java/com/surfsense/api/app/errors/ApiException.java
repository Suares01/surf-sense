package com.surfsense.api.app.errors;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {
  private int status;
  private HttpStatus reason;
  private String message;

  public ApiException(String message, HttpStatus status) {
    super(message);
    this.message = message;
    this.status = status.value();
    this.reason = status;
  }

  public ExceptionResponse toExceptionResponse(String path) {
    return new ExceptionResponse(reason, message, path);
  }
}
