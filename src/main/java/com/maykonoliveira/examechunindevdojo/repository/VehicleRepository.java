package com.maykonoliveira.examechunindevdojo.repository;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/** @author maykon-oliveira */
public interface VehicleRepository extends ReactiveCrudRepository<Vehicle, Long> {
  Mono<Vehicle> findById(Long id);
}
