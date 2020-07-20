package com.maykonoliveira.examechunindevdojo.security.config;

import com.maykonoliveira.examechunindevdojo.security.RegexServerWebExchangeMatcher;
import com.maykonoliveira.examechunindevdojo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.regex.Pattern;

import static org.springframework.security.config.Customizer.withDefaults;

/** @author maykon-oliveira */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    return http.csrf()
        .disable()
        .cors(withDefaults())
        .authorizeExchange()
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

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
