package com.exadel.carinsurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoRequestEntity {
  private String vinNumber;
  private String carMake;
  private String carModel;
  private int yearOfManufacture;
  private int odometerValue;
  private String licensePlateNumber;
  private String licenseState;
  private Date licenseExpirationDate;
}
