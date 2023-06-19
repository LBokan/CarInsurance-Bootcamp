package com.exadel.carinsurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleConditionInfoResponseEntity {
  private String directionOfImpact;
  private String namesOfPhotosOfImpact;
}
