package com.maykonoliveira.examechunindevdojo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

/** @author maykon-oliveira */
public class RegexServerWebExchangeMatcher implements ServerWebExchangeMatcher {
  private final Pattern pattern;
  private final HttpMethod method;

  public RegexServerWebExchangeMatcher(Pattern pattern) {
    this.pattern = pattern;
    this.method = null;
  }

  public RegexServerWebExchangeMatcher(HttpMethod method, Pattern pattern) {
    this.pattern = pattern;
    this.method = method;
  }

  @Override
  public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
    var request = serverWebExchange.getRequest();
    if (this.method != null && !this.method.equals(request.getMethod())) {
      return MatchResult.notMatch();
    } else {
      var uri = request.getPath().toString();
      var matcher = pattern.matcher(uri);

      return matcher.find()
          ? ServerWebExchangeMatcher.MatchResult.match()
          : ServerWebExchangeMatcher.MatchResult.notMatch();
    }
  }
}
