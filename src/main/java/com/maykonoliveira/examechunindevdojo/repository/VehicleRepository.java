package com.maykonoliveira.examechunindevdojo.repository;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/** @author maykon-oliveira */
public interface VehicleRepository extends ReactiveCrudRepository<Vehicle, Long> {}
