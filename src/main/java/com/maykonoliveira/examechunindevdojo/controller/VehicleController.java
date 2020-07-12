package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
    return vehicleService.findById(id);
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Vehicle> save(@Valid @RequestBody Vehicle vehicle) {
    return vehicleService.save(vehicle);
  }

  @PutMapping("{id}")
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
    return vehicleService.update(vehicle.withId(id));
  }

  @DeleteMapping("{id}")
  public Mono<Void> delete(@PathVariable Long id) {
    return vehicleService.delete(id);
  }
}
