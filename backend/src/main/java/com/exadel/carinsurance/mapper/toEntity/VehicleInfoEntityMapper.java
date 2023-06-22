package com.exadel.carinsurance.mapper.toEntity;

import com.exadel.carinsurance.model.assignment.VehicleInfoEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleInfoEntityMapper implements IEntityMapper<VehicleInfoEntity, AssignmentRequestEntity> {
  @Override
  public VehicleInfoEntity toEntity( AssignmentRequestEntity request ) {
    return VehicleInfoEntity
        .builder()
        .vinNumber( request.getVehicleInfo().getVinNumber() )
        .carMake( request.getVehicleInfo().getCarMake() )
        .carModel( request.getVehicleInfo().getCarModel() )
        .yearOfManufacture( request.getVehicleInfo().getYearOfManufacture() )
        .odometerValue( request.getVehicleInfo().getOdometerValue() )
        .licensePlateNumber( request.getVehicleInfo().getLicensePlateNumber() )
        .licenseState( request.getVehicleInfo().getLicenseState() )
        .licenseExpirationDate( request.getVehicleInfo().getLicenseExpirationDate() )
        .build();
  }
}
