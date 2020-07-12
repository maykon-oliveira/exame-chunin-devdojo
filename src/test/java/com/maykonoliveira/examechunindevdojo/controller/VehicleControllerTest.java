package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Year;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(VehicleController.class)
class VehicleControllerTest {
  @Autowired private WebTestClient webTestClient;
  @MockBean private VehicleService vehicleService;
  private final List<Vehicle> vehicles;

  VehicleControllerTest() {
    var vehicle1 = Vehicle.builder().id(1L).brand("Chevrolet").year(Year.of(2020)).build();
    var vehicle2 = Vehicle.builder().id(2L).brand("BMW").year(Year.of(2019)).build();
    var vehicle3 = Vehicle.builder().id(3L).brand("Fiat").year(Year.of(2018)).build();

    this.vehicles = List.of(vehicle1, vehicle2, vehicle3);
  }

  @Test
  @DisplayName("Should returns a list of vehicles with status code 200 and length 3")
  void test1() {
    when(vehicleService.findAll()).thenReturn(Flux.fromIterable(vehicles));

    webTestClient
        .get()
        .uri("/vehicles")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Vehicle.class)
        .value(List::size, equalTo(3));
  }

  @Test
  @DisplayName("Should returns a vehicle with status code 200 when vehicle exists")
  void test2() {
    var vehicle = vehicles.get(0);
    var id = vehicle.getId();

    when(vehicleService.findById(id)).thenReturn(Mono.just(vehicle));

    webTestClient
        .get()
        .uri("/vehicles/" + id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Vehicle.class)
        .isEqualTo(vehicle);
  }

  @Test
  @DisplayName("Should returns 404 status code when vehicle not found")
  void test3() {
    long id = 671L;
    when(vehicleService.findById(id)).thenReturn(Mono.empty());

    webTestClient
        .get()
        .uri("/vehicles/" + id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}
