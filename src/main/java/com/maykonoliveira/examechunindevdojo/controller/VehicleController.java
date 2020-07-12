package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author maykon-oliveira */
@RestController
@AllArgsConstructor
@RequestMapping("vehicles")
public class VehicleController {
  private final VehicleService vehicleService;

  @GetMapping
  public Flux<Vehicle> findAll() {
    return vehicleService.findAll();
  }

  @GetMapping("{id}")
  public Mono<Vehicle> findById(@PathVariable Long id) {
    return vehicleService.findById(id).switchIfEmpty(monoResponseStatusNotFoundException());
  }

  public <T> Mono<T> monoResponseStatusNotFoundException() {
    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
  }
}
