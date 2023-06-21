package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.assignment.VehicleInfoEntity;
import com.exadel.carinsurance.model.response.VehicleInfoResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleInfoResponseMapper implements IResponseMapper<VehicleInfoEntity, VehicleInfoResponseEntity> {
  @Override
  public VehicleInfoResponseEntity toResponse( VehicleInfoEntity entity ) {
    return VehicleInfoResponseEntity
        .builder()
        .vinNumber( entity.getVinNumber() )
        .carMake( entity.getCarMake() )
        .carModel( entity.getCarModel() )
        .yearOfManufacture( entity.getYearOfManufacture() )
        .odometerValue( entity.getOdometerValue() )
        .licensePlateNumber( entity.getLicensePlateNumber() )
        .licenseState( entity.getLicenseState() )
        .licenseExpirationDate( entity.getLicenseExpirationDate() )
        .build();
  }
}
