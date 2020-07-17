package com.maykonoliveira.examechunindevdojo.service;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/** @author maykon-oliveira */
@Service
@AllArgsConstructor
public class VehicleService {
  private final VehicleRepository vehicleRepository;
  private final FileStorageService fileStorageService;

  public Flux<Vehicle> findAll() {
    return vehicleRepository.findAll();
  }

  public Mono<Vehicle> findById(Long id) {
    return vehicleRepository.findById(id).switchIfEmpty(monoResponseStatusNotFoundException());
  }

  public <T> Mono<T> monoResponseStatusNotFoundException() {
    return Mono.error(new ResponseStatusException(NOT_FOUND, "Veículo não encontrado"));
  }

  public Mono<Vehicle> save(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  @Transactional
  public Mono<Vehicle> saveAndStoreFiles(Vehicle vehicle, Mono<FilePart> filePartMono) {
    return vehicleRepository
        .save(vehicle)
        .flatMap(
            (vehicleSaved) ->
                fileStorageService
                    .store(filePartMono)
                    .flatMap(
                        filename -> vehicleRepository.save(vehicleSaved.withThumbnail(filename))));
  }

  public Mono<Void> update(Vehicle vehicle) {
    return findById(vehicle.getId())
        .map(vehicleFound -> vehicle)
        .flatMap(vehicleRepository::save)
        .then();
  }

  public Mono<Void> delete(Long id) {
    return findById(id).flatMap(vehicleRepository::delete);
  }
}
