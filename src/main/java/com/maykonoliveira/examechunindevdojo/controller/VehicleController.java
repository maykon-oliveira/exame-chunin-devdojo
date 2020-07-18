package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/** @author maykon-oliveira */
@Controller
@AllArgsConstructor
@RequestMapping("vehicles")
public class VehicleController {
  private final VehicleService vehicleService;

  @GetMapping
  public String findAll(Model model) {
    IReactiveDataDriverContextVariable reactiveDataDrivenMode =
        new ReactiveDataDriverContextVariable(vehicleService.findAll(), 1);
    model.addAttribute("vehicles", reactiveDataDrivenMode);
    return "vehicles/vehicles-list";
  }

  @GetMapping("{id}")
  public Mono<Vehicle> findById(@PathVariable Long id) {
    return vehicleService.findById(id);
  }

  @ResponseStatus(CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
  public Mono<Vehicle> save(
      @Valid Vehicle vehicle, @RequestPart("file") Mono<FilePart> filePartMono) {
    return vehicleService.saveAndStoreFiles(vehicle, filePartMono);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
    return vehicleService.update(vehicle.withId(id));
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Mono<Void> delete(@PathVariable Long id) {
    return vehicleService.delete(id);
  }
}
