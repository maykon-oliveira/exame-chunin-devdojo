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
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import java.util.regex.Pattern;

/** @author maykon-oliveira */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/", "/login")
        .permitAll()
        .matchers(
            new RegexServerWebExchangeMatcher(
                HttpMethod.GET, Pattern.compile("^/(css|js|uploads)/")))
        .permitAll()
        .anyExchange()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/vehicles"))
        .and()
        .logout()
        .logoutUrl("/logout")
        .requiresLogout(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/logout"))
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
  public SpringSecurityDialect securityDialect() {
    return new SpringSecurityDialect();
  }
}
