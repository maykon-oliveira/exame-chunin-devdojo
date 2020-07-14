package com.maykonoliveira.examechunindevdojo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/** @author maykon-oliveira */
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("vehicle_image")
public class VehicleImage {
  @Id private Long id;
  private Long vehicleId;
  private String filename;
  private Boolean thumbnail;
}
