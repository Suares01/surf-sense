package com.surfsense.api.infra.controllers.errorhandler;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class HandlersConfig {
  @Bean
  FilterRegistrationBean<HttpClientErrorExceptionHandlerFilter> registerHttpClientErrorExceptionHandlerFilter() {
    var registrationBean = new FilterRegistrationBean<HttpClientErrorExceptionHandlerFilter>();
    var httpClientErrorExceptionHandlerFilter = new HttpClientErrorExceptionHandlerFilter();

    registrationBean.setFilter(httpClientErrorExceptionHandlerFilter);
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

    return registrationBean;
  }
}
