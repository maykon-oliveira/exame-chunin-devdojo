package com.maykonoliveira.examechunindevdojo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Year;

/** @author maykon-oliveira */
@Data
@With
@Builder
@Table("vehicle")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
  @Id private Long id;
  @NotNull private VehicleType type;
  @NotBlank private String brand;
  @NotBlank private String model;
  @NotNull private Year year;
  @NotNull private BigDecimal price;
}
