package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.response.AssignmentResponseEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;
import com.exadel.carinsurance.model.response.DirectionOfImpactResponseEntity;
import com.exadel.carinsurance.model.response.VehicleConditionInfoResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class AssignmentMapper {
  public static AssignmentEntity mapToAssignment( AssignmentRequestEntity assignmentRequest ) {
    return AssignmentEntity
        .builder()
        .dateOfIncident( assignmentRequest.getDateOfIncident() )
        .build();
  }

  public static AssignmentResponseEntity mapToAssignmentResponse( AssignmentEntity assignment ) {
    List<ContactInfoResponseEntity> contactsInfoResponse = new ArrayList<>();

    for ( ContactInfoEntity contactInfo : assignment.getContactsInfo() ) {
      ContactInfoResponseEntity contactInfoResponse = ContactInfoMapper
          .mapToContactInfoResponse( contactInfo );

      contactsInfoResponse.add( contactInfoResponse );
    }

    DirectionOfImpactResponseEntity directionOfImpactResponse = DirectionOfImpactResponseEntity
        .builder()
        .id( assignment.getVehicleConditionInfo().getDirectionOfImpact().getId() )
        .name( assignment.getVehicleConditionInfo().getDirectionOfImpact().getName() )
        .build();

    VehicleConditionInfoResponseEntity vehicleConditionInfoResponse = VehicleConditionInfoResponseEntity
        .builder()
        .directionOfImpact( directionOfImpactResponse.getName() )
        .namesOfPhotosOfImpact( assignment.getVehicleConditionInfo().getNamesOfPhotosOfImpact() )
        .build();

    return AssignmentResponseEntity
        .builder()
        .assignmentId( assignment.getAssignmentId() )
        .status( assignment.getStatus().getName().getLabel() )
        .dateOfCreation( assignment.getDateOfCreation() )
        .dateOfIncident( assignment.getDateOfIncident() )
        .contactsInfo( contactsInfoResponse )
        .vehicleInfo( VehicleInfoMapper
            .mapToVehicleInfoResponse( assignment.getVehicleInfo() ) )
        .vehicleConditionInfo( vehicleConditionInfoResponse )
        .build();
  }
}
