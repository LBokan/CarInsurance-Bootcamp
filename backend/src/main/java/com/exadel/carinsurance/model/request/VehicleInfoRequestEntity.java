package com.exadel.carinsurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoRequestEntity {
  private String vinNumber;
  private String carMake;
  private String carModel;
  private String yearOfManufacture;
  private String odometerValue;
  private String licensePlateNumber;
  private String licenseState;
  private String licenseExpirationDate;
}
