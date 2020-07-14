package com.maykonoliveira.examechunindevdojo.service;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.repository.VehicleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VehicleServiceTest {
  @InjectMocks private VehicleService vehicleService;
  @Mock private VehicleRepository vehicleRepository;
  private final Vehicle vehicle = Vehicle.builder().build();

  @BeforeAll
  static void beforeAll() {
    BlockHound.install();
  }

  @BeforeEach
  void setUp() {
    when(vehicleRepository.findAll()).thenReturn(Flux.just(vehicle));
    when(vehicleRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.just(vehicle));
    when(vehicleRepository.save(ArgumentMatchers.any(Vehicle.class)))
        .thenReturn(Mono.just(vehicle));
    when(vehicleRepository.delete(ArgumentMatchers.any(Vehicle.class))).thenReturn(Mono.empty());
  }

  @Test
  void blockhoundWorks() {
    try {
      var task =
          new FutureTask<>(
              () -> {
                Thread.sleep(0);
                return "";
              });
      Schedulers.parallel().schedule(task);

      task.get(10, TimeUnit.SECONDS);
      Assertions.fail();
    } catch (Exception e) {
      Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
    }
  }

  @Test
  @DisplayName("findAll: Should returns a flux of vehicles")
  void test1() {
    StepVerifier.create(vehicleService.findAll())
        .expectSubscription()
        .expectNext(vehicle)
        .verifyComplete();
  }

  @Test
  @DisplayName("findById: Should returns a mono when vehicle exists")
  void test2() {
    StepVerifier.create(vehicleService.findById(1L))
        .expectSubscription()
        .expectNext(vehicle)
        .verifyComplete();
  }

  @Test
  @DisplayName("findById: Should throws a exception if vehicle dosen't exist")
  void test3() {
    when(vehicleRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());

    StepVerifier.create(vehicleService.findById(1L))
        .expectSubscription()
        .expectError(ResponseStatusException.class)
        .verify();
  }

  @Test
  @DisplayName("delete: Should throws a exception if vehicle dosen't exist")
  void test4() {
    when(vehicleRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());

    StepVerifier.create(vehicleService.delete(1L))
        .expectSubscription()
        .expectError(ResponseStatusException.class)
        .verify();
  }

  @Test
  @DisplayName("delete: Should complete")
  void test5() {
    StepVerifier.create(vehicleService.delete(1L)).expectSubscription().verifyComplete();
  }

  //  @Test
  //  @DisplayName("save: Given a valid vehicle should save it")
  //  void test6() {
  //    StepVerifier.create(vehicleService.saveAndStoreFiles(vehicle, filePartFlux))
  //        .expectSubscription()
  //        .expectNext(vehicle)
  //        .verifyComplete();
  //  }
}
