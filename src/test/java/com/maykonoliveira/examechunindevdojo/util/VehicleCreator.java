package com.maykonoliveira.examechunindevdojo.util;

import com.maykonoliveira.examechunindevdojo.entity.Vehicle;
import com.maykonoliveira.examechunindevdojo.entity.VehicleType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;

/** @author maykon-oliveira */
public class VehicleCreator {

  private static final long id = 1L;
  private static final String brand = "Renault";
  private static final String sport = "Sport";
  private static final int year = 2020;
  private static final BigDecimal price = new BigDecimal(10000);

  public static Vehicle createValidVehicle() {
    return Vehicle.builder()
        .id(id)
        .brand(brand)
        .type(VehicleType.SPORT)
        .model(sport)
        .year(year)
        .price(price)
        .build();
  }

  public static BodyInserters.FormInserter createMultipartValidVehicle() {
    MultiValueMap map = new LinkedMultiValueMap<String, Object>();
    map.add("id", id);
    map.add("brand", brand);
    map.add("sport", sport);
    map.add("year", year);
    map.add("price", price);
    map.add("file", 1);
    return BodyInserters.fromFormData(map);
  }
}
