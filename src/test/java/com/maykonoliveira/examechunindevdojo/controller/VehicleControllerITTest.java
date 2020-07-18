package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.entity.VehicleType;
import com.maykonoliveira.examechunindevdojo.repository.VehicleRepository;
import com.maykonoliveira.examechunindevdojo.util.VehicleCreator;
import com.maykonoliveira.examechunindevdojo.util.WebTestClientUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
class VehicleControllerITTest {
  @Autowired private WebTestClientUtil webTestClientUtil;
  @Autowired private WebTestClient unauthenticatedUser;
  private WebTestClient adminAuthenticatedUser;
  @MockBean private VehicleRepository vehicleRepository;
  private final Vehicle vehicle = VehicleCreator.createValidVehicle();

  @BeforeEach
  void setup() {
    adminAuthenticatedUser = webTestClientUtil.authenticateClient("admin", "admin");
    when(vehicleRepository.findAll()).thenReturn(Flux.just(vehicle));
    when(vehicleRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.just(vehicle));
    when(vehicleRepository.save(ArgumentMatchers.any(Vehicle.class)))
        .thenReturn(Mono.just(vehicle));
    when(vehicleRepository.delete(ArgumentMatchers.any(Vehicle.class))).thenReturn(Mono.empty());
  }

  @Test
  @DisplayName("Should returns a list of vehicles with status code 200 and length 3")
  void test1() {
    unauthenticatedUser
        .get()
        .uri("/vehicles")
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Vehicle.class)
        .hasSize(1);
  }

  @Test
  @DisplayName("Should returns a vehicle with status code 200 when vehicle exists")
  void test2() {
    adminAuthenticatedUser
        .get()
        .uri("/vehicles/" + vehicle.getId())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Vehicle.class)
        .isEqualTo(vehicle);
  }

  @Test
  @DisplayName("Given a invalid vehicle should returns 404 status code")
  void test4() {
    adminAuthenticatedUser
        .post()
        .uri("/vehicles")
        .contentType(MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData("files", 1))
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Given a valid vehicle should save and returns 201 status code")
  void test5() {
    when(vehicleRepository.save(vehicle))
        .thenReturn(
            Mono.just(vehicle)
                .map(
                    v -> {
                      v.setId(4L);
                      return v;
                    }));

    adminAuthenticatedUser
        .post()
        .uri("/vehicles")
        .contentType(MULTIPART_FORM_DATA)
        .body(VehicleCreator.createMultipartValidVehicle())
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

  @Test
  @DisplayName("Given a id to delete should returns 200 status code")
  void test6() {
    adminAuthenticatedUser.delete().uri("/vehicles/" + 1).exchange().expectStatus().isOk();
  }

  @Test
  @DisplayName("Given a id that dosen't exist to delete should returns 404")
  void test7() {
    var id = 1L;
    when(vehicleRepository.findById(id)).thenReturn(Mono.empty());

    adminAuthenticatedUser.delete().uri("/vehicles" + id).exchange().expectStatus().isNotFound();
  }
}
