package com.exadel.carinsurance.mapper.toEntity;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class AssignmentEntityMapper implements IEntityMapper<AssignmentEntity, AssignmentRequestEntity> {
  @Override
  public AssignmentEntity toEntity( AssignmentRequestEntity request ) {
    return AssignmentEntity
        .builder()
        .dateOfIncident( request.getDateOfIncident() )
        .build();
  }
}
