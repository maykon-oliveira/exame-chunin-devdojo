package com.maykonoliveira.examechunindevdojo.exception;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.boot.web.error.ErrorAttributeOptions.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/** @author maykon-oliveira */
@Order(-2)
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
  public GlobalExceptionHandler(
      ErrorAttributes errorAttributes,
      ResourceProperties resourceProperties,
      ApplicationContext applicationContext,
      ServerCodecConfigurer codecConfigurer) {
    super(errorAttributes, resourceProperties, applicationContext);
    this.setMessageWriters(codecConfigurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
  }

  private Mono<ServerResponse> formatErrorResponse(ServerRequest req) {
    var attributeOptions = isTraceEnabled(req) ? of(Include.STACK_TRACE) : defaults();
    var errorAttributes = getErrorAttributes(req, attributeOptions);
    var status =
        (int)
            Optional.ofNullable(errorAttributes.get("status"))
                .orElse(INTERNAL_SERVER_ERROR.value());
    return ServerResponse.status(status)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(errorAttributes));
  }
}
