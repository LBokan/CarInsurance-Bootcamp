package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.response.AssignmentResponseEntity;

public class AssignmentMapper {
  public static AssignmentEntity mapToAssignment( AssignmentRequestEntity assignmentRequest ) {
    return AssignmentEntity
        .builder()
        .dateOfIncident( assignmentRequest.getDateOfIncident() )
        .build();
  }

  public static AssignmentResponseEntity mapToAssignmentResponse( AssignmentEntity assignment ) {
    return AssignmentResponseEntity
        .builder()
        .assignmentId( assignment.getAssignmentId() )
        .status( assignment.getStatus().getName().getLabel() )
        .dateOfIncident( assignment.getDateOfIncident() )
        .build();
  }
}
