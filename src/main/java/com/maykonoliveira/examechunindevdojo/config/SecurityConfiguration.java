package com.maykonoliveira.examechunindevdojo.config;

import com.maykonoliveira.examechunindevdojo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/** @author maykon-oliveira */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    return http.csrf()
        .disable()
        .authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/vehicles")
        .permitAll()
        .anyExchange()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .build();
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager(UserService userService) {
    return new UserDetailsRepositoryReactiveAuthenticationManager(userService);
  }
}
