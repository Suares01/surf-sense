package com.surfsense.api.app.errors;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
  private int status;
  private String reason;
  private String message;
  private String path;

  public ExceptionResponse(HttpStatus httpStatus, String message, String path) {
    this.status = httpStatus.value();
    this.reason = httpStatus.getReasonPhrase();
    this.message = message;
    this.path = path;
  }
}
