package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.response.AssignmentResponseEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;
import com.exadel.carinsurance.model.response.VehicleConditionInfoResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignmentResponseMapper implements IResponseMapper<AssignmentEntity, AssignmentResponseEntity> {
  private final ContactInfoResponseMapper contactInfoResponseMapper;
  private final VehicleInfoResponseMapper vehicleInfoResponseMapper;

  @Autowired
  public AssignmentResponseMapper(
      ContactInfoResponseMapper contactInfoResponseMapper,
      VehicleInfoResponseMapper vehicleInfoResponseMapper
  ) {
    this.contactInfoResponseMapper = contactInfoResponseMapper;
    this.vehicleInfoResponseMapper = vehicleInfoResponseMapper;
  }

  @Override
  public AssignmentResponseEntity toResponse( AssignmentEntity entity ) {
    List<ContactInfoResponseEntity> contactsInfoResponse = new ArrayList<>();

    for ( ContactInfoEntity contactInfo : entity.getContactsInfo() ) {
      ContactInfoResponseEntity contactInfoResponse = contactInfoResponseMapper.toResponse( contactInfo );

      contactsInfoResponse.add( contactInfoResponse );
    }

    VehicleConditionInfoResponseEntity vehicleConditionInfoResponse = VehicleConditionInfoResponseEntity
        .builder()
        .directionOfImpact( entity.getVehicleConditionInfo().getDirectionOfImpact().getName() )
        .namesOfPhotosOfImpact( entity.getVehicleConditionInfo().getNamesOfPhotosOfImpact() )
        .build();

    AssignmentResponseEntity assignmentResponse = AssignmentResponseEntity
        .builder()
        .id( entity.getId() )
        .userId( entity.getUserId() )
        .status( entity.getStatus().getName().getLabel() )
        .dateOfCreation( entity.getDateOfCreation() )
        .dateOfIncident( entity.getDateOfIncident() )
        .contactsInfo( contactsInfoResponse )
        .vehicleInfo( vehicleInfoResponseMapper.toResponse( entity.getVehicleInfo() ) )
        .vehicleConditionInfo( vehicleConditionInfoResponse )
        .insuranceAgency( entity.getInsuranceAgency().getName() )
        .build();

    if ( entity.getRepairFacilityId() != null ) {
      assignmentResponse.setRepairFacility( entity.getRepairFacility().getName() );
    }

    return assignmentResponse;
  }
}
