package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.response.AssignmentResponseEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;
import com.exadel.carinsurance.model.response.DirectionOfImpactResponseEntity;
import com.exadel.carinsurance.model.response.VehicleConditionInfoResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignmentMapper implements IMapper<AssignmentEntity, AssignmentRequestEntity, AssignmentResponseEntity> {
  private final ContactInfoMapper contactInfoMapper;
  private final VehicleInfoMapper vehicleInfoMapper;

  @Autowired
  public AssignmentMapper(
      ContactInfoMapper contactInfoMapper,
      VehicleInfoMapper vehicleInfoMapper
  ) {
    this.contactInfoMapper = contactInfoMapper;
    this.vehicleInfoMapper = vehicleInfoMapper;
  }

  @Override
  public AssignmentEntity toEntity( AssignmentRequestEntity request ) {
    return AssignmentEntity
        .builder()
        .dateOfIncident( request.getDateOfIncident() )
        .build();
  }

  @Override
  public AssignmentResponseEntity toResponse( AssignmentEntity entity ) {
    List<ContactInfoResponseEntity> contactsInfoResponse = new ArrayList<>();

    for ( ContactInfoEntity contactInfo : entity.getContactsInfo() ) {
      ContactInfoResponseEntity contactInfoResponse = contactInfoMapper.toResponse( contactInfo );

      contactsInfoResponse.add( contactInfoResponse );
    }

    DirectionOfImpactResponseEntity directionOfImpactResponse = DirectionOfImpactResponseEntity
        .builder()
        .id( entity.getVehicleConditionInfo().getDirectionOfImpact().getId() )
        .name( entity.getVehicleConditionInfo().getDirectionOfImpact().getName() )
        .build();

    VehicleConditionInfoResponseEntity vehicleConditionInfoResponse = VehicleConditionInfoResponseEntity
        .builder()
        .directionOfImpact( directionOfImpactResponse.getName() )
        .namesOfPhotosOfImpact( entity.getVehicleConditionInfo().getNamesOfPhotosOfImpact() )
        .build();

    return AssignmentResponseEntity
        .builder()
        .assignmentId( entity.getAssignmentId() )
        .status( entity.getStatus().getName().getLabel() )
        .dateOfCreation( entity.getDateOfCreation() )
        .dateOfIncident( entity.getDateOfIncident() )
        .contactsInfo( contactsInfoResponse )
        .vehicleInfo( vehicleInfoMapper.toResponse( entity.getVehicleInfo() ) )
        .vehicleConditionInfo( vehicleConditionInfoResponse )
        .build();
  }
}
