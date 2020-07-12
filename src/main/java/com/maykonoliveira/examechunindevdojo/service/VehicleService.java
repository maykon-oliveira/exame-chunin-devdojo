package com.maykonoliveira.examechunindevdojo.service;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** @author maykon-oliveira */
@Service
@AllArgsConstructor
public class VehicleService {
  private final VehicleRepository vehicleRepository;

  public Flux<Vehicle> findAll() {
    return vehicleRepository.findAll();
  }
}
