package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.VehicleInfoEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;

public class VehicleInfoMapper {
  public static VehicleInfoEntity mapToVehicleInfo( AssignmentRequestEntity request ) {
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
