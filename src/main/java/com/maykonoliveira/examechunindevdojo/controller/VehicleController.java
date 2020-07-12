package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
}
