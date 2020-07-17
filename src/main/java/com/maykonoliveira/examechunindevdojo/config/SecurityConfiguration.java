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

import java.util.regex.Pattern;

/** @author maykon-oliveira */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/vehicles")
        .permitAll()
        .matchers(
            new RegexServerWebExchangeMatcher(
                HttpMethod.GET, Pattern.compile("^/(css|js|uploads)/")))
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
