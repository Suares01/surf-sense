package com.surfsense.api.infra.controllers.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.surfsense.api.app.errors.ApiException;
import com.surfsense.api.app.errors.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(ApiException.class)
  private ResponseEntity<ExceptionResponse> conflictExceptionHandler(ApiException exception, HttpServletRequest request) {
    logException(exception);
    String path = request.getRequestURI();
    return ResponseEntity.status(exception.getStatus()).body(exception.toExceptionResponse(path));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleExceptionInternal(Exception exception, HttpServletRequest request, HttpServletResponse response) {
    logException(exception);
    if (exception instanceof NullPointerException) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  private void logException(Exception exception) {
    logger.error(exception.getMessage(), exception);
  }
}
