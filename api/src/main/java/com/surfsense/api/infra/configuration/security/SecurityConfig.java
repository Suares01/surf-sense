package com.surfsense.api.infra.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private AuthFilter authFilter;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/actuator").permitAll()
            .requestMatchers("/swagger-ui/index.html").permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()
            .requestMatchers(HttpMethod.POST, "/ratings/default").hasRole("ADMIN")
            .anyRequest().authenticated())
        .addFilterAfter(authFilter, BearerTokenAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
