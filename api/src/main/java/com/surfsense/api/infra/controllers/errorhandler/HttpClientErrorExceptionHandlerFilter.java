package com.surfsense.api.infra.controllers.errorhandler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.filter.OncePerRequestFilter;

import com.surfsense.api.app.errors.ExceptionResponse;
import com.surfsense.api.infra.utils.ParseObject;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HttpClientErrorExceptionHandlerFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(HttpClientErrorExceptionHandlerFilter.class);

  @Override
  @SuppressWarnings("null")
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (HttpClientErrorException exception) {
      int status = exception.getStatusCode().value();
      String path = request.getRequestURI();
      ExceptionResponse exceptionResponse;

      if (exception instanceof TooManyRequests) {
        exceptionResponse = new ExceptionResponse(status, exception.getStatusText(), "The rate limiter has been exceeded.", path);
      } else {
        exceptionResponse = new ExceptionResponse(status, exception.getStatusText(), exception.getMessage(), path);
      }

      logger.warn(exceptionResponse.getMessage(), exception);

      response.setStatus(exceptionResponse.getStatus());
      response.getWriter().write(ParseObject.toJson(exceptionResponse));
    }
  }
}
