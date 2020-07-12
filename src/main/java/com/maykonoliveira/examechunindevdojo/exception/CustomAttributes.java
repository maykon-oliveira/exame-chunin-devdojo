package com.maykonoliveira.examechunindevdojo.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/** @author maykon-oliveira */
@Component
public class CustomAttributes extends DefaultErrorAttributes {
  @Override
  public Map<String, Object> getErrorAttributes(
      ServerRequest request, ErrorAttributeOptions options) {
    var errorAttributes = super.getErrorAttributes(request, options);

    Throwable throwable = getError(request);

    if (throwable instanceof ResponseStatusException) {
      errorAttributes.put("message", throwable.getMessage());
    }

    return errorAttributes;
  }
}
