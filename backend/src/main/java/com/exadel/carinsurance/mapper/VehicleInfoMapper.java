package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.VehicleInfoEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.response.VehicleInfoResponseEntity;

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

  public static VehicleInfoResponseEntity mapToVehicleInfoResponse( VehicleInfoEntity vehicleInfo ) {
    return VehicleInfoResponseEntity
        .builder()
        .id( vehicleInfo.getId() )
        .vinNumber( vehicleInfo.getVinNumber() )
        .carMake( vehicleInfo.getCarMake() )
        .carModel( vehicleInfo.getCarModel() )
        .yearOfManufacture( vehicleInfo.getYearOfManufacture() )
        .odometerValue( vehicleInfo.getOdometerValue() )
        .licensePlateNumber( vehicleInfo.getLicensePlateNumber() )
        .licenseState( vehicleInfo.getLicenseState() )
        .licenseExpirationDate( vehicleInfo.getLicenseExpirationDate() )
        .build();
  }
}
