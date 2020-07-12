package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.entity.VehicleType;
import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@WebFluxTest(VehicleController.class)
class VehicleControllerTest {
  @Autowired private WebTestClient webTestClient;
  @MockBean private VehicleService vehicleService;
  private final List<Vehicle> vehicles;

  VehicleControllerTest() {
    var vehicle1 = Vehicle.builder().id(1L).brand("Chevrolet").year(2020).build();
    var vehicle2 = Vehicle.builder().id(2L).brand("BMW").year(2019).build();
    var vehicle3 = Vehicle.builder().id(3L).brand("Fiat").year(2018).build();

    this.vehicles = List.of(vehicle1, vehicle2, vehicle3);
  }

  @Test
  @DisplayName("Should returns a list of vehicles with status code 200 and length 3")
  void test1() {
    when(vehicleService.findAll()).thenReturn(Flux.fromIterable(vehicles));

    webTestClient
        .get()
        .uri("/vehicles")
        .accept(APPLICATION_JSON)
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
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Vehicle.class)
        .isEqualTo(vehicle);
  }

  @Test
  @Disabled
  @DisplayName("Should returns 404 status code when vehicle not found")
  void test3() {
    long id = 671L;

    webTestClient
        .get()
        .uri("/vehicles/" + id)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  @DisplayName("Given a invalid vehicle should returns 404 status code")
  void test4() {
    var vehicle = Vehicle.builder().build();

    when(vehicleService.save(vehicle)).thenReturn(Mono.just(vehicle));

    webTestClient
        .post()
        .uri("/vehicles")
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(vehicle))
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Given a valid vehicle should save and returns 201 status code")
  void test5() {
    var price = new BigDecimal(10000);
    var vehicle =
        Vehicle.builder()
            .brand("Renault")
            .type(VehicleType.SPORT)
            .model("Sport")
            .year(2020)
            .price(price)
            .build();

    when(vehicleService.save(vehicle))
        .thenReturn(
            Mono.just(vehicle)
                .map(
                    v -> {
                      v.setId(4L);
                      return v;
                    }));

    webTestClient
        .post()
        .uri("/vehicles")
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(vehicle))
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.brand")
        .isEqualTo("Renault")
        .jsonPath("$.price")
        .isEqualTo(10000)
        .jsonPath("$.type")
        .isEqualTo(VehicleType.SPORT.toString())
        .jsonPath("$.id")
        .isNotEmpty();
  }
}
