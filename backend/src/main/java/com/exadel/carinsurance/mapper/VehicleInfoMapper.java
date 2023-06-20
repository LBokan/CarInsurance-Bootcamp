package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.VehicleInfoEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.response.VehicleInfoResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleInfoMapper implements IMapper<VehicleInfoEntity, AssignmentRequestEntity, VehicleInfoResponseEntity> {
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
